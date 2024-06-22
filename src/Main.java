import model.Database;
import model.SharedStageHolder;
import model.User;
import view.About;
import view.AddRecord;
import view.HomePage;
import view.LandingPage;
import view.Login;
import view.Profile;
import view.Register;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.HomePage;

public class Main extends Application {

	public Main() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {    
        SharedStageHolder.setPrimaryStage(primaryStage); // Set the primary stage
        primaryStage.setTitle("JavaFX Shared Variable Example");
        Database.connect();
        primaryStage.setResizable(false);
        
        
        new LandingPage(primaryStage).show();
//        new HomePage(primaryStage,new User("Edwin","Tjandraatmadja", "edwintjandra8@gmail.com", "test",1),"").show();
     }
}



 