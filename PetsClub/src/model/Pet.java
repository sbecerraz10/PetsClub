package model;

import java.util.Date;

public class Pet {
	
	
	public static final char MALE = 'M'; 
	public static final char FEMALE = 'F'; 
	public static final String DOG = "Dog"; 
	public static final String CAT = "Cat"; 
	private Pet next;
	private String name;
	private Date birthday;
	private char gender;
	private String type;
	
	public Pet(String name,Date birthday,char gender,String type) {
		next = null;
		this.name =  name;
		this.birthday = birthday;
		this.gender = gender;
		this.type = type;
	}
	
	public Pet() {
		
	}

	public Pet getNext() {
		return next;
	}

	public void setNext(Pet next) {
		this.next = next;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
