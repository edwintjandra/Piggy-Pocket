package view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import components.Navbar;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Forum;
import model.Income;
import model.Outcome;
import model.User;

public class AddForum {

	Stage primaryStage;
	User userSession;
	 Label titleLabel = new Label("Title:");
     TextField titleTextArea = new TextField();
     

     // Content input
     Label contentLabel = new Label("Content:");
     TextArea contentTextArea = new TextArea();
    
	public AddForum(Stage primaryStage,User userSession) {
		// TODO Auto-generated constructor stub
		this.primaryStage=primaryStage;
		this.userSession=userSession;
	}
	
	public void show() {
		BorderPane root = new BorderPane();
		Navbar navbar = new Navbar(primaryStage,userSession);
		HBox navigationBar = navbar.createNavbar();
		VBox container = new VBox();
		container.getStyleClass().add("container");
		
		
		VBox inputBox = new VBox(10); // 10 is the spacing between elements
		 
		titleTextArea.setPromptText("Enter title");
		contentTextArea.setPromptText("Enter content");
	   	contentTextArea.setPrefWidth(200); // Choose the desired width
		contentTextArea.setPrefHeight(100); 
		contentTextArea.setWrapText(true);

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
        	insertProduct();
        });
        submitButton.getStyleClass().add("btn-sm");

        inputBox.getChildren().addAll(titleLabel, titleTextArea, contentLabel, contentTextArea, submitButton);
        container.getChildren().add(inputBox);
		root.setTop(navigationBar);
		root.setCenter(container);
        //add external css
        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	void insertProduct() {
		String title= titleTextArea.getText();
		String publisher=userSession.getFirstName();
		String content=contentTextArea.getText();
		
		 if(title.length() <=5|| content.length()<=100) {
			 System.out.println("title minimum of 5 charachters, content minimum of 100 charachters");
			 return;
		 }
		 
		 Forum.insertRecord(title, content, publisher);
		 new ForumView(primaryStage,userSession).show();
	}

}
