package model;

import java.util.Date;

import exceptions.ExistentOwnerException;
import exceptions.ExistentPetException;

public class PetsClub {
	
	private Owner first_owner;
	
	private int owners_size;
	
	
	
	public PetsClub() {
		first_owner = null;
	}
	
	
	/**
	 * 
	 * @param owner
	 */
	public void registerOwner(Owner new_owner) throws ExistentOwnerException {
	
		if(emptyOwners()) {
			first_owner = new_owner;
		}else {
			Owner actual = first_owner;
			Owner previous = null;			
			//New Owner is greater than the first one
			if(new_owner.compareTo(first_owner) < 0 ) {
				new_owner.setNext(first_owner);
				first_owner = new_owner;
			}else {
				//Loop while, for find the right position to save the new owner
				while(new_owner.compareTo(actual) > 0 && actual!=null) {
					if(new_owner.getId().equals(actual.getId())) 
					{
						throw new ExistentOwnerException();
					}
					
					previous = actual;
					actual = actual.getNext();
				}
				//Connections 
				new_owner.setNext(actual);
				previous.setNext(new_owner);
				new_owner.setPrevious(previous);
				if(actual!=null) actual.setPrevious(new_owner);
			
			}
			
		}
		
		owners_size++;
	}
	
	
	public boolean emptyOwners() {
		if(first_owner==null) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public Owner getOwner(int i) {
		Owner actual = first_owner;
		if(!emptyOwners()) {
			int times = 0;
			while(times<owners_size) {
				if(times==i) {
					return actual;
				}else {
					actual = actual.getNext();
				}
				times++;
			}
		}
		
		return null;
	}
	
	
	public int getOwnerPos(Owner ow) {
		Owner actual = first_owner;
		if(!emptyOwners()) {
			int times = 0;
			while(times<owners_size) {
				if(actual.equals(ow)) {
					return times;
				}else {
					actual = actual.getNext();
				}
				times++;
			}
		}
		return -1;
	}
	
	
	public boolean containsOwner(Owner ow) {
		boolean contains = false;
		Owner actual = first_owner;
		if(!emptyOwners()) {
			int times = 0;
			while(times<owners_size) {
				if(actual.equals(ow)) {
					contains = true;
					break;
				}else {
					actual = actual.getNext();
				}
				times++;
			}
		}
		return contains;
	}
	
	
	/**
	 * 
	 * @param id
	 */
	public void deleteOwner(String id) {
		if(!emptyOwners()) {
			Owner actual = first_owner;
			Owner previous = null;
			while(actual!=null) {
				if(actual.getId().equals(id)) {
					if(previous==null) {
						first_owner = first_owner.getNext();
						actual.setNext(null);
						actual = first_owner;
					}else {
						 previous.setNext(actual.getNext());
                         actual.getNext().setPrevious(previous);
						 actual.setNext(null);
                         actual = previous.getNext();
					}
				}else {
					previous = actual;
					actual = actual.getNext();
				}
			}
		}
		
		owners_size--;
	}
	
	
	public Owner searchOwnerByName(String name) {
		Owner actual = first_owner;
		if(!emptyOwners()) {
			while(actual!=null) {
				if(first_owner.getName().equals(name)) {
					return this.first_owner;
				}
				if(actual.getName().equals(name)) {
					return actual;
				}else{
					actual = actual.getNext();
				}
			}
		}
		
		return null;
	}
	
	
	public Owner searchOwnerById(String id) {
		Owner actual = first_owner;
		if(!emptyOwners()) {
			while(actual!=null) {
				if(first_owner.getId().equals(id)) {
					return this.first_owner;
				}
				if(actual.getId().equals(id)) {
					return actual;
				}else{
					actual = actual.getNext();
				}
			}
		}
		
		return null;
	}
	
	
	public void modifyOwner(Owner owner) {
		Owner actual = first_owner;
		if(!emptyOwners()) {
			while(actual!=null) {
				if(actual.equals(owner)) {
					owner.setNext(actual.getNext());
					owner.setPrevious(actual.getPrevious());
					actual.setNext(null);	
					actual.setPrevious(null);
					actual = null;
				}else {
					actual = actual.getNext();
				}
			}
		}
		
	}
	
	
	
	/**
	 * 
	 * @param owner
	 * @param pet
	 * pre: owner already exist 
	 * @throws ExistentPetException 
	 */
	public void registerPet(Pet pet,Owner owner) throws ExistentPetException  {
		//Should throw ExistentPetException
		
		if(owner.getFirst_pet()==null) {
			owner.setFirst_pet(pet);
		}else {
			if(!owner.containsPet(pet)) {
				pet.setNext(owner.getFirst_pet());
				owner.setFirst_pet(pet);
			}else {
				throw new ExistentPetException();
			}	
		}
		int size = owner.getPets_size(); 
		owner.setPets_size(size++);
	}
	
	
	/**
	 * 
	 * @param name
	 * @param owner
	 */
	public void deletePet(String name, Owner owner) {
		owner.deletePet(name);
	}
	
	
	public Pet searchPetByName(String name) {
		if(!emptyOwners()) {
			int times1 = 0;
			Owner actual = first_owner;
			while(times1<owners_size) {
					int times2 = 0;
					if(actual.getFirst_pet()!=null) {
						Pet pactual = actual.getFirst_pet();
						while(times2<actual.getPets_size()) {
							if(pactual.getName().equals(name)) {
								return pactual;
							}else {
								pactual = pactual.getNext();
							}
							times2++;
						}
					}
				actual = actual.getNext();
				times1++;
			}
		
		}
		
		
		return null;
	}
	
	
	public Pet searchPetByBirthdate(Date birthdate) {
		
		if(!emptyOwners()) {
			int times1 = 0;
			Owner actual = first_owner;
			while(times1<owners_size) {
					int times2 = 0;
					if(actual.getFirst_pet()!=null) {
						Pet pactual = actual.getFirst_pet();
						while(times2<actual.getPets_size()) {
							if(pactual.getBirthday().equals(birthdate)) {
								return pactual;
							}else {
								pactual = pactual.getNext();
							}
							times2++;
						}
					}
				actual = actual.getNext();
				times1++;
			}
		
		}
		
		
		return null;
	}
	
	
	public void modifyPet(Pet pet) {

		if(!emptyOwners()) {
			int times1 = 0;
			Owner actual = first_owner;
			while(times1<owners_size) {
					int times2 = 0;
					if(actual.getFirst_pet()!=null) {
						Pet pactual = actual.getFirst_pet();
						Pet previous = null;
						while(times2<actual.getPets_size()) {
							if(pactual.equals(pet)) {
								pet.setNext(pactual.getNext());
								pactual.setNext(null);
								if(previous!=null) previous.setNext(pet);
								break;
							}else {
								previous = pactual;
								pactual = pactual.getNext();
							}
							times2++;
						}
					}
				actual = actual.getNext();
				times1++;
			}
		
		}
		
	}
	
	
	public Owner consultOwners(Date criteria) {
		Owner toreturn = null;
		if(criteria==null) {
			toreturn = first_owner;
		}else {
			if(!emptyOwners()) {
				Owner actual = first_owner;
				Owner previous = null;
				Owner newlist = null;
			while(actual!=null) {
				if(actual.getBirthday().equals(criteria)) {
					if(newlist==null) {
						Owner newActual = actual;
						newlist = newActual;
						newlist.setPrevious(null);
						newlist.setNext(null);
					}else {
						Owner newActual = actual;
						newActual.setPrevious(null);
						newActual.setNext(newlist);
						newlist.setPrevious(newActual);
						newlist = newActual;
					}
					
				}
				
			}
			
			toreturn = newlist;
			
			}
		}
		
		
		return toreturn;
	}
	
	
	
	
}
