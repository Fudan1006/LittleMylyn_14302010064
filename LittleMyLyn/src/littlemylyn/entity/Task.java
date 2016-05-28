package littlemylyn.entity;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.widgets.Display;

import littlemylyn.views.SampleView;

public class Task extends Node{
	public String name;
	private Node state = new Node();
	private Node category = new Node();
	private Node relatedClass = new Node();
	
	public Task(String n, String s, String c) {
		name = n;
		this.setName(n);
		state.setName(s);
		category.setName(c);
		relatedClass.setName("Related Class("+relatedClass.getChildren().size()+")");
		this.addChild(state);
		this.addChild(category);
		this.addChild(relatedClass);
	}

	public void setState(Node state) {
		this.state = state;
	}

	public void setCategory(Node category) {
		this.category = category;
	}
	
	public int addRelatedClass(String filepath) {
		if (this.getName() == "null" && state.getName() == "null" && category.getName() == "null")
			return -1;
		Node rclass = new Node();
		rclass.setName(filepath);
		relatedClass.addChild(rclass);
		SampleView.repaint();
		return 1;
	}

}
