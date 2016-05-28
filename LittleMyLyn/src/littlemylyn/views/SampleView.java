//package littlemylyn.views;
//
//
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.ui.part.*;
//
//import littlemylyn.entity.TaskList;
//
//import org.eclipse.jface.viewers.*;
//import org.eclipse.swt.graphics.Image;
//import org.eclipse.jface.action.*;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.ui.*;
//import org.eclipse.swt.widgets.Menu;
//import org.eclipse.swt.SWT;
//
//
///**
// * This sample class demonstrates how to plug-in a new
// * workbench view. The view shows data obtained from the
// * model. The sample creates a dummy model on the fly,
// * but a real implementation would connect to the model
// * available either in this or another plug-in (e.g. the workspace).
// * The view is connected to the model using a content provider.
// * <p>
// * The view uses a label provider to define how model
// * objects should be presented in the view. Each
// * view can present the same model objects using
// * different labels and icons, if needed. Alternatively,
// * a single label provider can be shared between views
// * in order to ensure that objects of the same type are
// * presented in the same way everywhere.
// * <p>
// */
//
//public class SampleView extends ViewPart {
//
//	/**
//	 * The ID of the view as specified by the extension.
//	 */
//	public static final String ID = "littlemylyn.views.SampleView";
//	private static TaskList tl = new TaskList();
//
//	private TreeViewer viewer;
//	private Action action1;
//	private Action action2;
//	private Action doubleClickAction;
//
//	/*
//	 * The content provider class is responsible for
//	 * providing objects to the view. It can wrap
//	 * existing objects in adapters or simply return
//	 * objects as-is. These objects may be sensitive
//	 * to the current input of the view, or ignore
//	 * it and always show the same content 
//	 * (like Task List, for example).
//	 */
//	 
////	class ViewContentProvider implements IStructuredContentProvider {
////		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
////		}
////		public void dispose() {
////		}
////		public Object[] getElements(Object parent) {
////			int size = tl.taskList.size();
////			String[] tasks = new String[size];
////			for (int i = 0; i < size; i++) {
////				tasks[i] = tl.taskList.get(i).getName();
////			}
////			//return tasks;
////			return tl.taskList.toArray();
////			//return new Object[] { "One", "One","One"};
////		}
////	}
//
//	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
//		public String getColumnText(Object obj, int index) {
//			return getText(obj);
//		}
//		public Image getColumnImage(Object obj, int index) {
//			return getImage(obj);
//		}
//		public Image getImage(Object obj) {
//			return PlatformUI.getWorkbench().
//					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
//		}
//	}
//	class NameSorter extends ViewerSorter {
//	}
//
//	/**
//	 * The constructor.
//	 */
//	public SampleView() {
//	}
//
//	/**
//	 * This is a callback that will allow us
//	 * to create the viewer and initialize it.
//	 */
//	public void createPartControl(Composite parent) {
//		viewer = new TreeViewer(parent);
//		viewer.setContentProvider(new ProjectViewr());
//		viewer.setLabelProvider(new ViewLabelProvider());
//		viewer.setSorter(new NameSorter());
//		//viewer.setInput(getViewSite());
//		viewer.setInput(TaskList.root); 
//
//		// Create the help context id for the viewer's control
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "LittleMyLyn.viewer");
//		getSite().setSelectionProvider(viewer);
//		makeActions();
//		hookContextMenu();
//		hookDoubleClickAction();
//		contributeToActionBars();
//	}
//
//	private void hookContextMenu() {
//		MenuManager menuMgr = new MenuManager("#PopupMenu");
//		menuMgr.setRemoveAllWhenShown(true);
//		menuMgr.addMenuListener(new IMenuListener() {
//			public void menuAboutToShow(IMenuManager manager) {
//				SampleView.this.fillContextMenu(manager);
//			}
//		});
//		Menu menu = menuMgr.createContextMenu(viewer.getControl());
//		viewer.getControl().setMenu(menu);
//		getSite().registerContextMenu(menuMgr, viewer);
//	}
//
//	private void contributeToActionBars() {
//		IActionBars bars = getViewSite().getActionBars();
//		fillLocalPullDown(bars.getMenuManager());
//		fillLocalToolBar(bars.getToolBarManager());
//	}
//
//	private void fillLocalPullDown(IMenuManager manager) {
//		manager.add(action1);
//		manager.add(new Separator());
//		manager.add(action2);
//	}
//
//	private void fillContextMenu(IMenuManager manager) {
//		manager.add(action1);
//		manager.add(action2);
//		// Other plug-ins can contribute there actions here
//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
//	}
//	
//	private void fillLocalToolBar(IToolBarManager manager) {
//		manager.add(action1);
//		manager.add(action2);
//	}
//
//	private void makeActions() {
//		action1 = new Action() {
//			public void run() {
//				NewTaskFrame ntf = new NewTaskFrame();
//				ntf.setVisible(true);
//				ntf.setSize(300,150);
//				ntf.setLocation(300, 100);
//			}
//		};
//		action1.setText("New Task");
//		action1.setToolTipText("Create a new task");
//		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
//			getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
//		
//		action2 = new Action() {
//			public void run() {
//				showMessage("Action 2 executed");
//			}
//		};
//		action2.setText("Action 2");
//		action2.setToolTipText("Action 2 tooltip");
//		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
//				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
//		doubleClickAction = new Action() {
//			public void run() {
//				ISelection selection = viewer.getSelection();
//				Object obj = ((IStructuredSelection)selection).getFirstElement();
//				showMessage("Double-click detected on "+obj.toString());
//			}
//		};
//	}
//
//	private void hookDoubleClickAction() {
//		viewer.addDoubleClickListener(new IDoubleClickListener() {
//			public void doubleClick(DoubleClickEvent event) {
//				doubleClickAction.run();
//			}
//		});
//	}
//	private void showMessage(String message) {
//		MessageDialog.openInformation(
//			viewer.getControl().getShell(),
//			"Sample View",
//			message);
//	}
//
//	/**
//	 * Passing the focus request to the viewer's control.
//	 */
//	public void setFocus() {
//		viewer.getControl().setFocus();
//	}
//}
package littlemylyn.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
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
	private Action action1;
	private Action action2;
	private Action action3;
    private Action action4;
    private Action action5;
    private Action action6;
	
	private Node root;
	private TreeViewer tv;

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
				text = ((Task) element).name.getName();
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
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(action3);
		MenuManager menuMgr = new MenuManager("Set category");
		menuMgr.add(action4);
		menuMgr.add(action5);
		menuMgr.add(action6);
		manager.add(menuMgr);
		
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				NewTaskFrame ntf = new NewTaskFrame();
				ntf.setVisible(true);
				ntf.setSize(300, 150);
				ntf.setLocation(300, 100);
			}
		};
		action1.setText("New Task");
		action1.setToolTipText("Create a new task");
		action1.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));

		action2 = new Action() {
			public void run() {
				//showMessage("Action 2 executed");
				recordRelatedClass();
			}
		};
		action2.setText("Record Related Class");
		action2.setToolTipText("Record Related Class");
		action2.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action3 = new Action() {
			public void run() {
				ISelection selection = tv.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				Node task = ((Node)obj);
				System.out.println("The task's state is finished: " + task.getName());
				//task.setState("finished");
			}
		};
		action3.setText("Finished");
		action3.setToolTipText("Finished");
		action3.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		
		action4 = new Action() {
			public void run() {
				ISelection selection = tv.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				Node task = ((Node)obj);
				System.out.println("The task's category is debug: " + task.getName());
				//task.setState("debug");
			}
		};
		action4.setText("Debug");
		action4.setToolTipText("Debug");
		action4.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		
		action5 = new Action() {
			public void run() {
				ISelection selection = tv.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				Node task = ((Node)obj);
				System.out.println("The task's category is new feature: " + task.getName());
				//task.setState("new feature");
			}
		};
		action5.setText("New feature");
		action5.setToolTipText("New feature");
		action5.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		
		action6 = new Action() {
			public void run() {
				ISelection selection = tv.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				Node task = ((Node)obj);
				System.out.println("The task's category is refactor: " + task.getName());
				//task.setState("refactor");
			}
		};
		action6.setText("Refactor");
		action6.setToolTipText("Refactor");
		action6.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		
	}
	

	private void showMessage(String message) {
		MessageDialog.openInformation(tv.getControl().getShell(), "Sample View", message);
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
}
