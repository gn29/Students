package ru.gn29.students;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import ru.gn29.students.view.SplitLeftPanel;

public class StudentController implements ResultCallBack, Repository {
	
	private ArrayList<DataChangedListener> dataChangedListeners;
	private List<Student> students;
	private static StudentController instance = null;
	private boolean isStudentsListChanged = false;
	public static final String FILE_PATH = "students.json"; // to inflate from file (JSON, XML...)
	
	// константы для обработки событий при обратном вызове из окон ввода
	public static final String ACTION = "ru.gn29.students.action";
	public static final String ACTION_ADD = "ru.gn29.students.action_add";
	public static final String ACTION_FIND = "ru.gn29.students.action_find";
	public static final String ACTION_DELETE = "ru.gn29.students.action_delete";
	
	/**
	 * This is default constructor for this class
	 * it's private.
	 * Use static method getStudentController 
	 * instead to get the instance
	 */	
	private StudentController() {
		students = new ArrayList<Student>();
		inflateFromFile();
	}
	
	/**
	 *  This method call when you want to create new student 
	 *  (to add new student in collection)
	 *  
	 *  @param name - Full name of student
	 *  @param birthday - Birthday date
	 *  @param facultet - Name of facultet
	 *  @param groupNumber - Number of the group
	 */	
	public void addNewStudent(String name, String secondName, String surname,
			Date birthday, String facultet, int groupNumber) {
		students.add(
				Student.get(name, secondName, surname, birthday, facultet, groupNumber)
				);
		isStudentsListChanged = true;
		dataChangedNotify();
		
	}
	
	/**
	 * Call this method for deleting student from list
	 * @param s - Student object
	 */	
	public void deleteStudent(Student s) {
		students.remove(s);
		isStudentsListChanged = true;
		dataChangedNotify();
	}
	
	/**
	 * Call this method to search all students by name
	 * 
	 * @param name - the name of the student
	 * @return List of Student objects
	 */	
	public List<Student> findAllStudentsByName(String name) {
		
		ArrayList<Student> list = new ArrayList<Student>();
		
		name = name.toLowerCase();
		
		for(Student s : students) {
			String sName = s.getName().toLowerCase();
			if (sName.startsWith(name)) {
				list.add(s);
			}
		}
		
		return list;
	}
	
	/**
	 * This static method could get you a reference 
	 * to instance of StudentController (it's singleton).
	 * 
	 * @return reference to instance
	 */	
	public static StudentController get() {
		if (instance == null) {
			instance = new StudentController();
		}		
		return instance;
	}
	
	/**
	 * This method will inflate the instance with Student objects
	 * from file
	 */	
	private void inflateFromFile() {
		
		File file = null;		
		try {
			file = new File(FILE_PATH);
		} catch (NullPointerException e) {
			System.exit(1);
		}
		
		FileReader reader = null;		
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("Can't find source (json file)");
			System.err.println(e.getMessage());
			System.exit(1);
			
		}
		
		JSONObject jsonObject = new JSONObject(
				new JSONTokener(reader)
				);
		
		JSONArray array = jsonObject.getJSONArray("students");
		
		for (int i = 0; i < array.length(); i++) {			
			JSONObject student = array.getJSONObject(i);			
			String name = student.getString("name");
			String secondName = student.getString("secondName");
			String surname = student.getString("surname");
			String birthday = student.getString("birthday");
			String facultet = student.getString("facultet");
			int groupNumber = student.getInt("groupNumber");
			
			Date dateBirthday = null; 
			//делаем Date из строки			
			try {
				dateBirthday = new SimpleDateFormat("dd.MM.yyyy").parse(birthday);
			} catch(ParseException e) {
				System.err.println("Can't understand the date format from source.");
			}
						
			students.add(
					Student.get(name, secondName, surname, dateBirthday, facultet, groupNumber)
					);			
		}			
	}
	
	/**
	 * Возвращает список студентов
	 * @return List of students
	 */
	public List<Student> getAllStudents() {
		return students;
	}
	
	/**
	 * Метод сохраняет весь список в  JSON java.io.writer
	 * В этом случае в файл.
	 */	
	public void saveListToJson() {
		if (isStudentsListChanged) {				
			List<Student> l = StudentController.get().getAllStudents();			
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(StudentController.FILE_PATH);
			} catch(Exception e2){
				System.out.println("Source for writing is not avalable.\nExit without save");
				System.exit(1);
			}
			
			if (writer != null) {				
				JSONWriter jsonWriter = new JSONWriter(writer);
				
				jsonWriter.object();
				jsonWriter.key("students");
				jsonWriter.array();
				
				for (Student s : l) {					
					jsonWriter.object();
					
					jsonWriter.key(Student.NAME);
					jsonWriter.value(s.getName());
					
					jsonWriter.key(Student.SECOND_NAME);
					jsonWriter.value(s.getSecondName());
					
					jsonWriter.key(Student.SURNAME);
					jsonWriter.value(s.getSurname());
									
					jsonWriter.key(Student.BIRTHDAY);
					jsonWriter.value(s.getBirthdayAsString());
					
					jsonWriter.key(Student.FACULTET);
					jsonWriter.value(s.getFacultet());
					
					jsonWriter.key(Student.GROUP_NUMBER);
					jsonWriter.value(s.getGroupNumber());
					
					jsonWriter.endObject();
					
				}
				
				jsonWriter.endArray();
				jsonWriter.endObject();	
				
			}
			
			writer.close();
		}
	}
	
	@Override
	public void onResult(Properties p) {
		
		switch (p.getProperty(StudentController.ACTION)) {
		
		case StudentController.ACTION_ADD :
			isStudentsListChanged = true;
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Date birthday = null;
			try {
				birthday = format.parse(p.getProperty(Student.BIRTHDAY));
			} catch(Exception e) {}
				
			StudentController.get().addNewStudent(p.getProperty(Student.NAME),
				p.getProperty(Student.SECOND_NAME), 
				p.getProperty(Student.SURNAME), 
				birthday, 
				p.getProperty(Student.FACULTET), 
				Integer.valueOf(p.getProperty(Student.GROUP_NUMBER)));
			
			break;
				
		case StudentController.ACTION_DELETE :	
			isStudentsListChanged = true;
			Student delStudent = null;
			for (Student s : students) {
				if (s.getName().equalsIgnoreCase(p.getProperty(Student.NAME)) &&
						s.getSecondName().equalsIgnoreCase(p.getProperty(Student.SECOND_NAME)) &&
						s.getSurname().equalsIgnoreCase(p.getProperty(Student.SURNAME)) &&
						s.getBirthdayAsString().equalsIgnoreCase(p.getProperty(Student.BIRTHDAY)) &&
						s.getFacultet().equalsIgnoreCase(p.getProperty(Student.FACULTET)) &&
						String.valueOf(s.getGroupNumber()).equalsIgnoreCase(p.getProperty(Student.GROUP_NUMBER))
						) {
					delStudent = s;
					break;
				}				
			}
			
			System.out.println("\nУдаление студента\n-------------");
			if (delStudent != null) {
				students.remove(delStudent);
				System.out.println("Выполнено");
			} else {
				System.out.println("Не найдено ни одного студента.");
			}
			
			break;
					
		case StudentController.ACTION_FIND :
			List<Student> searchResult = new ArrayList<Student>();
			for (Student s : students) {
				if (s.getName().equalsIgnoreCase(p.getProperty(Student.NAME)) &&
						s.getSecondName().equalsIgnoreCase(p.getProperty(Student.SECOND_NAME)) &&
						s.getSurname().equalsIgnoreCase(p.getProperty(Student.SURNAME))
						) {
					searchResult.add(s);
				}				
			}
			
			System.out.println("\nПоиск\n-----");
			if (searchResult.size() > 0) {
				for (Student s : searchResult) {
					System.out.println(s);
				}
			} else {
				System.out.println("Не найдено ни одного студента.");
			}
			
			break;
				
		}		
	}
	
	@Override
	public void onExitSave() {
		saveListToJson();
	}

	/**
	 * Добавить Listener для отслеживания изменения данных
	 * @param listener - слушатель изменения
	 */
	@Override
	public void addDataChangedListener(DataChangedListener listener) {
		if (listener != null) {
			dataChangedListeners.add(listener);
		}		
	}
	
	/**
	 * Оповестить всех заинтересованных об изменение данных
	 */	
	private void dataChangedNotify() {		
		SplitLeftPanel.get().updateData();
		
//		if(dataChangedListeners.size() != 0) {
//			for (DataChangedListener l : dataChangedListeners) {
//				if (l != null) {
//					l.updateData();
//				}
//			}
//		}
		
	}

}
