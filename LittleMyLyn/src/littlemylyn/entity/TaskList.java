package littlemylyn.entity;

import java.util.ArrayList;

import org.json.JSONObject;

public class TaskList extends Node {
	private static TaskList root = null;
	public static Task nullTask = new Task("null", "null", "null");
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

	public void initTaskList() {
//		ArrayList<JSONObject> tasklist = IO.readfile();
//		for (int i = 0; i < tasklist.size(); i++) {
//			JSONObject task = tasklist.get(i);
//			task.getString("")
//		}
	}

}