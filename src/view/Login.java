package view;


import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Database;
import model.User;

public class Login {
    private Stage primaryStage;

    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label errorLabel = new Label();
    private Label space = new Label();

    public Login(Stage primaryStage) {
     //   this.db = new Database();
    	this.primaryStage=primaryStage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        
        // Tambahkan ImageView untuk 
        Image logoImage = new Image("/images/logo.png"); // Ganti dengan path atau URL logo Anda
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(60); // Sesuaikan lebar logo sesuai kebutuhan
        logoImageView.setFitHeight(60); // Sesuaikan tinggi logo sesuai kebutuhan
        logoImageView.setOnMouseClicked(event -> {
        	new LandingPage(primaryStage).show();
            // Add your custom actions here
        });

        HBox logoBox = new HBox(10, logoImageView);
        logoBox.setAlignment(Pos.TOP_LEFT); // Letakkan di kiri atas
        
        Label loginLabel = new Label("Login to your Account");
        Button loginButton = new Button("Sign In");
        Label signInLabel = new Label("Don't have an Account? ");
        Label signUpButton = new Label (" Sign Up");
        signUpButton.setOnMouseClicked(event -> {
        	new Register(primaryStage).show();
        });


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleLogin(event);
            }
        });
        
        VBox login = new VBox(10);
        
        loginButton.getStyleClass().addAll("btn","btn-round"); //ini ada masalah ni
         
        
        loginLabel.setStyle(
                "-fx-font-weight: 800; " +
                "-fx-font-size: 30px; " +
                "-fx-text-fill: green;");
        emailField.getStyleClass().add("input");
        errorLabel.setStyle(
        		"-fx-text-fill: red;"
        		);
        passwordField.getStyleClass().add("input");
        signUpButton.setStyle(
                "-fx-text-fill: green; " );
        
        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");
        
        HBox signUpBox = new HBox(signInLabel, signUpButton);
        

        Rectangle whiteBackground = new Rectangle(500, 400);
        whiteBackground.setFill(Color.WHITE);
        whiteBackground.setArcWidth(30);
        whiteBackground.setArcHeight(30);
        
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(whiteBackground, login);
        
        login.setAlignment(Pos.CENTER);  
        login.setSpacing(10); 
        signUpBox.setAlignment(Pos.CENTER);
        


        login.getChildren().addAll(
        		 loginLabel,
        		 space,
                 emailField,
                 passwordField,
                 errorLabel,
                 loginButton,
                 signUpBox
        );
        
        root.setStyle(
        		"-fx-background-image: url('/images/Gambar1.png'); " +
        				"-fx-background-size: cover; " +
        				"-fx-background-position: center center;");

        //----------------SETUP-----------------//
        root.setTop(logoBox);
        root.setCenter(stackPane);
        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        
        if (email.isEmpty() && password.isEmpty()) {
            errorLabel.setText("Email dan password harus diisi!");
            return;
        }
        if(email.isEmpty()) {
        	errorLabel.setText("Email harus diisi!");
            return;
        }

        if(password.isEmpty()) {
        	errorLabel.setText("Password harus diisi!");
            return;
        }
        
        // Validasi email harus mengandung karakter "@"
        if (!email.contains("@")) {
            errorLabel.setText("Email tidak valid. Harus mengandung karakter '@'!");
            return;
        }
        
        User userSession;
        userSession=User.validateUser(email, password);
        
        if(userSession == null) {
        	errorLabel.setText("User tidak ditemukan");
        	return;
        }else {
        	new HomePage(primaryStage,userSession,"").show();
        }

       

    }
}