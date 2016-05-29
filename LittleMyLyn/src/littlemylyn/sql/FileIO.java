package littlemylyn.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.json.JSONObject;

import littlemylyn.entity.Task;

public class FileIO {
	private static String fileName = "data.txt";
	
	public static void main(String[] args) {
		Task a = new Task("try a", "1", "1");
		ArrayList<Task> tl = new ArrayList<Task>();
		tl.add(a);
		Task b = new Task("try b", "1", "1");
		tl.add(b);
		Task c = new Task("try b", "1", "1");
		tl.add(c);
		writeFile(tl);
	}
	
	public static ArrayList<Task> readFile() {
		ArrayList<Task> alltask = new ArrayList<Task>();
		try {
			String encoding = "UTF-8";
			File file = new File(fileName);
			boolean bool = file.createNewFile();
			if (file.isFile() && file.exists()) {
				InputStreamReader read;
				read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String task = null;
				while ((task = bufferedReader.readLine()) != null) {
					
				}
			}
		} catch (Exception e) {
			System.out.println("没有该文件");
		}

		return alltask;

	}

	public static void writeFile(ArrayList<Task> tasklist) {		     
        try {  
        	FileOutputStream fo = new FileOutputStream(fileName);   		  
            ObjectOutputStream so = new ObjectOutputStream(fo);
            so.writeObject(tasklist.get(1)); 
            so.writeObject(tasklist.get(2)); 
            so.close();     
        } catch (IOException e) {   
            System.out.println(e);   
        }   
	}
}
