package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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

public class Register {
    private Stage primaryStage;
    private Database db;
    Connection connection;
    ArrayList<User> users=User.retreiveRecord();

    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label errorLabel = new Label();

    public Register(Stage primaryStage) {
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
    
    Label registerLabel = new Label("Register your Account");
    Button registerButton = new Button("Create an account");
    Label signInLabel = new Label("Already have an Account? ");
    Label signInButton = new Label (" Sign in");
    
    signInButton.setOnMouseClicked(event -> {
    	new Login(primaryStage).show();
    });

    registerButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
				handleRegistration(event);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    });
    
    VBox register = new VBox(10);
    
    registerButton.getStyleClass().addAll("btn","btn-round"); 
    registerLabel.setStyle(
            "-fx-font-weight: 800; " +
            "-fx-font-size: 30px; " +
            "-fx-text-fill: green;");    
    emailField.getStyleClass().add("input");
    
  
    
    errorLabel.setStyle(
    		"-fx-text-fill: red;"
    		);

    firstNameField.getStyleClass().add("input");
    lastNameField.getStyleClass().add("input");
    
    passwordField.getStyleClass().add("input");
    
    signInButton.setStyle(
            "-fx-text-fill: green; " );
    
    emailField.setPromptText("Email");
    firstNameField.setPromptText("First Name");
    lastNameField.setPromptText("Last Name");
    passwordField.setPromptText("Password");
    
    HBox signInBox = new HBox(signInLabel, signInButton);
    

    Rectangle whiteBackground = new Rectangle(500, 400);
    whiteBackground.setFill(Color.WHITE);
    whiteBackground.setArcWidth(30);
    whiteBackground.setArcHeight(30);
    
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(whiteBackground, register);
    
    register.setAlignment(Pos.CENTER);  
    register.setSpacing(10); 
    signInBox.setAlignment(Pos.CENTER);
    


    register.getChildren().addAll(
    		 registerLabel,
             firstNameField,
             lastNameField,
             emailField,
             passwordField,
             errorLabel,
             registerButton,
             signInBox
    );
    
    root.setStyle(
    		"-fx-background-image: url('/images/background.png'); " +
    				"-fx-background-size: cover; " +
    				"-fx-background-position: center center;");

    //----------------SETUP-----------------//
    root.setTop(logoBox);
    root.setTop(logoBox);
    root.setCenter(stackPane);
    // Tambahkan CSS eksternal jika diperlukan
    Scene scene = new Scene(root, 700, 500);
    scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();

   }
   
    private void handleRegistration(ActionEvent event) throws SQLException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            errorLabel.setText("please fill the required form");
            return;
        }
        
        if(firstName.length()>7) {
        	errorLabel.setText("firstName maximum charachter is 7");
            return;
        }
  
        if (!email.contains("@")) {
            errorLabel.setText("please enter the correct email format");
            return;
        } 
        
        for(User user:users) {
        	if(user.getEmail() == email) {
        		 errorLabel.setText("email already been added");
                 return;
        	}
        }

        // Simpan data ke database
        String insertSQL = "INSERT INTO MsUser (firstName,lastName,Email,Password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = Database.connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            // Execute the insert statement
            preparedStatement.executeUpdate();


        // Tampilkan pesan sukses atau alihkan ke halaman lain jika diperlukan
        System.out.println("Registrasi berhasil. Redirect ke halaman lain.");
        new Login(primaryStage).show();
        	
        }catch (Exception e) {
        	System.out.println(e);
        }

        }
    }

