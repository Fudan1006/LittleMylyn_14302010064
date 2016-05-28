package littlemylyn.entity;

public class TaskList extends Node {
	private static TaskList root = null;
	private static Task nullTask = new Task("null", "null", "null");
	public static Task activatedTask = nullTask;

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
	}

}