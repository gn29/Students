package ru.gn29.students;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentView extends JFrame {
	ResultCallBack r;
	JFrame mainFrame;
	JTextField name;
	JTextField secondName;
	JTextField surname;
	JTextField birthday;
	JTextField facultet;
	JTextField groupNumber;
	
	int size = 20;	
	
	private AddStudentView(ResultCallBack r) {
		this.r = r;
		mainFrame = this;
		setSize(new Dimension(500, 320));
		this.setResizable(true);
		setTitle("Добавить студента");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		add(p);
		
		name = new JTextField(size);
		secondName = new JTextField(size);
		surname = new JTextField(size);
		birthday = new JTextField(size);
		facultet = new JTextField(size);
		groupNumber = new JTextField(size);
		
		JButton addButton = new JButton("Добавить");
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
		pName.add(new JLabel("Дата рождения (дд.мм.гггг)"));
		pName.add(birthday);
		p.add(pName);

		pName = new JPanel();
		pName.setLayout(new FlowLayout());
		pName.add(new JLabel("Факультет"));
		pName.add(facultet);
		p.add(pName);
		
		pName = new JPanel();
		pName.setLayout(new FlowLayout());
		pName.add(new JLabel("Номер группы"));
		pName.add(groupNumber);
		p.add(pName);

		pName = new JPanel();
		pName.setLayout(new FlowLayout());
		pName.add(addButton);
		p.add(pName);
		
		setVisible(true);
		
	}
	
	public static void showView(ResultCallBack r) {
		new AddStudentView(r);
	}
	
	class ButtonClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Properties p = new Properties();
			p.setProperty(StudentController.ACTION, StudentController.ACTION_ADD);
			p.setProperty(Student.NAME, name.getText());
			p.setProperty(Student.SECOND_NAME, secondName.getText());
			p.setProperty(Student.SURNAME, surname.getText());
			p.setProperty(Student.BIRTHDAY, birthday.getText());
			p.setProperty(Student.FACULTET, facultet.getText());
			p.setProperty(Student.GROUP_NUMBER, groupNumber.getText());
			mainFrame.setVisible(false);
			mainFrame.dispose();
			r.onResult(p);		
			
		}
	}
	

}
