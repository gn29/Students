package ru.gn29.students.view;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ru.gn29.students.Student;

public class SplitRightPanel extends JPanel implements ListSelectionListener {
	
	public static SplitRightPanel panel = null;
	JTextArea textArea;
	private boolean onKeyUp = false;
	private int index = 0;
	
	private SplitRightPanel() {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		setLayout(layout);
		textArea = new JTextArea();
//		textArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		textArea.setEditable(false);
		textArea.setOpaque(false);
//		textArea.setBackground(new Color(0,0,0,0));
		textArea.setText("Выберете студента из списка справа");
		add(textArea);
	}
	
	public static SplitRightPanel get() {
		if (panel == null) {
			panel = new SplitRightPanel();
		}
		return panel;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (onKeyUp) {
			if (e.getSource() != null) {				
				int first = e.getFirstIndex();
				int last = e.getLastIndex();
				if (first == last) {
					index = first;
				} else if (first == index) {
					index = last;
				} else {
					index = first;
				}
				
				SplitLeftPanel leftPanel = (SplitLeftPanel)e.getSource();
				Student s = (Student)leftPanel.getSelectedValue();
				if(s != null) {
					textArea.setText(s.getAllFields());
				} else {
					textArea.setText("Никто не выбран");
				}
			}
			onKeyUp = false;
		} else {
			onKeyUp = true;
		}
		
	}

}
