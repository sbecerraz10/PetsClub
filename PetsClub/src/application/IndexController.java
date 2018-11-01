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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Owner;
import model.Pet;
import model.PetsClub;

public class IndexController {
	
	
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
	private ListView listview;
	
	
	private Main main;
	
	private PetsClub petsclub;
	
	private Owner actualOwner;
	
	private Pet actualPet;
	
	
	public IndexController() {
		
	
	}
	
	public void initialize() {
			actualOwner = new Owner();
			actualPet = new Pet();
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
			showInfo();
	}
	
	
	
	public Owner getActualOwner()  {
		
		if(tfID.getText()!="" && tfName.getText()!="" && tfSurname.getText()!="" && dpBirthdate.getEditor().getText()!="") {	
			String id = tfID.getText();
			String name = tfName.getText();
			String surname= tfSurname.getText();
			LocalDate birthdate = dpBirthdate.getValue();
			//LocalDate ld = dpBirthdate.getValue();
			//Instant instant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
			//Date birthdate = Date.from(instant);
			
			
			//newOwner = new Owner(id,name,surname,birthdate);
			actualOwner.setId(id);
			actualOwner.setName(name);
			actualOwner.setLastname(surname);
			actualOwner.setBirthdate(birthdate);

		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Empty field");
			a.setHeaderText("Please fill the fields");
			a.setContentText("Complete the empty fields");
			a.showAndWait();
		}
		
		
		return actualOwner;
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
		if(tfNamePet.getText()!="" && dpBirthdatePet.getEditor().getText()!="") {	
			String name = tfNamePet.getText();
			LocalDate birthdate = dpBirthdatePet.getValue();
			char gender = cbGender.getValue();
			String type = cbType.getValue();
			actualPet.setName(name);
			actualPet.setBirthdate(birthdate);
			actualPet.setType(type);
			actualPet.setGender(gender);
			
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Empty field");
			a.setHeaderText("Please fill the fields");
			a.setContentText("Complete the empty fields");
			a.showAndWait();
		}
		
		return actualPet;
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
					Main.registerOwner(getActualOwner());
				//petsclub.registerOwner(getActualOwner());
					
					
				}catch (ExistentOwnerException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Existent Id");
					a.setHeaderText("Existent Owner");
					a.setContentText(e.getMessage());
					a.showAndWait();
					setEmptyFields();
				}catch (NumberFormatException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Empty field");
					a.setHeaderText("Please fill the fields");
					a.setContentText("Complete the empty fields");
					a.showAndWait();
				}
			}
		});
	}
	
	
	public void deleteOwner() {
		btDelete.setOnMouseClicked((MouseEvent) -> {
			try {
				
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Delete");
				dialog.setHeaderText("Delete an Owner");
				dialog.setContentText("Please enter your id:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
				    String id = result.get();
				    Main.deleteOwner(id);
				}
				
				
			}catch(NullPointerException e) {
				e.printStackTrace();
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
			
			
			TextField tf1 = new TextField();
			tf1.setPromptText("Enter your name or ID");
//			dialog.getDialogPane().setContent(tf1);
			
			Pane pane = new Pane();
			
			ComboBox<String> cb = new ComboBox();
			cb.getItems().addAll(choices);
			String value = "Searching for an Owner";
			Text text = new Text(value);
			Button bt = new Button();
			bt.setText("DONE");
			bt.setPrefWidth(70);
			
			pane.getChildren().addAll(tf1,cb,text,bt);
			tf1.relocate(30, 100);
			cb.relocate(30, 50);
			text.relocate(30, 10);
			bt.relocate(30, 130);
			
			
			 Stage stage = new Stage();
			 stage.initStyle(StageStyle.DECORATED);
			 Scene scene = new Scene(pane,200,200);
			 stage.setScene(scene);
			 stage.setTitle("OWNERS");
			 //stage.getIcons().add(new Image("images_PetsClub/icons8-find-user-male-filled-50.png"));
			 stage.show();
			
			 bt.setOnMouseClicked((ActionEvent)->{
				if(!tf1.getText().isEmpty() && (cb.getValue()!=null)) {	
					 if(cb.getValue().equals(op1)) {
						//Name of the Owner
						 String name = tf1.getText();
						 Owner ow1 = Main.searchOwner(name);
						 
						 if(ow1!=null) {
							 actualOwner = ow1;
							 System.out.println(""+ow1);
							 tfName.setText(ow1.getName());
							 tfSurname.setText(ow1.getLastname());
							 tfID.setText(ow1.getId());
							 dpBirthdate.getEditor().setText(ow1.getBirthdate()+ "");
						 }else {
							 Alert a = new Alert(AlertType.ERROR);
								a.setTitle("ERROR");
								a.setHeaderText("Couldnt find the user");
								a.setContentText("Please try again");
								a.showAndWait();
						 }
						 stage.close(); 
					 }else if(cb.getValue().equals(op2)) {
						 String id = tf1.getText();
						 Owner ow1 = Main.searchOwner(id);
						 if(ow1!=null) {
							 actualOwner = ow1;
							 tfName.setText(ow1.getName());
							 tfSurname.setText(ow1.getLastname());
							 tfID.setText(ow1.getId());
							 dpBirthdate.getEditor().setText(ow1.getBirthdate()+ "");
						 }else {
							 Alert a = new Alert(AlertType.ERROR);
								a.setTitle("ERROR");
								a.setHeaderText("Couldnt find the user");
								a.setContentText("Please try again");
								a.showAndWait();
						 } 
						 stage.close(); 
					 }
					 
					}else {
						Alert a = new Alert(AlertType.ERROR);
						a.setTitle("Empty field");
						a.setHeaderText("Please fill the fields");
						a.setContentText("Complete the empty fields");
						a.showAndWait();
						
						
					}

				 
			 });
			 
			
		});
	}
	
	
	public void modifyOwner() {
		btModify.setOnMouseClicked((MouseEvent)->{
			
			Main.modifyOwner(getActualOwner());
			setEmptyFields();
		});
	}
	
	
	
	public void registerPet() {
		btRegisterPet.setOnMouseClicked((MouseEvent)->{
			if(Main.contains(getActualOwner())) {	
				try {
					Main.registerPet(getActualPet(),getActualOwner());
					setEmptyFields();
				} catch (ExistentPetException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Existent Pet");
					a.setHeaderText("Already registered");
					a.setContentText(e.getMessage());
					a.showAndWait();
				}
			}else {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("ERROR");
				a.setHeaderText("The user is not registered");
				a.setContentText("Please register");
				a.showAndWait();
			}	
		});
	}
	
	
	public void deletePet() {
		btDeletePet.setOnMouseClicked((MouseEvent)->{
				int size = getActualOwner().getPets_size();
				
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Delete");
				dialog.setHeaderText("Delete an Owner");
				dialog.setContentText("Please enter your id:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
				    String name = result.get();
				    Main.deletePet(name,getActualOwner());
				    
				}
				if(size-1==getActualOwner().getPets_size()) {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setTitle("DELETED PET");
					a.setHeaderText("Pet deleted sucessfully");
					a.setContentText(";)");
					a.showAndWait();
				}else {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setTitle("ERROR");
					a.setHeaderText("Couldnt delete the pet");
					a.setContentText(":(");
					a.showAndWait();
				}
			
			
		});
	}
	
	
	public void searchPet() {
		btSearchPet.setOnMouseClicked((MouseEvent)->{
			
			
			TextField tf1 = new TextField();
			DatePicker datep = new DatePicker();
			tf1.setPromptText("Enter pet name or ID:");
			
			Pane pane = new Pane();
			
			String value = "Searching Pet";
			Text text = new Text(value);
			Button bt = new Button();
			bt.setText("DONE");
			bt.setPrefWidth(70);
			
			pane.getChildren().addAll(datep,text,bt);
			datep.relocate(30, 100);
			text.relocate(30, 10);
			bt.relocate(30, 130);
			
			
			 Stage stage = new Stage();
			 stage.initStyle(StageStyle.DECORATED);
			 Scene scene = new Scene(pane,200,200);
			 stage.setScene(scene);
			 stage.setTitle("PETS");
			 //stage.getIcons().add(new Image("images_PetsClub/icons8-find-user-male-filled-50.png"));
			 stage.show();
			
			 bt.setOnMouseClicked((ActionEvent)->{
				if(!datep.getEditor().getText().isEmpty()) {
					String condition = datep.getEditor().getText();
					 Pet temp = Main.searchPet(condition);
					 actualPet = temp;
					 tfNamePet.setText(actualPet.getName());
					 dpBirthdatePet.getEditor().setText(actualPet.getBirthdate()+"");
					 cbGender.setValue(actualPet.getGender());
					 cbType.setValue(actualPet.getType());
					 
					 
				}else {
						Alert a = new Alert(AlertType.ERROR);
						a.setTitle("Empty field");
						a.setHeaderText("Please fill the fields");
						a.setContentText("Complete the empty fields");
						a.showAndWait();	
				}
				
			 		
			 });
		});
	}
	
	
	public void modifyPet() {
		btModifyPet.setOnMouseClicked((MouseEvent)->{
			Main.modifyPet(getActualPet());
			setEmptyFields();
		});
	}
	
	
	public void showInfo() {
		
		btOwners.setOnMouseClicked((MouseEvent)->{
			Owner actual = Main.consultOwners(criteria.getValue());
			ObservableList<String> lb = FXCollections.observableArrayList(); 
			listview.getItems().clear();
			while(actual!=null) {
				lb.add("Name: "+actual.getName() +" ID: " +actual.getId()+ " Birthdate:" + actual.getBirthdate());
				System.out.println(""+actual.getName());
				actual = actual.getNext();
			}
			
			listview.getItems().addAll(lb);
			
		});
		
		btPets.setOnMouseClicked((MouseEvent)->{
			Pet actual = Main.consultPets(criteria.getValue());
			ObservableList<String> lb = FXCollections.observableArrayList(); 
			listview.getItems().clear();
			while(actual!=null) {
				lb.add("Name: " + actual.getName() + " Type: "+ actual.getType() + " Birthdate: " + actual.getBirthdate());
				System.out.println(""+actual.getName());
				actual = actual.getNext();
			}
			
			listview.getItems().addAll(lb);
		});
		
		
		btRepOwners.setOnMouseClicked((MouseEvent)->{
			
		});
		
		btRepPets.setOnMouseClicked((MouseEvent)->{
			
		});
		
		
	}
	
	
	
}
