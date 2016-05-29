package littlemylyn.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.widgets.Display;

import littlemylyn.sql.UpdateTask;
import littlemylyn.views.SampleView;

public class Task extends Node {
	private Node state = new Node();
	private Node category = new Node();
	private Node relatedClass = new Node();
	
	public Task(String n, String s, String c) {
		this.setName(n);
		this.setType("task");
		state.setName(s);
		state.setType("state");
		category.setName(c);
		category.setType("category");
		relatedClass.setName("Related Class("+relatedClass.getChildren().size()+")");
		relatedClass.setType("related");
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

	public Node getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category.setName(category);
	}
	
	public Node getRelatedClass() {
		return relatedClass;
	}

	public int addRelatedClass(String filepath) {
		if (this.getName().equals("null")
				&& state.getName().equals("null")
				&& category.getName().equals("null"))
			return -1;
		Node rclass = new Node();
		rclass.setName(filepath);
		rclass.setType("class");
		ArrayList<Node> children = relatedClass.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getName().equals(filepath))
				return 1;
		}
		relatedClass.addChild(rclass);
		relatedClass.setName("Related Class("+relatedClass.getChildren().size()+")");
		UpdateTask.addRelatedClass(filepath, getName());
		SampleView.repaint();
		return 1;
	}

	public void initRelatedClass(String filepath) {
		Node rclass = new Node();
		rclass.setName(filepath);
		rclass.setType("class");
		relatedClass.addChild(rclass);
		relatedClass.setName("Related Class("+relatedClass.getChildren().size()+")");
	}
}

