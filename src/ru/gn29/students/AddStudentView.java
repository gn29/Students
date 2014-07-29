package ru.gn29.students;

import javax.swing.*;

import ru.gn29.students.view.SplitViewStart;

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
		
		setSize(new Dimension(500, 260));
		setResizable(false);
		setTitle("Добавить студента");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		
		JPanel p = new JPanel();
		GridLayout mainLayout = new GridLayout(7, 1);
		mainLayout.setVgap(0);
		p.setLayout(mainLayout);
		add(p);
		
		name = new JTextField(size);
		secondName = new JTextField(size);
		surname = new JTextField(size);
		birthday = new JTextField(size);
		facultet = new JTextField(size);
		groupNumber = new JTextField(size);
		
		JButton addButton = new JButton("Добавить");
		addButton.addActionListener(new ButtonClickListener());
		
		FlowLayout itemLayout = new FlowLayout();
		itemLayout.setAlignment(FlowLayout.LEFT);
		
		JPanel pName = new JPanel();
		pName.setLayout(itemLayout);
		pName.add(new JLabel("Фамилия"));
		pName.add(name);
		p.add(pName);
		
		
		pName = new JPanel();
		pName.setLayout(itemLayout);
		pName.add(new JLabel("Имя"));
		pName.add(secondName);
		p.add(pName);
		
		pName = new JPanel();
		pName.setLayout(itemLayout);
		pName.add(new JLabel("Отчество"));
		pName.add(surname);
		p.add(pName);

		pName = new JPanel();
		pName.setLayout(itemLayout);
		pName.add(new JLabel("Дата рождения (дд.мм.гггг)"));
		pName.add(birthday);
		p.add(pName);

		pName = new JPanel();
		pName.setLayout(itemLayout);
		pName.add(new JLabel("Факультет"));
		pName.add(facultet);
		p.add(pName);
		
		pName = new JPanel();
		pName.setLayout(itemLayout);
		pName.add(new JLabel("Номер группы"));
		pName.add(groupNumber);
		p.add(pName);

		pName = new JPanel();
		pName.setLayout(itemLayout);
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
