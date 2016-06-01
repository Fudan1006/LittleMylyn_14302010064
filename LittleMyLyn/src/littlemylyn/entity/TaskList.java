package littlemylyn.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import littlemylyn.sql.Initialize;

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
		}
		return root;
	}

	public static void initTaskList() {
		if(root != null){
			return;
		}
		ArrayList<Task> tl = Initialize.load();
		System.out.println("in init" + tl);
		for (int i = 0; i < tl.size(); i++) {
			getTaskList().addChild(tl.get(i));
			if (tl.get(i).getState().getName().equals("activated"))
				activatedTask = tl.get(i);
		}
	}
}