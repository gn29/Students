package ru.gn29.students.view;

import javax.swing.*;

import ru.gn29.students.StudentController;

public class SplitView extends JSplitPane {
	
	public static final int DIVIDER_LOCATION = 150;
	
	public SplitView() {
		
		super(JSplitPane.HORIZONTAL_SPLIT, 
				new JScrollPane(SplitLeftPanel.get()), 
				new JScrollPane(SplitRightPanel.get()));
		this.setOneTouchExpandable(true);
		setDividerLocation(DIVIDER_LOCATION);
		
//		StudentController.get().addDataChangedListener(SplitLeftPanel.get());
		
		
	}

}
