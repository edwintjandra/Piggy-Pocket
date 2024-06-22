package components;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.SharedStageHolder;
import model.User;
import view.AddRecord;
import view.ForumView;
import view.HomePage;
import view.LandingPage;
import view.Login;
import view.Profile;
import view.About;


public class Navbar {

	private Stage primaryStage;
	private User userSession;
	public Navbar(Stage primaryStage,User userSession) {
		// TODO Auto-generated constructor stub
		this.userSession=userSession;
		this.primaryStage=primaryStage;
	}
	
	 public HBox createNavbar() {
	        HBox navbar = new HBox(290);
	        
	        HBox left=new HBox(4);
	        Image image = new Image("/images/logo.png"); // Adjust the path to your image.
	        ImageView imageView = new ImageView(image);
	        imageView.setFitWidth(50); // Set the desired width
	        imageView.setFitHeight(50);
	        imageView.setOnMouseClicked(event -> {
	        	new LandingPage(primaryStage).show();
	        });
	        Button homeButton = new Button("Home");
	        homeButton.setOnAction(event-> {
	          new HomePage(primaryStage,userSession,"").show();
	    	});
	        Button aboutButton = new Button("About");
	        aboutButton.setOnAction(event-> {
	        	new About(primaryStage,userSession).show();
	    	});
	        Button forumButton = new Button("Forum");
	        forumButton.setOnAction(event-> {
	        	new ForumView(primaryStage,userSession).show();
	    	});
	        
	        left.getChildren().addAll(imageView,homeButton, forumButton,aboutButton);
	        Button loginButton = new Button(userSession.getFirstName());
	        loginButton.setOnAction(event-> {
	        	//new Login(primaryStage).show();
//	        	System.out.println("User");
	        	new Profile(primaryStage,userSession).show();
 	    	});
	        
	        loginButton.getStyleClass().add("btn-round-sm");
 	        loginButton.setStyle(
	        	"-fx-border-color: #2e7d32;" +
	        	"-fx-background-color: green;"+
	        	"-fx-text-fill: white;  "
	        		);


	        navbar.getChildren().addAll(left,loginButton);	         
	        navbar.getStyleClass().add("navbar");

	        return navbar;
	    }
	 
	 public String trimFirstWord(String input) {
	        String[] words = input.split("\\s+", 2); 
	        if (words.length > 1) {
	            return words[1];  
	        } else {
	            return ""; 
	        }
	    }
	 
	 

}
