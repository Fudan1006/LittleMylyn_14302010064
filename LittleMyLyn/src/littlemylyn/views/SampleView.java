package littlemylyn.views;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import littlemylyn.entity.Node;
import littlemylyn.entity.Task;
import littlemylyn.entity.TaskList;
import littlemylyn.sql.UpdateTask;

public class SampleView extends ViewPart {

	public static final String ID = "littlemylyn.views.SampleView";
	private Action newTaskAction;
	private Action recordRelatedClassAction;
	private Action debugAction;
	private Action newFeatureAction;
	private Action refactorAction;
	private Action activateAction;
	private Action deactivateAction;

	private Action doubleClickAction;

	private Node root;
	private static TreeViewer tv;

	public class TVContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getChildren(Object parentElement) {
			return ((Node) parentElement).getChildren().toArray();
		}

		@Override
		public Object getParent(Object element) {
			return ((Node) element).getParent();
		}

		@Override
		public boolean hasChildren(Object element) {
			return (((Node) element).getChildren().size() > 0);
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return ((Node) inputElement).getChildren().toArray();
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
		}

	}

	public class TVLabelProvider implements ILabelProvider {
		public Image getImage(Object element) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

		public String getText(Object element) {
			String text;
			if (element instanceof TaskList)
				text = "root";
			else if (element instanceof Task)
				text = ((Task) element).getName();
			else
				text = ((Node) element).getName();
			return text;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
		}
	}

	class NameSorter extends ViewerSorter {
	}

	public SampleView() {
		// TODO Auto-generated constructor stub
	}

	public void createPartControl(Composite parent) {
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		parent.setLayout(fillLayout);
		tv = new TreeViewer(parent);
		tv.setContentProvider(new TVContentProvider());
		tv.setLabelProvider(new TVLabelProvider());
		tv.setSorter(new NameSorter());
		TaskList.initTaskList();
		root = TaskList.getTaskList();
		tv.setInput(root);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(tv.getControl(), "LittleMyLyn.TreeViewer");
		getSite().setSelectionProvider(tv);
		makeActions();
		hookContextMenu();
		contributeToActionBars();
		hookDoubleClickAction();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(tv.getControl());
		tv.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, tv);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(newTaskAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		IStructuredSelection is = tv.getStructuredSelection();
		if (!(is.getFirstElement() instanceof Task)){
			return;
		}
		manager.add(recordRelatedClassAction);
		MenuManager menuMgr = new MenuManager("Set category");
		menuMgr.add(debugAction);
		menuMgr.add(newFeatureAction);
		menuMgr.add(refactorAction);
		manager.add(menuMgr);

		// Other plug-ins can contribute there actions here
		manager.add(activateAction);
		manager.add(deactivateAction);
		manager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager arg0) {
				// TODO Auto-generated method stub
				IStructuredSelection is = tv.getStructuredSelection();
				if (!(is.getFirstElement() instanceof Task)){
					return;
				}
				Task task = (Task) is.getFirstElement();
				System.out.println(task.getState().getName());
				if (task.getState().getName().equals("activated")) {
					deactivateAction.setEnabled(true);
					activateAction.setEnabled(false);
					//manager.add(deactivateAction);
				} else if (task.getState().getName().equals("finished")) {
					deactivateAction.setEnabled(false);
					activateAction.setEnabled(true);
					//manager.add(activateAction);
				} else if (task.getState().getName().equals("new")) {
					deactivateAction.setEnabled(true);
					activateAction.setEnabled(true);
					//manager.add(activateAction);
					//manager.add(deactivateAction);
				}
				//if (!TaskList.activatedTask.equals(TaskList.nullTask))
					//manager.add(deactivateAction);
			}

		});

		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(newTaskAction);
	}

	private void makeActions() {
		// new a task
		newTaskAction = new Action() {
			public void run() {
				NewTaskFrame ntf = NewTaskFrame.getInstance();
				ntf.setVisible(true);
				ntf.setSize(300, 150);
				ntf.setLocation(300, 100);
			}
		};
		newTaskAction.setText("New Task");
		newTaskAction.setToolTipText("Create a new task");
		newTaskAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		// activate the task
		activateAction = new Action() {
			public void run() {
				if (TaskList.activatedTask.equals(TaskList.nullTask)) {
					IStructuredSelection is = tv.getStructuredSelection();
					Task task = (Task) is.getFirstElement();
					TaskList.activatedTask = task;
					task.setState("activated");
					UpdateTask.update(task.getName(), "state", "activated");
					repaint();
					tv.setExpandedState(task, true);
					for (Node property : task.getChildren()) {
						if (property.getType().equals("related"))
							tv.setExpandedState(property, true);
					}
				} else {
//					System.out.println("Activate error");
					 JOptionPane.showMessageDialog(null,"You must deactivate all the other activated tasksfirst.", "Activate error",JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		activateAction.setText("Activate");
		activateAction.setToolTipText("Activate this task");
		activateAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT));

		deactivateAction = new Action() {
			public void run() {
				TaskList.activatedTask = TaskList.nullTask;
				IStructuredSelection is = tv.getStructuredSelection();
				Task task = (Task) is.getFirstElement();
				task.setState("finished");
				UpdateTask.update(task.getName(), "state", "finished");
				repaint();
			}
		};
		deactivateAction.setText("Deactivate");
		deactivateAction.setToolTipText("Deactivate this task");
		deactivateAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_STOP));

		// modify the category of the task
		recordRelatedClassAction = new Action() {
			public void run() {
				recordRelatedClass();
			}
		};
		recordRelatedClassAction.setText("Record Related Class");
		recordRelatedClassAction.setToolTipText("Record Related Class");
		recordRelatedClassAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		debugAction = new Action() {
			public void run() {
				IStructuredSelection is = tv.getStructuredSelection();
				Task task = (Task) is.getFirstElement();
				task.setCategory("debug");
				repaint();
				UpdateTask.update(task.getName(), "category", "debug");
			}
		};
		debugAction.setText("Debug");
		debugAction.setToolTipText("Debug");
		debugAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));

		newFeatureAction = new Action() {
			public void run() {
				IStructuredSelection is = tv.getStructuredSelection();
				Task task = (Task) is.getFirstElement();
				task.setCategory("new feature");
				repaint();
				UpdateTask.update(task.getName(), "category", "new feature");
			}
		};
		newFeatureAction.setText("New feature");
		newFeatureAction.setToolTipText("New feature");
		newFeatureAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));

		refactorAction = new Action() {
			public void run() {
				IStructuredSelection is = tv.getStructuredSelection();
				Task task = (Task) is.getFirstElement();
				task.setCategory("refactor");
				repaint();
				UpdateTask.update(task.getName(), "category", "refactor");
			}
		};
		refactorAction.setText("Refactor");
		refactorAction.setToolTipText("Refactor");
		refactorAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));

		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection is = tv.getStructuredSelection();
				Node relatedClass = (Node) is.getFirstElement();
				if (relatedClass.getType().equals("class")) {
					String fName = relatedClass.getName();

					IWorkbenchPage wbPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.getActiveEditor();

					String[] stringArray = fName.split("/");
					if (stringArray.length > 2) {
						String prjName = stringArray[1];
						fName = "";
						for (int i = 2; i < stringArray.length; i++) {
							fName += "/" + stringArray[i];
						}
						IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(prjName).getFile(fName);
						try {
							if (file != null) {
								IDE.openEditor(wbPage, file);
							}
						} catch (PartInitException e) {
							//System.out.println("There is no such file");
						}
					}
				}
			}
		};
	}

	public void setFocus() {
		// TODO Auto-generated method stub
	}

	public void recordRelatedClass() {
		// Get the active class and record to a txt file
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		IEditorPart part = page.getActiveEditor();
		if (part != null) {
			IEditorInput input = part.getEditorInput();
			if (input != null) {
				String path = ((IFileEditorInput) input).getFile().getFullPath().toString();
				// Add the related class to the actived task's related list
				TaskList.activatedTask.addRelatedClass(path);
				UpdateTask.addRelatedClass(path, TaskList.activatedTask.getName());
			}
		}
	}

	public static void repaint() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				tv.setInput(TaskList.getTaskList());
			}
		});
	}

	private void hookDoubleClickAction() {
		tv.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
}
