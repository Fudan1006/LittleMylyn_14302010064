package littlemylyn.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import littlemylyn.entity.Node;
import littlemylyn.entity.Task;
import littlemylyn.entity.TaskList;

public class SaveTask {
    public static void save() {     	
    	String url = "jdbc:mysql://localhost:3306/LittleMyLyn";  
        String user = "root";  
        String password = "270329zuki";
        Connection con;   
        if (TaskList.getTaskList() == null)
        	return;
        ArrayList<Node> tasklist = TaskList.getTaskList().getChildren();
        
        try {   
            con = DriverManager.getConnection(url, user, password);  
            Statement stm = con.createStatement();
            for (int i = 0; i < tasklist.size(); i++) {
            	Node task = tasklist.get(i);
            	ArrayList<Node> child = task.getChildren();
            	String category = "";
            	String state = "";
            	String classes = "";
            	Node relatedclass = null;
            	for (int j = 0; j < child.size(); j++) {
            		String temp = child.get(j).getType();
            		if (temp.equals("state"))
            			state = child.get(j).getName();
            		else if (temp.equals("category"))
            			category = child.get(j).getName();
            		else if (temp.equals("related"))
            			relatedclass = child.get(j);            			
            	}
            	if (relatedclass != null) {
            		child = relatedclass.getChildren();
            		for (int j = 0; j < child.size(); j++) {
            			classes += child.get(j).getName() + " ";
            		}
            	} else {
            		classes = "null";
            	}
            	String sql = "insert into `tasklist` (`name`, `category`, `state`, `relatedclass`) values ('"
                		+ task.getName() +"','"+ category + "','" + state + "','" + classes.trim() +"')";
                stm.execute(sql);
            }            
            stm.close();
            con.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}
