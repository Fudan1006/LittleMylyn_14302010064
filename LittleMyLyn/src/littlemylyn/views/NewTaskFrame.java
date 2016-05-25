package littlemylyn.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import littlemylyn.entity.Task;
import littlemylyn.entity.TaskList;

public class NewTaskFrame extends JFrame {
	private String[] category = {"debug", "new feature", "refactor"};
	private String[] state = {"new", "activated", "finished"}; 
	private JLabel jlbName = new JLabel("Task Name: ");
	private JLabel jlbCategory = new JLabel("Category: ");
	private JLabel jlbState = new JLabel("State: ");
	private JTextField jtfName = new JTextField();
	private JComboBox jcbCategory = new JComboBox(category);
	private JComboBox jcbState = new JComboBox(state);
	private JButton jbt = new JButton("Submit");
	private JPanel p = new JPanel();
	
	NewTaskFrame() {
		p.setLayout(new GridLayout(0,2,5,5));
		p.add(jlbName);
		p.add(jtfName);
		p.add(jlbCategory);
		p.add(jcbCategory);
		p.add(jlbState);
		p.add(jcbState);
		p.add(jbt);
		
		jbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String name = jlbName.getText();
				String cate = jlbCategory.getText();
				String state = jlbState.getText();
				Task task = new Task(name, state, cate);
				TaskList.getTaskList().addChild(task);
				SampleView.repaint(TaskList.getTaskList());	
				((JFrame)(jbt.getParent().getParent())).dispose();
			}			
		});
		add(p);
	}
}
