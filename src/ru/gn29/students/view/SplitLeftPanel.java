package ru.gn29.students.view;

import java.util.Collections;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ru.gn29.students.DataChangedListener;
import ru.gn29.students.Repository;
import ru.gn29.students.Student;
import ru.gn29.students.StudentController;

public class SplitLeftPanel extends JList implements DataChangedListener {
	
	private Object[] items;
	private Repository repository;
	private static SplitLeftPanel leftPanel = null;
	
	private SplitLeftPanel() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setFixedCellHeight(25);
		addListSelectionListener(
			SplitRightPanel.get()
			);
		updateData();
	}
	
	public static SplitLeftPanel get() {
		if (leftPanel == null) {
			leftPanel = new SplitLeftPanel();
		}
		return leftPanel;
	}
	
	@Override
	public void updateData() {
		repository = StudentController.get();
		List<Student> itemsList = repository.getAllStudents();
		Collections.sort(itemsList);		
		showItemsOnList(itemsList);		
	}
	
	public void showItemsOnList(List itemList) {
		items = itemList.toArray();
		setListData(items);
		int indexOfElementToSelect = 0;
		setSelectedIndex(indexOfElementToSelect);
		ListSelectionListener[] listeners = getListSelectionListeners();
		for(ListSelectionListener l : listeners) {
			ListSelectionEvent e = new ListSelectionEvent(this, indexOfElementToSelect, indexOfElementToSelect, false);
			l.valueChanged(e);
		}
	}
	
	public Object getItem(int index) {
		return items[index];
	}
	
}
