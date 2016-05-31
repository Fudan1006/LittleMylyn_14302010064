package littlemylyn.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import littlemylyn.entity.Node;
import littlemylyn.entity.Task;
import littlemylyn.entity.TaskList;

public class AddTask {
	public static void add(Task task) {     	
    	String url = "jdbc:mysql://localhost:3306/LittleMyLyn";  
        String user = "root";  
        String password = "270329zuki";
        Connection con;   
       
        try {   
            con = DriverManager.getConnection(url, user, password);  
            Statement stm = con.createStatement();
            String sql = "insert into `tasklist` (`name`, `category`, `state`) values ('"
            		+ task.getName() +"','"+ task.getCategory().getName()
            		+ "','" + task.getState().getName() +"')";
            stm.execute(sql);
            stm.close();
            con.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
