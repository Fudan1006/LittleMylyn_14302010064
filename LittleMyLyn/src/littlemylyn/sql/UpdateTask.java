package littlemylyn.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import littlemylyn.entity.Node;
import littlemylyn.entity.Task;

public class UpdateTask {
	public static void addRelatedClass(String filepath, String name) {
		String url = "jdbc:mysql://localhost:3306/LittleMyLyn";
		String user = "root";
		String password = "wxsql";
		Connection con;

		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stm = con.createStatement();
			String sql = "select `relatedclass` from `tasklist` where `name`='" + name + "'";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				String old = rs.getString("relatedclass");
				if (old != null && old.contains(" ")) {
					String[] files = old.split(" ");
					for (String temp : files) {
						if (temp.equals(filepath)) {
							stm.close();
							con.close();
							return;
						}

					}
				}
				String related = old + " " + filepath;
				if(old.equals(" ")){
					related = filepath;
				}
				sql = "update `tasklist` set `relatedclass`='" + related + "' where `name`='" + name + "'";
				stm.execute(sql);
			}
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(String task, String key, String val) {
		String url = "jdbc:mysql://localhost:3306/LittleMyLyn";
		String user = "root";
		String password = "wxsql";
		Connection con;

		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stm = con.createStatement();
			String sql = "update `tasklist` set `" + key + "`='" + val + "' where `name`='" + task + "'";
			stm.execute(sql);
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
