package ru.gn29.students.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ru.gn29.students.AddStudentView;
import ru.gn29.students.Repository;
import ru.gn29.students.Student;
import ru.gn29.students.StudentController;

public class SouthPanel extends JPanel {
	
	JButton addButton;
	JButton deleteButton;
	
	public SouthPanel() {
		
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		setLayout(layout);
		
		// Add button
		addButton = new JButton("Добавить");
		addButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						AddStudentView.showView(StudentController.get());
					}
				}
				);
		add(addButton);
		
		// Delete button
		deleteButton = new JButton("Удалить");
		deleteButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {						
						int confirm = JOptionPane.showConfirmDialog(deleteButton, 
								"Удалить?", 
								"Подтверждение удаления", 
								JOptionPane.YES_NO_OPTION);
							if(confirm == JOptionPane.YES_OPTION) {
								JList panel = SplitLeftPanel.get();
								Student s = (Student)panel.getSelectedValue();
								Repository r = StudentController.get();
								r.deleteStudent(s);		
							}
					}
				}
				);		
		add(deleteButton);		
		
	}
}
