package view;

import java.util.ArrayList;

import components.Navbar;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Forum;
import model.Record;
import model.User;

public class ForumView {
	private Stage primaryStage;
	User userSession;
	
	 ArrayList<Forum> forums=Forum.retreiveRecord();
	public ForumView(Stage primaryStage,User userSession) {
		// TODO Auto-generated constructor stub
		this.primaryStage=primaryStage;
		this.userSession=userSession;
	}
	
	public void show() {
		BorderPane root = new BorderPane();
		VBox scrollContent=new VBox(); 
		ScrollPane scroll = new ScrollPane(scrollContent);
		scroll.setFitToWidth(true); 
		Navbar navbar = new Navbar(primaryStage,userSession);
		HBox navigationBar = navbar.createNavbar();
		VBox container = new VBox();
		container.getStyleClass().add("container");
		scrollContent.getChildren().add(navigationBar);


	   Button addForum= new Button("New Forum");
        addForum.setOnAction(event -> {
        	new AddForum(primaryStage,userSession).show();
        });
        addForum.getStyleClass().addAll("btn-sm","btn-outline");
		
		VBox rows=new VBox(15);
        for (Forum forum: forums) {
        	VBox row=createForum(forum.getTitle(),forum.getPublisher(),forum.getContent());
        	rows.getChildren().add(row);
        }

		container.getChildren().addAll(addForum,rows);
		scrollContent.getChildren().add(container);
		
		root.setCenter(scroll);
        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
    VBox createForum(String title, String publisher, String content) {
		 VBox row = new VBox();

        // Title Label
        Label titleLabel = new Label("Title: " + title);

        // Publisher Label
        Label publisherLabel = new Label("Publisher: " + publisher);

        // Content Label
        TextArea contentTextArea = new TextArea(content);
        contentTextArea.setWrapText(true); // Enable text wrapping
        contentTextArea.setEditable(false);

		 // Set preferred width and height
		 contentTextArea.setPrefWidth(200); // Choose the desired width
		 contentTextArea.setPrefHeight(100); // Choose the desired height


        VBox.setVgrow(contentTextArea, javafx.scene.layout.Priority.ALWAYS); // Allow the text area to grow vertically

        row.getChildren().addAll(titleLabel, publisherLabel, contentTextArea);

        row.getStyleClass().add("row");
        row.getStyleClass().add("consultant"); // Apply CSS styling if needed

        return row;
	}

}
