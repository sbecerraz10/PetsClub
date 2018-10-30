package application;
	
import java.time.LocalDate;
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
	
	public Main() {
		petsclub = new PetsClub();
	}
	
	public static void main(String[] args) {
		//petsclub = new PetsClub();
		Main main = new Main();
		launch(args);
	}
	
	public PetsClub getPetsClub() {
		return this.petsclub;
	}
	
	public static void registerOwner(Owner new_owner) throws ExistentOwnerException {
		petsclub.registerOwner(new_owner);
	}
	
	public static void deleteOwner(String id) {
		petsclub.deleteOwner(id);
	}
	
	public static Owner searchOwnerByName(String name) {
		return petsclub.searchOwnerByName(name);
	}
	
	public static Owner searchOwnerById(String id) {
		return petsclub.searchOwnerById(id);
	}
	
	public static void modifyOwner(Owner owner) {
		petsclub.modifyOwner(owner);
	}
	
	public static void registerPet(Pet pet,Owner owner) throws ExistentPetException {
		petsclub.registerPet(pet, owner);
	}
	
	public static void deletePet(String name,Owner owner) {
		petsclub.deletePet(name, owner);;
	}
	
	public static Pet searchPetByName(String name) {
		return petsclub.searchPetByName(name);
	}
	
	public static Pet searchPetByBirthdate(LocalDate birthdate) {
		return petsclub.searchPetByBirthdate(birthdate);
	}
	
	public static void modifyPet(Pet pet) {
		petsclub.modifyPet(pet);
	}
	
	public static Owner consultOwners(LocalDate criteria) {
		return petsclub.consultOwners(criteria);
	}

	public static Pet consultPets(LocalDate criteria) {
		return petsclub.consultPets(criteria);
	}
	
	
	
	
	
}
