package littlemylyn.views;
 
import javax.swing.JOptionPane;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider; 
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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import littlemylyn.entity.Node;
import littlemylyn.entity.Task;
import littlemylyn.entity.TaskList;

public class SampleView extends ViewPart {

	public static final String ID = "littlemylyn.views.SampleView";
	private Action newTaskAction;
	private Action recordRelatedClassAction;
    private Action debugAction;
    private Action newFeatureAction;
    private Action refactorAction;
	private Action activateAction;
	private Action deactivateAction;
	
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
		root = TaskList.getTaskList();
		tv.setInput(root);
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(tv.getControl(), "LittleMyLyn.TreeViewer");
		getSite().setSelectionProvider(tv);
		makeActions();
		hookContextMenu();
		contributeToActionBars();
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
		//manager.add(new Separator());
		//manager.add(recordRelatedClassAction);
	}

	private void fillContextMenu(IMenuManager manager) {
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
				Task task = (Task)is.getFirstElement();
				if (task.getState().getName().equals("activated")) {
					deactivateAction.setEnabled(true);
					activateAction.setEnabled(false);
				} else if (task.getState().getName().equals("finished")){
					deactivateAction.setEnabled(false);
					activateAction.setEnabled(true);
				} else if (task.getState().getName().equals("new")) {
					deactivateAction.setEnabled(true);
					activateAction.setEnabled(true);
				}
			}
			
		});

		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(newTaskAction);
		//manager.add(recordRelatedClassAction);
	}

	private void makeActions() {
		//new a task
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

		//activate the task
		activateAction = new Action() {
			public void run() {
				if (TaskList.activatedTask.getName().equals("null")) {
					IStructuredSelection is = tv.getStructuredSelection();
					Task task = (Task)is.getFirstElement();
					TaskList.activatedTask = task;
					task.setState("activated");
					repaint();
				} else {
					JOptionPane.showMessageDialog(null,"You must deactivate all the other activated tasks first.", "Activate error", JOptionPane.ERROR_MESSAGE);
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
				Task task = (Task)is.getFirstElement();
				task.setState("finished");
				repaint();				
			}
		};
		deactivateAction.setText("Deactivate");
		deactivateAction.setToolTipText("Deactivate this task");
		deactivateAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_STOP)); 
		
		//modify the category of the task
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
				if (TaskList.activatedTask.getName().equals("null")) {
					IStructuredSelection is = tv.getStructuredSelection();
					Task task = (Task)is.getFirstElement();
					task.setCategory("debug");
					repaint();
				}
			}
		};
		debugAction.setText("Debug");
		debugAction.setToolTipText("Debug");
		debugAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		
		newFeatureAction = new Action() {
			public void run() {
				if (TaskList.activatedTask.getName().equals("null")) {
					IStructuredSelection is = tv.getStructuredSelection();
					Task task = (Task)is.getFirstElement();
					task.setCategory("new feature");
					repaint();
				}
			}
		};
		newFeatureAction.setText("New feature");
		newFeatureAction.setToolTipText("New feature");
		newFeatureAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		
		refactorAction = new Action() {
			public void run() {
				if (TaskList.activatedTask.getName().equals("null")) {
					IStructuredSelection is = tv.getStructuredSelection();
					Task task = (Task)is.getFirstElement();
					task.setCategory("refactor");
					repaint();
				}
			}
		};
		refactorAction.setText("Refactor");
		refactorAction.setToolTipText("Refactor");
		refactorAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
	}
	
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	public void recordRelatedClass(){
    //Get the active class and record to a txt file 
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		IEditorPart part = page.getActiveEditor(); 
		IEditorInput input = part.getEditorInput();
		System.out.println("The actived class name: " + input.getName());
		String path = ((IFileEditorInput)input).getFile().getFullPath().toString();
		System.out.println("The actived class path: " + path);
    //TODO Add the related class to the actived task's related list
		//if(TaskList.activedTask.addRelatedClass(path) == 1){
	    //}
		//else{
		// //-1
		//}
	}
	public static void repaint() {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	tv.setInput(TaskList.getTaskList());
		    }
		}); 
	}
}
