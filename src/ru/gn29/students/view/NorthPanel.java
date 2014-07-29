package ru.gn29.students.view;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import ru.gn29.students.Repository;
import ru.gn29.students.Student;
import ru.gn29.students.StudentController;

public class NorthPanel extends JPanel {
	
	private static final int TEXT_FIELD_SIZE = 25;
	
	public NorthPanel() {
		setLayout(new FlowLayout());
		BufferedImage imgSearchFile = null;
		BufferedImage imgCancelFile = null;

		try {
			imgSearchFile = ImageIO.read(new File("ic_action_search.png"));
			imgCancelFile = ImageIO.read(new File("ic_action_cancel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel imgSearch = new JLabel(new ImageIcon(imgSearchFile));
		add(imgSearch);
		
		final JTextField textField = new JTextField(TEXT_FIELD_SIZE);
		textField.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						findByText(e);						
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						findByText(e);
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						System.out.println("buup");
					}
					
				}
				);
		add(textField);
		
		JLabel imgCancel = new JLabel(new ImageIcon(imgCancelFile));
		imgCancel.addMouseListener(
				new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						textField.setText("");
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}
				
				}
		);
		add(imgCancel);
	}
	
	private void findByText(DocumentEvent e) {
		Document doc = e.getDocument();
		String text = null;						
		try {
			text = doc.getText(0, doc.getLength());
		} catch(BadLocationException badLocationException) {
			text = "Not legal offset";
		}
		
		Repository r = StudentController.get();
		List<Student> resultSearchList = r.findAllStudentsByName(text);
		SplitLeftPanel.get().showItemsOnList(resultSearchList);
		
	}

}
