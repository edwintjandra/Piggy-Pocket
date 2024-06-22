package view;

import java.sql.SQLException;

import components.Navbar;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LandingPage {

	private Stage primaryStage;
	public LandingPage(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage=primaryStage;
	}
	
	public void show() {
		BorderPane root = new BorderPane();
		Image logo = new Image("/images/logo.png"); // Adjust the path to your image.
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(60); // Set the desired width
        logoView.setFitHeight(60);
        root.setTop(logoView);		 
		VBox container = new VBox();
		container.getStyleClass().add("container");
		
		HBox content=new HBox(20);
 		VBox desc=new VBox(10);
		desc.getStyleClass().add("landingDesc");
		Label heading = new Label("WELCOME TO\nPIGGY POCKET");
	    heading.getStyleClass().add("landingHeading"); // Apply CSS styling if needed
        Label paragraph = new Label(
        		"Budget, Save, Thrive"
	        );
	    paragraph.getStyleClass().add("landingParagraph"); // Apply CSS styling if needed

        // Create buttons
        Button button1 = new Button("Create an account");
        button1.setPrefWidth(150); 
    	button1.setOnAction(event-> {
    		 new Register(primaryStage).show();
    	});
    	button1.getStyleClass().addAll("btn-sm","btn-outline");
    	
        Button button2 = new Button("Sign in");
        button2.setPrefWidth(150); 
        button2.setOnAction(event-> {
   		 	new Login(primaryStage).show();
    	});
        button2.getStyleClass().add("btn-sm");
	    desc.getChildren().addAll(heading,paragraph,button1,button2);
		
		
		Image image = new Image("/images/thumbnail.png"); // Adjust the path to your image.
        ImageView imageView = new ImageView(image);
        
		
		content.getChildren().addAll(desc,imageView);
		container.getChildren().addAll(content);
		root.setCenter(container);
        //add external css
        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
   }

}
