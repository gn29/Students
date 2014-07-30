package ru.gn29.students.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import ru.gn29.students.Repository;
import ru.gn29.students.Student;
import ru.gn29.students.StudentController;

/**
 * Класс для отображения строки поиска в северной 
 * части окна программы.
 * 
 * @author Gennady Chugrin <29gena@gmail.com>
 * @version 1.0
 * @since 1.0
 *
 */

public class NorthPanel extends JPanel {
	
	private static final int TEXT_FIELD_SIZE = 25;
	private JLabel imgCancel;
	private Component rootComponent;
	
	public NorthPanel(Component c) {
		super();
		rootComponent = c;
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
		
		/*
		 * JTextField для поиска по фамилии в списке
		 */		
		final JTextField textField = new JTextField(TEXT_FIELD_SIZE);
		add(textField);
		
		
		// смена раскладки клавиатуры не работает на 
		// OS X :-(
		Locale locale = new Locale("ru", "RU");
		Locale.setDefault(locale);		
		c.getInputContext().selectInputMethod(locale);
		// ====================
		
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
		// конец блока JTextField
		
		/*
		 * Картинка-крестик для очистки JTextField 
		 * одним нажатием
		 */
		imgCancel = new JLabel(new ImageIcon(imgCancelFile));
		imgCancel.setVisible(false);
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
		// конец блока картинки-крестика
	}
	
	/**
	 * Общий метод для поиска введеной фамилии и формирования
	 * списка из найденого.
	 * 
	 * @param e - для получения введеного в JTextField текста
	 */
	
	private void findByText(DocumentEvent e) {
		Document doc = e.getDocument();
		String text = null;						
		try {
			text = doc.getText(0, doc.getLength());
		} catch(BadLocationException badLocationException) {
			text = "Not legal offset";
		}
		
		/*
		 * Блок для отображения картинки-"крестика" справа от JTextField
		 */		
		if(text.equalsIgnoreCase("")) {
			imgCancel.setVisible(false);
		} else {
			imgCancel.setVisible(true);
		}
		
		Repository r = StudentController.get();
		List<Student> resultSearchList = r.findAllStudentsByName(text);
		SplitLeftPanel.get().showItemsOnList(resultSearchList);

	}
	
}
