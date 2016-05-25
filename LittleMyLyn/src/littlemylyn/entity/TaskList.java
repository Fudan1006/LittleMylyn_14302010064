package littlemylyn.entity;

public class TaskList extends Node {
	private static TaskList root = null;

	private TaskList() {
	}

	public static TaskList getTaskList() {
		if (root == null) {
			root = new TaskList();
			root.setParent(null);
			root.initTaskList();
		}
		return root;
	}

	private void initTaskList() {
		Task task = new Task("try a", "1", "1");
		root.addChild(task.name);
		Task a = new Task("try b", "2", "2");
		root.addChild(a.name);
		Task b = new Task("try c", "3", "3");
		root.addChild(b.name);
	}
}