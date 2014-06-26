package ru.gn29.students;

import java.util.*;
import org.jnativehook.*;
import org.jnativehook.keyboard.*;

public class StudentsConsoleStart {
	
	Repository repository;
	
	public StudentsConsoleStart() {	
		// запуск со StudentsController
		repository = StudentController.get();		
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Запуск программы");
		new StudentsConsoleStart().go();		
	}
	
	private void go() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("F1 - весь список | "
				+ "F2 - Поиск по Ф.И.О. | "
				+ "F3 - добавить студента | "
				+ "F4 - Удалить студента | "
				+ "F5 - Выйти из программы");
		
		GlobalScreen screen = GlobalScreen.getInstance();		
		
		screen.addNativeKeyListener(
				new NativeKeyListener() {
					public void nativeKeyPressed(NativeKeyEvent e) {						
						switch (e.getKeyCode()) {	
							// нажатие F1 - весь список
							case 112 :				
								System.out.println("\nВесь список\n-----------");
								List<Student> list = 
										repository.getAllStudents();
								Collections.sort(list);
								
								Iterator<Student> i = list.iterator();
								while(i.hasNext()) {
									System.out.println(i.next());
								}
								break;
								
							// нажатие F2 - поиск по Ф.И.О.
							case 113 :
								System.out.println("\nПункт меню Поиск. Дождитесь появления окна для ввода Ф.И.О.");
								FindStudentView.showView(repository);
								break;
								
							// нажатие F3 - добавить студента
							case 114 :
								System.out.println("\nПункт меню Добавить студента. Дождитесь появления окна для ввода данных.");
								AddStudentView.showView(repository);
								break;
							
							// нажатие F4 - удалить студента
							case 115 :
								System.out.println("\nПункт меню Удалить студента. Дождитесь появления окна для ввода данных.");
								DeleteStudentView.showView(repository);
								break;
							
							// нажатие F5 - выйти из программы
							// здесь сохранение списка в файл
							case 116 :								
								// перед завершением сохраняем весь список в файл
								StudentController.get().saveListToJson();								
								// отключаем модуль перехвата
								// нажатия кнопок на клавиатуре
								GlobalScreen.unregisterNativeHook();
								System.out.println("\nРабота программы завершена");
								System.exit(0);								
								break;
							
							default :
								//System.out.println(e.getKeyCode());
								break;
						}						
						
					}
					public void nativeKeyReleased(NativeKeyEvent e) {}
					public void nativeKeyTyped(NativeKeyEvent e) {}					
				}				
			);
	}
	
}
