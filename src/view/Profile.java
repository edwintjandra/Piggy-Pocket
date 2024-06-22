package view;

import java.text.NumberFormat;

import components.Navbar;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.PercentageStringConverter;
import model.User;

public class Profile {

	Stage primaryStage;
	User userSession;
	BorderPane root = new BorderPane();
	 
	GridPane gp = new GridPane();
	Spinner<Integer> spinner_TB = new Spinner<>();
	 private static final double MIN = 0;
	 private static final double MAX = 250;
	 private static final double INITIAL_VALUE = 100;
	 private static final double STEP_INCREMENT = 0.1;
	  
	public Profile(Stage primaryStage,User userSession) {
		// TODO Auto-generated constructor 
		this.primaryStage=primaryStage;
		this.userSession=userSession;
	}
	
	public void show() {
	    BorderPane root = new BorderPane();
	    Navbar navbar = new Navbar(primaryStage, userSession);
	    HBox navigationBar = navbar.createNavbar();
		VBox scrollContent=new VBox(); 
		ScrollPane scroll = new ScrollPane(scrollContent);
		scroll.setFitToWidth(true); 
		scrollContent.getChildren().add(navigationBar);


	    VBox container = new VBox();
	    container.getStyleClass().add("container");

	    Button sb = new Button("Submit");
	    Button logout=new Button("Logout");

	    Label firstNameLabel = new Label("First Name");
	    TextField  firstNameText = new TextField(userSession.getFirstName());
	    VBox firstNameContainer=new VBox();
	    firstNameContainer.getChildren().addAll(firstNameLabel,firstNameText);
	    
	    Label lastNameLabel = new Label("Last Name");
	    TextField  lastNameText = new TextField(userSession.getLastName());
	    VBox lastNameContainer= new VBox();
	    lastNameContainer.getChildren().addAll(lastNameLabel,lastNameText);

	  
	    Label emailLabel=new Label("Email");
	    TextField emailField = new TextField(userSession.getEmail());
	    VBox emailContainer=new VBox();
	    emailContainer.getChildren().addAll(emailLabel,emailField);
	    
	    Label passwordLabel=new Label("Password");
	    PasswordField passwordField = new PasswordField();
	    VBox passwordContainer=new VBox();
	    passwordContainer.getChildren().addAll(passwordLabel,passwordField);

	    
	    sb.setOnAction(e -> {
	        String email = emailField.getText();
	        String password = passwordField.getText();
	        
	        User.updateUser(String.valueOf(userSession.getUserId()), firstNameText.getText(), lastNameText.getText(), emailField.getText(), passwordField.getText());
	        new Login(primaryStage).show();
	    });
	    
	    logout.setOnAction(e -> {
	    	new LandingPage(primaryStage).show();
 
	    });
	    
	    VBox rootVBox = new VBox(
	    		firstNameContainer,
	    		lastNameContainer,
	    		emailContainer,
	    		passwordContainer,
	    		new HBox(5,sb,logout)
 	    );

	    rootVBox.setAlignment(Pos.CENTER);
	    rootVBox.setSpacing(20);

	    container.getChildren().add(rootVBox);
	    scrollContent.getChildren().add(container);
 

	    root.setCenter(scroll);

	    Scene scene = new Scene(root, 700, 500);
	    scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	


	
		


		
}


