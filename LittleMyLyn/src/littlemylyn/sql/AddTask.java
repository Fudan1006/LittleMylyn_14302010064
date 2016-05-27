package littlemylyn.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import littlemylyn.entity.Task;

public class AddTask {  
    public static void add(Task task) { 
    	try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
    	
    	String url = "jdbc:mysql://127.0.0.1/LittleMyLyn";  
        String user = "root";  
        String password = "270329zuki";
        Connection con;   
        
        try {   
            con = DriverManager.getConnection(url, user, password);  
            Statement stm = con.createStatement();
            String sql = "insert into `tasklist` (`name`, `category`, `state`) values ('"
            		+ task.name.getName() +"','"+ task.category.getName()
            		+ "','" + task.state.getName() +"')";
            stm.execute(sql);
            stm.close();
            con.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}
