package view;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import components.Navbar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Database;
import model.Income;
import model.Outcome;
import model.SharedStageHolder;
import model.User;

public class AddRecord {
 	private ToggleButton toggleButton = new ToggleButton("Add new record");
	
	//input field
	Label labelName = new Label("Input Name:");
    TextField fieldName = new TextField();
    Label labelPrice = new Label("Input Total Income/Outcome:");
    TextField fieldPrice = new TextField();
    Label labelDate = new Label("Input date:");
    ComboBox<String> comboBox = new ComboBox<>();
    private Button addButton = new Button("Save");
    
    DatePicker datePicker = new DatePicker();

    // Define a custom date format using DateTimeFormatter
    DateTimeFormatter customDateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("en"));

    private Stage primaryStage;
    User userSession;
    public AddRecord(Stage primaryStage,User userSession) {
      this.primaryStage=primaryStage;
      this.userSession=userSession;
	}
	 
	public void show() {
		BorderPane root = new BorderPane();
		Navbar navbar = new Navbar(primaryStage,userSession);
		HBox navigationBar = navbar.createNavbar();		
 		VBox container = new VBox();
		container.getStyleClass().add("container");
        
        VBox display = new VBox(10);  
        Label labelDescription = new Label("Enter Note Income/Outcome:");
    	comboBox.getItems().addAll("Income", "Outcome");
    	comboBox.setValue("Income");
        datePicker.setValue(LocalDate.now());
        TextFormatter<Object> priceFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                // Allow only numeric input
                return change;
            }
            return null; // Disallow non-numeric input
        });
        fieldPrice.setTextFormatter(priceFormatter);
    	VBox inputBox = new VBox(10); // 10 is the spacing between elements
    	inputBox.getChildren().addAll(
    			comboBox,labelName, fieldName, labelPrice, fieldPrice,labelDate,datePicker
    			);
    	addButton.setOnAction(event-> {
    		try {
    			insertProduct();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	});
    	
    	 // 10 is the spacing between elements
    	display.getChildren().addAll(
    	    inputBox,
    	    addButton
    	);
    	display.getStyleClass().add("addRecordDisplay"); //adding style


        container.getChildren().add(display);
    	root.setTop(navigationBar);
    	Scene scene = new Scene(root, 700, 500);
        root.setCenter(container);
        scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
  	}
	

void insertProduct() throws SQLException {
	LocalDate selectedDate = datePicker.getValue();
	String name = fieldName.getText();
    double total;
    
    if (selectedDate == null || name == null || name.trim().isEmpty()) {
        System.out.println("Selected date and name cannot be null or empty.");
        return;  
    }
    
    try {
        total =Double.parseDouble(fieldPrice.getText());
    } catch (NumberFormatException e) {
        System.out.println("Total must be a valid number.");
        return; // Abort further processing
    }
    
    if(total <=1000) {
    	System.out.println("total cannot be less than 1000");
    	return;
    }
    
    if (name.length() > 95) {
        System.out.println("Name is too long. Maximum length is 95 characters.");
         return;  
    }
    
    LocalDate now = LocalDate.now();
    if (selectedDate.isAfter(now)) {
        // Handle the error, e.g., show a message to the user
        System.out.println("Selected date cannot be after the current date.");
         return; // Abort further processing
    }
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("en")); 
    String formattedDate = selectedDate.format(dateFormatter);
	
	 String type=comboBox.getValue();
	    if(type.equals("Income")) {
	    	Income.insertRecord(name,total, formattedDate,userSession);
	    }else if(type.equals("Outcome")) {
	    	Outcome.insertRecord(name, total, formattedDate,userSession);
	    }
	    

 
	
    fieldName.clear();
    datePicker.setValue(LocalDate.now());
    fieldPrice.clear();    
    
    backHome();
}

void backHome() {
	//Scene HomePageScene= new HomePage().createHomeScene();
    //SharedStageHolder.getPrimaryStage().setScene(HomePageScene);
	new HomePage(primaryStage,userSession,"").show();
}

}








 




