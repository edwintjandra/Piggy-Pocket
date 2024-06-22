package view;


import model.Record;
import java.util.ArrayList;
 
import java.util.Date;
import java.util.Locale;

import components.Navbar;
 
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
 
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Income;
import model.Outcome;
import model.SharedStageHolder;
import model.User;

import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
 
 
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
 
public class HomePage {
    private Stage primaryStage;
    User userSession;

    ArrayList<Income> incomeList=Income.retreiveRecord();
     ArrayList<Outcome> outcomeList=Outcome.retreiveRecord();    
     ArrayList<Record> combinedRecords= new ArrayList<>();
     SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("en"));

     
     String filterSorting="Descending";
     String filterType="All";
     String search="";
     VBox recordList=new VBox(10); //root center layout
	 HBox filter=filterButton(recordList);
     
     
     public HomePage(Stage primaryStage,User userSession,String search){
    	 this.primaryStage=primaryStage;
    	 this.userSession=userSession;
    	 this.search=search;
     }
 
	public void show() {
		BorderPane root = new BorderPane();
		VBox scrollContent=new VBox(); 
		ScrollPane scroll = new ScrollPane(scrollContent);
		scroll.setFitToWidth(true); 
		Navbar navbar = new Navbar(primaryStage,userSession);
		HBox navigationBar = navbar.createNavbar();
		scrollContent.getChildren().add(navigationBar);
		VBox container = new VBox();
		container.getStyleClass().add("container");

		
		//create footer
		HBox footer=createFooter();
		
		
        resetRecord(recordList,filter);
        insertAndSortRecord(filterSorting);
        printRecord(filterType,recordList);
        
        
        container.getChildren().add(recordList);        
        scrollContent.getChildren().add(container);
        root.setCenter(scroll);
        root.setBottom(footer);
        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	
	public void resetRecord(VBox recordList,HBox filter) {
	     HBox header=createHeader();
	     
	    combinedRecords.clear();
		recordList.getChildren().clear();
        recordList.getChildren().add(header);
        recordList.getChildren().add(filter);
	}
	
	public HBox filterButton(VBox recordList) {
		HBox filter=new HBox(255);
		
		HBox sorting=new HBox(3);
		ComboBox<String> sortingOrderComboBox = new ComboBox<>();
	    sortingOrderComboBox.getItems().addAll("Ascending", "Descending");
	    sortingOrderComboBox.setValue("Descending");

	    sortingOrderComboBox.setOnAction(event -> {
            filterSorting = sortingOrderComboBox.getValue();
            // You can perform actions based on the selected option here
            resetRecord(recordList,filter);
            insertAndSortRecord(filterSorting);
            printRecord(filterType,recordList);

        });
	    sortingOrderComboBox.getStyleClass().addAll("btn","btn-outline","btn-sm","btn-round-sm");
	    
	    ComboBox<String> sortingTypeComboBox = new ComboBox<>();
	    sortingTypeComboBox.getItems().addAll("All", "Outcome", "Income");
	    sortingTypeComboBox.setValue("All");
	    
	    sortingTypeComboBox.setOnAction(event -> {
            filterType = sortingTypeComboBox.getValue();
            resetRecord(recordList,filter);
            insertAndSortRecord(filterSorting);
            printRecord(filterType,recordList);
        });
	    sortingTypeComboBox.getStyleClass().addAll("btn","btn-outline","btn-sm","btn-round-sm");

	    
	    Button export=new Button("Export Data");
	    export.setOnAction(event -> {
	    	confirmationDialog();
	    });
	    export.getStyleClass().addAll("btn","btn-sm","btn-outline","btn-round-sm");

	    sorting.getChildren().addAll(sortingOrderComboBox,sortingTypeComboBox);
	    filter.getChildren().addAll(sorting,export);

        return filter;
	}
	
	private void confirmationDialog() {
        Alert confirmationDialog = new Alert(AlertType.WARNING);
        confirmationDialog.setTitle("Confirmation Dialog");
        confirmationDialog.setHeaderText("Are you sure you want to export the data?");
        
        // Add OK and Cancel buttons to the dialog
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        confirmationDialog.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        // Show the dialog and wait for the user's response
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOK) {
                // User clicked OK, perform the export
            	exportToNotepad(combinedRecords,"./output.txt");
            } else {
             }
        });
    }
	
	public  void exportToNotepad(ArrayList<Record> records, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Iterate through the records and write each record to the file
            writer.write("income/outcome data: "+userSession.getFirstName()+"\n");
            for (Record record : records) {
            	if(record.getUserId() == userSession.getUserId()) {
                    writer.write(record.toString() + System.lineSeparator());
            	}
            }
            System.out.println("Export successful!");
        } catch (IOException e) {
            System.err.println("Error exporting records: " + e.getMessage());
        }
        
        
        new HomePage(primaryStage,userSession,search).show();
    }
 
	 
	
	public void printRecord(String option, VBox recordList) {
        for (Record item : combinedRecords) {
            if (item.getName().toLowerCase().contains(search.toLowerCase()) && item.getUserId() == userSession.getUserId()) {
                HBox incomeBox = new HBox(15);
                Image incomeImg = new Image("/images/income.png");
                ImageView incomeImage = new ImageView(incomeImg);
                incomeImage.setFitWidth(20);
                incomeImage.setFitHeight(20);

                Image outcomeImg = new Image("/images/outcome.png");
                ImageView outcomeImage = new ImageView(outcomeImg);
                outcomeImage.setFitWidth(20);
                outcomeImage.setFitHeight(20);
                
                Image trashImg = new Image("/images/trash.png");
                ImageView trashImage = new ImageView(trashImg);
                trashImage.setFitWidth(20);
                trashImage.setFitHeight(20);
                
                trashImage.setOnMouseClicked(event -> {
                	if(item instanceof Income) {
                		Income income=(Income) item;
                		
                		Income.deleteRecordById( Integer.parseInt(income.getIncomeID()) );
        	            new HomePage(primaryStage,userSession,search).show();

                	}else if(item instanceof Outcome) {
                	    Outcome outcome=(Outcome) item;
                		Outcome.deleteRecordById( Integer.parseInt(outcome.getOutcomeID()) );
        	            new HomePage(primaryStage,userSession,search).show();

                	}
                 });
                

                if ((option.equals("All") || option.equals("Income")) && item instanceof Income) {
	                int boxLength = 80 - item.getName().length();
	                Label income = new Label(String.format("%-20s %-"+boxLength+"s ",
	                        "+ Rp." + formatNumber( item.getTotal() ),
	                        item.getName()));
	                
	                Label date=new Label(item.getDate());
	                incomeBox.getChildren().addAll(incomeImage, income,date,trashImage);
	                incomeBox.getStyleClass().add("homeIncomeBox");
	                recordList.getChildren().add(incomeBox);
	            } else if ((option.equals("All") || option.equals("Outcome")) && item instanceof Outcome) {
	                int boxLength = 80 - item.getName().length();
	                Label outcome = new Label(String.format("%-20s %-"+boxLength+"s  ",
	                        "- Rp." + formatNumber( item.getTotal()),
	                        item.getName()));

	                Label date=new Label(item.getDate());
	                incomeBox.getChildren().addAll(outcomeImage, outcome,date,trashImage);
	                incomeBox.getStyleClass().add("homeIncomeBox");
	                recordList.getChildren().add(incomeBox);
	            }
            }
        }
    }
	
	
	public void insertAndSortRecord(String option) {
	    combinedRecords.addAll(incomeList);
	    combinedRecords.addAll(outcomeList);

	    //satu
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("en"));

	    for (int i = 0; i < combinedRecords.size(); i++) {
	        for (int j = 0; j < combinedRecords.size() - 1; j++) {
	            try {
	                Date date1 = dateFormat.parse(combinedRecords.get(j).getDate());
	                Date date2 = dateFormat.parse(combinedRecords.get(j + 1).getDate());

	                if ((option.equals("Descending") && date1.compareTo(date2) < 0)
	                        || (option.equals("Ascending") && date1.compareTo(date2) > 0)) {
	                    // Swap the records by creating a temporary variable
	                    Record temp = combinedRecords.get(j);
	                    combinedRecords.set(j, combinedRecords.get(j + 1));
	                    combinedRecords.set(j + 1, temp);
	                }
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	
	public HBox createFooter(){
		  HBox footer=new HBox(0); //root balance& addrecord
		  Button addRecord=new Button("Add record");
 			
	        addRecord.setOnAction(event-> {
	        	moveAddRecord();
	    	});
	        footer.getChildren().addAll(
	        		addRecord
	        		);
	        footer.setAlignment(Pos.CENTER_RIGHT);  
	        footer.getStyleClass().add("homeRecord"); //adding style to record;
		return footer;
	}
	
	 public HBox createHeader() {
		    HBox header = new HBox();  //root header box
	        VBox goalBar=new VBox();  
	        TextField inputBar = new TextField(search);
	        goalBar.getChildren().addAll(new Label("Search income/outcome name"), inputBar);
	        goalBar.getStyleClass().add("goalBar");      
	        HBox.setHgrow(goalBar, javafx.scene.layout.Priority.ALWAYS);
	        inputBar.setStyle("-fx-prompt-text-fill: darkgray;");
	        inputBar.setPromptText("press enter");

	        inputBar.setOnAction(event -> {
	            search = inputBar.getText();
	            new HomePage(primaryStage,userSession,search).show();
	        });
	        
 	        header.getChildren().addAll(goalBar); 
	        header.getStyleClass().add("header"); //adding style to header
	        
	        return header;
	    }
	
	 private  String formatNumber(double value) {
	        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	        return numberFormat.format(value);
	    }
	 
	void moveAddRecord() {
		new AddRecord(primaryStage,userSession).show();
	}
}