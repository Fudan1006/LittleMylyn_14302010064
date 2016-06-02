package littlemylyn.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import littlemylyn.entity.Node;
import littlemylyn.entity.Task;
import littlemylyn.entity.TaskList;

public class AddTask {
	public static void add(Task task) {     	
    	String url = "jdbc:mysql://localhost:3306/LittleMyLyn";
        String user = "root";  
        String password = "wxsql";
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
	
	//chech whether the name is duplicate, return false if duplicate
	public static boolean checkDupilcate(String name){
		String url = "jdbc:mysql://localhost:3306/LittleMyLyn";  
        String user = "root";  
        String password = "wxsql";
        Connection con;   
       
        try {   
            con = DriverManager.getConnection(url, user, password); 
            
            Statement stmCheck = con.createStatement();
            String sqlCheck = "select `name` from `tasklist` where `name`='" + name  + "'";
			ResultSet rs = stmCheck.executeQuery(sqlCheck);
			if (rs.next()) {
				 stmCheck.close();
		         con.close();
				 return false;
			}
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		return true;
	}
}
