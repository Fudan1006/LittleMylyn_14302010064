package littlemylyn.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.eclipse.swt.widgets.Display;

import littlemylyn.entity.Task;
import littlemylyn.entity.TaskList;
import littlemylyn.sql.AddTask;

public class NewTaskFrame extends JFrame {
	private String[] category = {"debug", "new feature", "refactor"};
	private String[] states = {"new", "activated", "finished"}; 
	private JLabel jlbName = new JLabel("Task Name: ");
	private JLabel jlbCategory = new JLabel("Category: ");
	private JLabel jlbState = new JLabel("State: ");
	private JTextField jtfName = new JTextField();
	private JComboBox jcbCategory = new JComboBox(category);
	private JComboBox jcbState = new JComboBox(states);
	private JButton jbt = new JButton("Submit");
	private JPanel p = new JPanel();
	private static NewTaskFrame frame;
	
	public static NewTaskFrame getInstance() {
		if (frame == null)
			frame = new NewTaskFrame();
		return frame;
	}
	
	private NewTaskFrame() {
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
				String name = jtfName.getText();
				String cate = category[jcbCategory.getSelectedIndex()];
				String state = states[jcbState.getSelectedIndex()];
				Task task = new Task(name, state, cate);
				if (state.equals("activated")) {
					if (TaskList.activatedTask.name.equals("null")) {
						TaskList.activatedTask = task;
					} else {
						JOptionPane.showMessageDialog(null,
								"You must deactivate all the other activated tasks first.",
								"New task error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}				
				TaskList.getTaskList().addChild(task);	
				//store to database
				SampleView.repaint();
				frame.dispose();
				jtfName.setText("");
			}			
		});
		add(p);
	}
} 
