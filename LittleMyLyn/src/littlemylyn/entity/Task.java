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

	public Node getState() {
		return state;
	}

	public void setState(String state) {
		this.state.setName(state);
	}

	public void setCategory(String category) {
		this.category.setName(category);
	}
	
	public int addRelatedClass(String filepath) {
		if (this.getName().equals("null")
				&& state.getName().equals("null")
				&& category.getName().equals("null"))
			return -1;
		Node rclass = new Node();
		rclass.setName(filepath);
		relatedClass.addChild(rclass);
		SampleView.repaint();
		return 1;
	}

}
