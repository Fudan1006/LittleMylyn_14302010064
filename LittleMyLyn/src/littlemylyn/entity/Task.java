package littlemylyn.entity;

import org.eclipse.jface.viewers.TreeNode;

public class Task extends Node{
	public Node name = new Node();
	public Node state = new Node();
	public Node category = new Node();
	public Node relatedClass = new Node();
	
	public Task(String n, String s, String c) {
		name.setName(n);
		state.setName(s);
		category.setName(c);
		relatedClass.setName("Related Class("+relatedClass.getChildren().size()+")");
		name.addChild(state);
		name.addChild(category);
		name.addChild(relatedClass);
	}

}
