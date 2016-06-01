package littlemylyn.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import littlemylyn.entity.Task;

public class Initialize {
	public static ArrayList<Task> load() {
		String url = "jdbc:mysql://localhost:3306/LittleMyLyn";
		String user = "root";
		String password = "wxsql";
		Connection con;
		ResultSet rs;
		ArrayList<Task> all = new ArrayList<Task>();
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stm = con.createStatement();
			String sql = "select * from `tasklist`";
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Task task = new Task(rs.getString("name"), rs.getString("state"), rs.getString("category"));
				String file = rs.getString("relatedclass");
				if (file == null) {
					all.add(task);
					continue;
				}
				if (file.contains(" ")) {
					String[] filepath = file.split(" ");
					for (int i = 0; i < filepath.length; i++){
						if(!filepath[i].equals(""))
						task.initRelatedClass(filepath[i]);
					}
				}
				else{
					task.initRelatedClass(file);
				}
				all.add(task);

			}
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
}
