package model;

import javafx.stage.Stage;

public class SharedStageHolder {

	private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

}
