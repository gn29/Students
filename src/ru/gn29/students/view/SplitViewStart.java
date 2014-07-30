package ru.gn29.students.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ru.gn29.students.Repository;
import ru.gn29.students.StudentController;



/**
 * <h1>Students Swing UI</h1>
 * <p>
 * Starts program here.
 * </p>
 * @author Gennady Chugrin <29gena@gmail.com>
 * @since 29.07.2014
 * @version 1.0
 * @see http://gn29.ru
 * 
 */
public class SplitViewStart {
	public static final int X_MIN_SIZE = 600;
	public static final int Y_MIN_SIZE = 120;
	public static final int X_START_SIZE = X_MIN_SIZE;
	public static final int Y_START_SIZE = Y_MIN_SIZE + 280;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
				() -> {					
					final JFrame f = new JFrame("Students");
					f.pack();
					f.setSize(X_START_SIZE, Y_START_SIZE);
					f.setLocationRelativeTo(null);
					f.setMinimumSize(new Dimension(X_MIN_SIZE, Y_MIN_SIZE));
					f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					
					//Icon
					Toolkit kit = Toolkit.getDefaultToolkit();
//					imgSearchFile = ImageIO.read(new File("ic_action_search.png"));
					try {
						Image image = ImageIO.read(new File("ic_action_good.png"));
						f.setIconImage(image);
					} catch(Exception e) {}
					
					// Menu
					JMenuBar menuBar = new JMenuBar();
					f.setJMenuBar(menuBar);
					
					JMenu menuFile = new JMenu("Файл");
					menuBar.add(menuFile);
					
					JMenuItem close = new JMenuItem("Закрыть");
					menuFile.add(close);
					close.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {	
							
							if(isExitConfirmed()){							
								f.setVisible(false);
								f.dispose();
								Repository r = StudentController.get();
								r.onExitSave();
								System.exit(0);
							}
							
						}
					});
					
					Container contentPane = f.getContentPane();		
					contentPane.setLayout(new BorderLayout());
					contentPane.add(new NorthPanel(f), BorderLayout.NORTH);
					contentPane.add(new SplitView(), BorderLayout.CENTER);
					contentPane.add(new SouthPanel(), BorderLayout.SOUTH);
					
					// надо сохраниться при закрытии программы
					f.addWindowListener(
							new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent e) {
									if(isExitConfirmed()) {
										Repository r = StudentController.get();
										r.onExitSave();
										System.exit(0);
									}
								}					
							}
						);
					
					f.setVisible(true);
				}
				);
	}
	
	public static boolean isExitConfirmed() {
		int confirmation = JOptionPane.showConfirmDialog(
				null,
				"Завершить работу программы?",
				"Подверждение закрытия программы",				
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
				);		
		
		if (confirmation == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
		
	}
}
