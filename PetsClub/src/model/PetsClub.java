package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import exceptions.ExistentOwnerException;
import exceptions.ExistentPetException;

public class PetsClub {
	
	private Owner first_owner;
	
	private int owners_size;
	
	private Owner repetido;
	private Pet prepetida;
	
	public PetsClub() {
		first_owner = null;
		owners_size = 0;
		loadApp();
	}
	
	public int getSize() {
		return owners_size;
	}
	
	public void setFirst(Owner owner) {
		this.first_owner = owner;
	}
	
	public void saveApp() {
		
		try {
			FileOutputStream fileout = new FileOutputStream("archivo/app.dat");
			ObjectOutputStream obj = new ObjectOutputStream(fileout);
			if(first_owner!=null)
			obj.writeObject(first_owner);
			
			obj.flush();
			obj.close();
			fileout.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void loadApp() {
		
		try {
		FileInputStream filein = new FileInputStream("archivo/app.dat");
		ObjectInputStream obj = new ObjectInputStream(filein);
		Owner temp = new Owner();
	
		temp = (Owner)obj.readObject();
		first_owner = temp; 
		
		
		obj.close();
		filein.close();
		}catch(IOException e) {
			//saveApp();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean existentOwner(String id) {
		boolean existent = false;
		Owner actual = first_owner;
		if(!emptyOwners()) {
			while(actual!=null) {
				if(actual.getId().equals(id)) {
					existent = true;
				}
				actual = actual.getNext();
			}		
		}
		
		return existent;
	}
	
	
	/**
	 * 
	 * @param owner
	 */
	public void registerOwner(Owner new_owner) throws ExistentOwnerException {
	
		if(emptyOwners()) {
			first_owner = new Owner();
			first_owner = new_owner;
		}else {
			if(!existentOwner(new_owner.getId())) {
				Owner actual = first_owner;
				Owner previous = null;			
				//New Owner is greater than the first one
				if(first_owner.compareTo(new_owner) > 0 ) {
					if(!new_owner.getId().equals(first_owner.getId())) {
						new_owner.setNext(first_owner);
						first_owner = new_owner;
					}else {
						throw new ExistentOwnerException();
					}
				}else {
					//Loop while, for find the right position to save the new owner
					while(new_owner.compareTo(actual) > 0 && actual!=null) {
						previous = actual;
						actual = actual.getNext();
						if(actual==null) break;
					}
					//Connections 
					new_owner.setNext(actual);
					if(previous!=null)
					previous.setNext(new_owner);
					new_owner.setPrevious(previous);
					if(actual!=null) actual.setPrevious(new_owner);
				
				}
			
			}else {
				throw new ExistentOwnerException();
			}
		}
		owners_size++;
		saveApp();
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
			//int times = 0;
			while(actual!=null) {
				if(actual.equals(ow)) {
					contains = true;
					break;
				}
				actual = actual.getNext();
				//times++;
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
                         if(actual.getNext()!=null)
						 actual.getNext().setPrevious(previous);
						 actual.setNext(null);
                         actual = previous.getNext();
                         break;
					}	
				}
				previous = actual;
				//if(actual.getNext()!=null);
				actual = actual.getNext();
			}
		}
		
		owners_size--;
		saveApp();
	}
	
	public Owner searchOwner(String condition) {
		Owner actual = first_owner;
		Owner toReturn = null;
		if(!emptyOwners()) {
			while(actual!=null) {
				if(first_owner.getName().equals(condition) || first_owner.getId().equals(condition)) {
					toReturn = this.first_owner;
				}
				if(actual.getName().equals(condition) || actual.getId().equals(condition)) {
					toReturn = actual;
				}
					actual = actual.getNext();
			}
		}
		
		return toReturn;
	}
	
	
	public Owner searchOwnerByName(String name) {
		Owner actual = first_owner;
		Owner toReturn = null;
		if(!emptyOwners()) {
			while(actual!=null) {
				if(first_owner.getName().equals(name)) {
					toReturn = this.first_owner;
				}
				if(actual.getName().equals(name)) {
					toReturn = actual;
				}
					actual = actual.getNext();
			}
		}
		
		return toReturn;
	}
	
	
	public Owner searchOwnerById(String id) {
		Owner actual = first_owner;
		Owner toReturn = null;
		if(!emptyOwners()) {
			while(actual!=null) {
				if(first_owner.getId().equals(id)) {
					toReturn =  this.first_owner;
				}
				if(actual.getId().equals(id)) {
					toReturn = actual;
				}else{
					actual = actual.getNext();
				}
			}
		}
		
		return toReturn;
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
		
		saveApp();
	}
	
	
	public boolean existentPet(Pet pet, Owner ow) {
		boolean exist = false;
		if(ow!=null) {
			if(ow.getFirst_pet()!=null) {
				Pet actual = ow.getFirst_pet();
				while(actual!=null) {
					if(actual.getName().compareToIgnoreCase(pet.getName()) == 0) {
						exist = true;
					}
					
					actual = actual.getNext();
				}
			}	
		}
		
		return exist;
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
		if(!existentPet(pet, owner)) {
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
		}else {
			throw new ExistentPetException();
		}	
	}
	
	
	/**
	 * 
	 * @param name
	 * @param owner
	 */
	public void deletePet(String name, Owner owner) {
		owner.deletePet(name);
	}
	
	
	//REVISAR
	public Pet searchPet(String condition) {
		Pet toreturn = null;
		if(!emptyOwners()) {
			int times1 = 0;
			Owner actual = first_owner;
			while(actual!=null) {
					int times2 = 0;
					if(actual.getFirst_pet()!=null) {
						Pet pactual = actual.getFirst_pet();
						while(pactual!=null) {
							System.out.println(pactual.getName());
							System.out.println(condition);
							if(pactual.getName().equals(condition) || pactual.getBirthdate().toString().equals(condition)) {
								toreturn = pactual;
							}
							pactual = pactual.getNext();
							
							times2++;
						}
					}
				actual = actual.getNext();
				times1++;
			}
		}
		
		return toreturn;
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
	
	
	public Pet searchPetByBirthdate(LocalDate birthdate) {
		
		if(!emptyOwners()) {
			int times1 = 0;
			Owner actual = first_owner;
			while(times1<owners_size) {
					int times2 = 0;
					if(actual.getFirst_pet()!=null) {
						Pet pactual = actual.getFirst_pet();
						while(times2<actual.getPets_size()) {
							if(pactual.getBirthdate().equals(birthdate)) {
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
	
	
	public Owner consultOwners(LocalDate criteria) {
		Owner toreturn = null;
		if(criteria==null) {
			toreturn = first_owner;
		}else {
			if(!emptyOwners()) {
				Owner actual = first_owner;
				Owner previous = null;
				Owner newlist = null;
			while(actual!=null) {
				if(actual.getBirthdate().equals(criteria)) {
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
				previous = actual;
				actual = actual.getNext();
				
			}
			
			toreturn = newlist;
			
			}
		}
		
		
		return toreturn;
	}
	
	
	public Pet consultPets(LocalDate criteria) {
		Pet firstPet = null;
		if(!emptyOwners()) {
			Owner actual = first_owner;
			Owner previous = null;
			if(criteria==null) {
				Pet inside = null;
				Pet actual1 = null;
				while(actual!=null) {
					//actual1 = new Pet();
					//Pet next1 = null;
					actual1 = actual.getFirst_pet();
					if(actual1!=null) {
					inside = actual1;
					inside.setNext(null);
					
						
					}else if(firstPet==null) {
						firstPet = inside;
						
					}else if(firstPet!=null){
						inside.setNext(null);
						inside.setNext(firstPet);
						firstPet = inside;
					}

					
					previous = actual;
					actual = actual.getNext();
				}
			}else {
				while(actual!=null) {
					Pet actual1 = new Pet();
					Pet next1 = null;
					actual1 = actual.getFirst_pet();
					
					for(int i=0; actual1!=null;i++) {
						Pet inside = actual1;
						if(inside.getBirthdate().equals(criteria)) {		
								if(firstPet==null) {
									firstPet = inside;
								}else {
									inside.setNext(firstPet);
									firstPet = inside;
								}
						}		
						actual1 = actual1.getNext();
 					}
					
					previous = actual;
					actual = actual.getNext();
				}
			}
		}
		
		return firstPet;
	}
	
	public Owner consultRepetidos(LocalDate criteria) {
		consultRep(first_owner,first_owner.getNext());
		return repetido;
	}
	
	public Owner repetidos(Owner base, Owner actual) {
		Owner aux = null;
		if(repetido==null) {
			repetido = new Owner();
			aux = base;
			aux.setPrevious(null);
			aux.setNext(null);
			aux.setNext(actual);
			repetido = aux;
		}else {
			aux = base;
			
			aux.setPrevious(null);
			aux.setNext(null);
			aux.setNext(actual);
			actual.setNext(repetido);
			repetido = aux;
		}
		
		
		return repetido;
	}
	
	
	public void consultRep(Owner base, Owner actual) {
		if(base!=null) {
			if(actual!=null) {
				if(base.getName().equals(actual.getName())) {
					repetidos(base,actual);
				}
				actual = actual.getNext();
				consultRep(base,actual);
			}else {
				base = base.getNext();
				if(base!=null) {
					actual = base.getNext();
					consultRep(base,actual);
				}
			}
		}else {
		
		}
	}
	
	
	
}
