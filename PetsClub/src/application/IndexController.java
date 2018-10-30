package application;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import exceptions.ExistentOwnerException;
import exceptions.ExistentPetException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Owner;
import model.Pet;
import model.PetsClub;

public class IndexController {
	
	public static int CONSTANT_YEAR = 1900;
	
	@FXML
	private Tab tabOwners;
	@FXML
	private TextField tfID;
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfSurname;
	@FXML
	private DatePicker dpBirthdate;
	@FXML
	private Button btRegister;
	@FXML
	private Button btDelete;
	@FXML
	private Button btSearch;
	@FXML
	private Button btModify;
	
	@FXML
	private TextField tfNamePet;
	@FXML
	private DatePicker dpBirthdatePet;
	@FXML
	private ComboBox<Character> cbGender;
	@FXML
	private ComboBox<String> cbType;
	@FXML
	private Button btRegisterPet;
	@FXML
	private Button btDeletePet;
	@FXML
	private Button btSearchPet;
	@FXML
	private Button btModifyPet;
	@FXML
	private Tab tabConsults;
	
	@FXML
	private DatePicker criteria;
	@FXML
	private Button btOwners;
	@FXML
	private Button btPets;
	@FXML
	private Button btRepOwners;
	@FXML
	private Button btRepPets;
	@FXML
	private TextArea textarea;
	
	
	private Main main;
	
	private PetsClub petsclub;
	
	public IndexController() {
		
	
	}
	
	public void initialize() {
			petsclub = new PetsClub();
			cbGender.getItems().addAll(genderOptions());
			cbType.getItems().addAll(typeOptions());
		
			saveOwner();
			deleteOwner();
			searchOwner();
			modifyOwner();
			registerPet();
			deletePet();
			searchPet();
			modifyPet();
	}
	
	public void setPetsOptions() {
		//ObservableList list  = new ObservableList();
		//ArrayList<char> list1 = new ArrayList<char>();
		//cbGender.setItems(Pet.MALE, Pet.FEMALE);
	}
	
	
	public Owner getActualOwner()  {
		Owner newOwner = new Owner();
		if(tfID.getText()!="" && tfName.getText()!="" && tfSurname.getText()!="" && dpBirthdate.getEditor().getText()!="") {	
			String id = tfID.getText();
			String name = tfName.getText();
			String surname= tfSurname.getText();
			LocalDate birthdate = dpBirthdate.getValue();
			//LocalDate ld = dpBirthdate.getValue();
			//Instant instant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
			//Date birthdate = Date.from(instant);
			
			
			newOwner = new Owner(id,name,surname,birthdate);
		}
		
		
		return newOwner;
	}
	
	public List<Character> genderOptions() {
		ArrayList<Character> list = new ArrayList<Character>();
		list.add(Pet.FEMALE);
		list.add(Pet.MALE);
		
		List<Character> listForShow = list;
		
		return listForShow;
	}
	
	public List<String> typeOptions() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(Pet.CAT);
		list.add(Pet.DOG);
		
		List<String> listForShow = list;
		
		return listForShow;
	}
	
	
	public Pet getActualPet() {
		Pet pet = new Pet();
		if(tfNamePet.getText()!="" && dpBirthdatePet.getEditor().getText()!="") {	
			String name = tfNamePet.getText();
			LocalDate birthdate = dpBirthdatePet.getValue();
			char gender = cbGender.getValue();
			String type = cbType.getValue();
			pet = new Pet(name,birthdate,gender,type);
		}	
		return pet;
	}
	
	public String getActualOwnerId() throws NullPointerException{
		if(tfID.getText()=="") {
			throw new NullPointerException();
		}
		return tfID.getText();
	}
	
	public void setEmptyFields() {
		tfID.setText("");
		tfName.setText("");
		tfNamePet.setText("");
		tfSurname.setText("");
		dpBirthdate.getEditor().setText("");
		dpBirthdatePet.getEditor().setText("");
	}
	
	public void saveOwner() {
		btRegister.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent t) {
				try {
				
					System.out.println(getActualOwner());
					//main.getPetsClub().registerOwner(getActualOwner());
					main.registerOwner(getActualOwner());
				//petsclub.registerOwner(getActualOwner());
					
					
				}catch (ExistentOwnerException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Existent Id");
					a.setHeaderText("Existen Owner");
					a.setContentText(e.getMessage());
					a.showAndWait();
				}
			}
		});
	}
	
	
	public void deleteOwner() {
		btDelete.setOnMouseClicked((MouseEvent) -> {
			try {
				main.deleteOwner(getActualOwnerId());
			}catch(NullPointerException e) {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Empty field");
				a.setHeaderText("Please fill the fields");
				a.setContentText("Complete the empty fields");
				a.showAndWait();
			}	
		});
	}
	
	public void searchOwner() {
		btSearch.setOnMouseClicked((MouseEvent) ->{
			String op1 = "Search by Name";
			String op2 = "Search by ID";
			List<String> choices = new ArrayList<>();
			choices.add(op1);
			choices.add(op2);

			ChoiceDialog<String> dialog = new ChoiceDialog<>(op2, choices);
			dialog.setTitle("Search Owner");
			dialog.setHeaderText("Search an Owner");
			dialog.setContentText("Choose:");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
			   if(result.get().equals(op1)) {
				   Dialog<String> dialog1 = new Dialog<>();
				   dialog.setTitle("Login Dialog");
				   dialog.setHeaderText("Look, a Custom Login Dialog");

				   GridPane grid = new GridPane();
				   grid.setHgap(10);
				   grid.setVgap(10);
				   grid.setPadding(new Insets(20, 150, 10, 10));

				   TextField username = new TextField();
				   username.setPromptText("Owner name");
				   grid.add(username,1,0);
				   
				   dialog1.getDialogPane().setContent(grid);
				   
				   String name = username.getText();
				   main.searchOwnerByName(name);
				   
			   }else if(result.get().equals(op2)) {
				   Dialog<String> dialog1 = new Dialog<>();
				   dialog.setTitle("Login Dialog");
				   dialog.setHeaderText("Look, a Custom Login Dialog");
				   
				   GridPane grid = new GridPane();
				   grid.setHgap(10);
				   grid.setVgap(10);
				   grid.setPadding(new Insets(20, 150, 10, 10));

				   TextField username = new TextField();
				   username.setPromptText("Owner ID");
				   grid.add(username,1,0);
				   
				   dialog1.getDialogPane().setContent(grid);
				   
				   String id = username.getText();
				   main.searchOwnerById(id);   
			   }
			}

		});
	}
	
	
	public void modifyOwner() {
		btModify.setOnMouseClicked((MouseEvent)->{
			
			main.modifyOwner(getActualOwner());
		});
	}
	
	
	
	public void registerPet() {
		btRegisterPet.setOnMouseClicked((MouseEvent)->{
			try {
				main.registerPet(getActualPet(),getActualOwner());
				setEmptyFields();
			} catch (ExistentPetException e) {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Existent Pet");
				a.setHeaderText("Already registered");
				a.setContentText(e.getMessage());
				a.showAndWait();
			}
		});
	}
	
	
	public void deletePet() {
		btDeletePet.setOnMouseClicked((MouseEvent)->{
			Pet actual = getActualPet();
			String name = actual.getName();
			main.deletePet(name,getActualOwner());
		});
	}
	
	
	public void searchPet() {
		
	}
	
	
	public void modifyPet() {
		btModifyPet.setOnMouseClicked((MouseEvent)->{
			main.modifyPet(getActualPet());
		});
	}
	
	
	public void showInfo() {
		
		btOwners.setOnMouseClicked((MouseEvent)->{
			Owner actual = main.consultOwners(criteria.getValue());
			String lista = "";
			while(actual!=null) {
				lista+= actual.getName()+" \n";
				actual = actual.getNext();
			}
			
			textarea.setText(lista);
		});
		
		btPets.setOnMouseClicked((MouseEvent)->{
			main.consultPets(criteria.getValue());
		});
		
		
	}
	
	
	
}
