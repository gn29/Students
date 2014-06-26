package ru.gn29.students;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindStudentView extends JFrame {
	
	ResultCallBack r;
	JFrame mainFrame;
	JTextField name;
	JTextField secondName;
	JTextField surname;
	JTextField birthday;
	JTextField facultet;
	JTextField groupNumber;
	
	int size = 20;	
	
	private FindStudentView(ResultCallBack r) {
		this.r = r;
		mainFrame = this;
		setSize(new Dimension(500, 200));
		this.setResizable(true);
		setTitle("Найти студента");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		add(p);
		
		name = new JTextField(size);
		secondName = new JTextField(size);
		surname = new JTextField(size);
		
		JButton addButton = new JButton("Найти");
		addButton.addActionListener(new ButtonClickListener());
		
		JPanel pName = new JPanel();
		pName.setLayout(new FlowLayout());
		pName.add(new JLabel("Фамилия"));
		pName.add(name);
		p.add(pName);
		
		
		pName = new JPanel();
		pName.setLayout(new FlowLayout());
		pName.add(new JLabel("Имя"));
		pName.add(secondName);
		p.add(pName);
		
		pName = new JPanel();
		pName.setLayout(new FlowLayout());
		pName.add(new JLabel("Отчество"));
		pName.add(surname);
		p.add(pName);

		pName = new JPanel();
		pName.setLayout(new FlowLayout());
		pName.add(addButton);
		p.add(pName);
		
		setVisible(true);
		
	}
	
	public static void showView(ResultCallBack r) {
		new FindStudentView(r);
	}
	
	class ButtonClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Properties p = new Properties();
			p.setProperty(StudentController.ACTION, StudentController.ACTION_FIND);
			p.setProperty(Student.NAME, name.getText());
			p.setProperty(Student.SECOND_NAME, secondName.getText());
			p.setProperty(Student.SURNAME, surname.getText());
			mainFrame.setVisible(false);
			mainFrame.dispose();
			r.onResult(p);			
			
		}
	}
	

}
