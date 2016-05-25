package littlemylyn.entity;

import org.eclipse.jface.viewers.TreeNode;

public class Task extends Node{
	public Node name = new Node();
	public Node state = new Node();
	//public static String[] states = {"new", "activated", "finished"};
	public Node category = new Node();
	//public static String[] categories = {"debug", "new feature", "refactor"}; 
	public Node relatedClass = new Node();
	
	Task(String n, String s, String c) {
		name.setName(n);
		state.setName(s);
		category.setName(c);
		name.addChild(state);
		name.addChild(category);
		name.addChild(relatedClass);
	}

}
