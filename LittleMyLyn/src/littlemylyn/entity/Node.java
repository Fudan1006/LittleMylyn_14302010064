package littlemylyn.entity;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name ;
    private Node parent ;
    private ArrayList<Node> children = new ArrayList<Node>();
    private boolean isClass = false;

    Node() {}  

    Node(Node parent) {
       this .setParent(parent);
    }

    public String getName() {
       return name ;
    }

    public void setName(String name) {
       this . name = name;
    } 

    public Node getParent() {
       return parent ;
    }

    public void setParent(Node parent) {
       this . parent = parent;
    }
   
    public void addChild(Node child) {
       child.setParent( this );
       children .add(child);
    }
  
    public void removeChild(Node child) {
       children .remove(child);
    }

    public ArrayList<Node> getChildren() {
       return children;
    }

	public boolean isClass() {
		return isClass;
	}

	public void setClass(boolean isClass) {
		this.isClass = isClass;
	}
    
    

}