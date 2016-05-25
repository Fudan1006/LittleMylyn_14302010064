package littlemylyn.views;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewTaskFrame extends JFrame {
	private String[] category = {"debug", "new feature", "refactor"};
	private JLabel jlbName = new JLabel("Task Name: ");
	private JLabel jlbCategory = new JLabel("Category: ");
	private JTextField jtfName = new JTextField();
	private JComboBox jcbCategory = new JComboBox(category);
	private JPanel p = new JPanel();
	NewTaskFrame() {
		p.setLayout(new GridLayout(0,2));
		p.add(jlbName);
		p.add(jtfName);
		p.add(jlbCategory);
		p.add(jcbCategory);
		add(p);
	}
}
