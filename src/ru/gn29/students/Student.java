package ru.gn29.students;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class Student implements Comparable<Student>, Serializable{
	
	public static final String NAME = "name";
	public static final String SECOND_NAME = "secondName";
	public static final String SURNAME = "surname";
	public static final String BIRTHDAY = "birthday";
	public static final String FACULTET = "facultet";
	public static final String GROUP_NUMBER = "groupNumber";
	
	private UUID mId;
	private String mName;
	private String mSecondName;
	private String mSurname;
	private Date mBirthday;
	private String mFacultet;
	private int mGroupNumber;
	
	// getters and setters
	
	public void setId(UUID id) {
		mId = id;
	}
	
	public UUID getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}


	public void setName(String name) {
		mName = name;
	}

	public String getSecondName() {
		return mSecondName;
	}

	public void setSecondName(String secondName) {
		mSecondName = secondName;
	}

	public String getSurname() {
		return mSurname;
	}

	public void setSurname(String surname) {
		mSurname = surname;
	}

	public Date getBirthday() {
		return mBirthday;
	}
	
	public String getBirthdayAsString() {
		return new SimpleDateFormat("dd.MM.yyyy").format(getBirthday());
	}


	public void setBirthday(Date birthday) {
		mBirthday = birthday;
	}


	public String getFacultet() {
		return mFacultet;
	}


	public void setFacultet(String facultet) {
		mFacultet = facultet;
	}


	public int getGroupNumber() {
		return mGroupNumber;
	}


	public void setGroupNumber(int groupNumber) {
		mGroupNumber = groupNumber;
	}

	// constructors

	private Student() {		
				
	}
	
	/**
	 * Static method for instantiate the class
	 * @param name - Student name
	 * @param birthday - his birthday
	 * @param facultet - facultet
	 * @param groupNumber - number of group
	 * @return instance of Student
	 */
	
	public static Student get(String name, 
								String secondName, 
								String surname,
								Date birthday,
								String facultet,
								int groupNumber) {
		
		Student s = new Student();
		s.setName(name);
		s.setSecondName(secondName);
		s.setSurname(surname);
		s.setBirthday(birthday);
		s.setFacultet(facultet);
		s.setGroupNumber(groupNumber);
		
		return s;		
		
	}
	
	public String getAllFields() {
		return 
		getName() + " " + getSecondName() + " " + getSurname() + "\nДата рождения: " + 
		getBirthdayAsString() + "\nФакультет: " +
		getFacultet() + "\nНомер группы: " + 
		getGroupNumber();
		
	}
	
	@Override
	public String toString() {
//		return 
//				getName() + " " + getSecondName() + " " + getSurname() + " | " + 
//				getBirthdayAsString() + " | " +
//				getFacultet() + " | " + 
//				getGroupNumber();
		
		return getName() + " " + getSecondName().substring(0, 1) + ". " 
			+ getSurname().substring(0, 1) + ".";
	}
	
	/**
	 * Метод для сортировки Collections.sort()
	 * Сравнивает сначала по Фамилиям, если фамилии одинаковые по
	 * Именам, если имена одинаковые, по Отчествам
	 * @param s - студент для сравнивания
	 * @return int - отрицательное, нуль или положительное значение 
	 */
	@Override
	public int compareTo(Student s) {
		if (getName().equalsIgnoreCase(s.getName())) {
			if (getSecondName().equalsIgnoreCase(s.getSecondName())) {
				return getSurname().compareTo(s.getSurname());
				
			} else {
				return getSecondName().compareTo(s.getSecondName());
			}
		} else {
			return getName().compareTo(s.getName());
		}

	}

}
