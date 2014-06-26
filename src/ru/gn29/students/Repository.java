package ru.gn29.students;

import java.util.*;

public interface Repository extends ResultCallBack {
	/**
	 * Добавить студента
	 * 
	 * @param name
	 * @param secondName
	 * @param surname
	 * @param birthday
	 * @param facultet
	 * @param groupNumber
	 */
	public void addNewStudent(String name, String secondName, String surname, Date birthday, String facultet, int groupNumber);
	
	/**
	 * Удалить студента
	 * 
	 * @param student
	 */
	public void deleteStudent(Student student);
	
	/**
	 * Найти всех студентов по Ф.И.О.
	 * 
	 * @param name
	 */
	public List<Student> findAllStudentsByName(String name);
	
	/**
	 * Вернуть список студентов
	 * 
	 * @return
	 */
	public List<Student> getAllStudents();
	
	/**
	 * Программа завершается, сделай что нужно
	 */
	public void onExitSave();

}
