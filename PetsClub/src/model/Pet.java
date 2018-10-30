package model;

import java.time.LocalDate;

public class Pet {
	
	
	public static final char MALE = 'M'; 
	public static final char FEMALE = 'F'; 
	public static final String DOG = "Dog"; 
	public static final String CAT = "Cat"; 
	private Pet next;
	private String name;
	private LocalDate birthdate;
	private char gender;
	private String type;
	
	public Pet(String name,LocalDate birthdate,char gender,String type) {
		next = null;
		this.name =  name;
		this.birthdate = birthdate;
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

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
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
