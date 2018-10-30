package model;

import java.time.LocalDate;
import java.util.Date;

public class Owner implements Comparable<Owner>{
	
	private Owner next;
	private Owner previous;
	private String id;
	private String name;
	private String lastname;
	private LocalDate birthdate;
	private int pets_size;
	
	private Pet first_pet;
	
	
	public Owner() {
		
	}


	/**
	 * @param id
	 * @param name
	 * @param lastname
	 * @param birthday
	 */
	public Owner(String id, String name, String lastname, LocalDate birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.birthdate = birthdate;
		next = null;
		previous = null;
		first_pet = null;
		pets_size = 0;
	}

	
	public Owner getNext() {
		return next;
	}


	public void setNext(Owner next) {
		this.next = next;
	}


	public Owner getPrevious() {
		return previous;
	}


	public void setPrevious(Owner previous) {
		this.previous = previous;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public LocalDate getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}


	public int getPets_size() {
		return pets_size;
	}


	public void setPets_size(int pets_size) {
		this.pets_size = pets_size;
	}


	public Pet getFirst_pet() {
		return first_pet;
	}


	public void setFirst_pet(Pet first_pet) {
		this.first_pet = first_pet;
	}


	@Override
	public int compareTo(Owner owner2) {
		// TODO Auto-generated method stub
		int number = 0;
		if(Integer.parseInt(this.id) < Integer.parseInt(owner2.getId())) {
			number = -1;
		}else if(Integer.parseInt(this.id) < Integer.parseInt(owner2.getId())){
			number = 1;
		}
		
		
		return number; 
	}
	
	public boolean containsPet(Pet pet) {
		Pet actual = first_pet;
		boolean contains = false;
		if(pets_size>0) {
			int times = 0;
			while(times<pets_size) {
				if(actual.getName().equals(pet.getName())) {
					contains = true;
				}else {
					actual = actual.getNext();
				}
				times++;
			}
		}
		
		return contains;
	}
	
	public void deletePet(String pname) {
		Pet actual = first_pet;
		Pet previous = null;
		if(pets_size>0) {
			int times = 0;
			
			while(times<pets_size) {
				if(first_pet.getName().equals(pname)) {
					first_pet = first_pet.getNext();
					break;
				}
				if(actual.getName().equals(pname)) {
					previous.setNext(actual.getNext());
					actual.setNext(null);
					break;
				}else {
					previous = actual;
					actual = actual.getNext();
					
				}
				times++;
			}
		}
		
		
	}
	
	
}
