package application;
	
import java.util.Date;

import exceptions.ExistentOwnerException;
import exceptions.ExistentPetException;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Owner;
import model.Pet;
import model.PetsClub;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static PetsClub petsclub;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Index.fxml"));
			Scene scene = new Scene(root,800,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pets Club");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void registerOwner(Owner new_owner) throws ExistentOwnerException {
		petsclub.registerOwner(new_owner);
	}
	
	public void deleteOwner(String id) {
		petsclub.deleteOwner(id);
	}
	
	public Owner searchOwnerByName(String name) {
		return petsclub.searchOwnerByName(name);
	}
	
	public Owner searchOwnerById(String id) {
		return petsclub.searchOwnerById(id);
	}
	
	public void modifyOwner(Owner owner) {
		petsclub.modifyOwner(owner);
	}
	
	public void registerPet(Pet pet,Owner owner) throws ExistentPetException {
		petsclub.registerPet(pet, owner);
	}
	
	public void deletePet(String name,Owner owner) {
		petsclub.deletePet(name, owner);;
	}
	
	public Pet searchPetByName(String name) {
		return petsclub.searchPetByName(name);
	}
	
	public Pet searchPetByBirthdate(Date birthdate) {
		return petsclub.searchPetByBirthdate(birthdate);
	}
	
	public void modifyPet(Pet pet) {
		petsclub.modifyPet(pet);
	}
	
	public Owner consultOwners(Date criteria) {
		return petsclub.consultOwners(criteria);
	}
	
	
	
	
	
}
