package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Outcome extends Record{


	private final String OutcomeID;
   
	public Outcome(String OutcomeID,String name, Double total, String date,int userId) {
		super(name, total, date,userId);
		this.OutcomeID=OutcomeID;
		// TODO Auto-generated constructor stub
	}
	 
	
	public static ArrayList<Outcome> retreiveRecord() {
		ArrayList<Outcome> outcomes=new ArrayList<>();
		
		String query = "SELECT OutcomeID,Name,TotalOutcome,DateOutcome,userId FROM Outcome";
		try (PreparedStatement preparedStatement = Database.connection.prepareStatement(query)) {
		    ResultSet resultSet = preparedStatement.executeQuery();

		    while (resultSet.next()) {
		    	String OutcomeID = resultSet.getString("OutcomeID");
		    	String Name = resultSet.getString("Name");
		    	Double TotalOutcome = resultSet.getDouble("TotalOutcome");
		    	String DateOutcome = resultSet.getString("DateOutcome");
		    	int userId= resultSet.getInt("userId");

		        // Create a new ArrayList to store the data
		        Outcome outcome=new Outcome(OutcomeID,Name,TotalOutcome,DateOutcome,userId);
		        outcomes.add(outcome);
		        
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return outcomes;
	}

	 
	
	public static void insertRecord(String Name,double TotalOutcome, String DateOutcome,User userSession) throws SQLException {
	    	  String insertSQL = "INSERT INTO Outcome (Name,  TotalOutcome,DateOutcome,userId) VALUES (?, ?, ?,?)";

	          try (PreparedStatement preparedStatement = Database.connection.prepareStatement(insertSQL)) {
	              preparedStatement.setString(1, Name);
	              preparedStatement.setDouble(2, TotalOutcome);
	              preparedStatement.setString(3, DateOutcome);
	              preparedStatement.setInt(4, userSession.getUserId());

	              // Execute the insert statement
	              preparedStatement.executeUpdate();

	              System.out.println("Product added successfully!");
	          }
		    }
	
	public static void deleteAllRecords() {
	    String deleteSQL = "DELETE FROM Outcome";

	    try (PreparedStatement preparedStatement = Database.connection.prepareStatement(deleteSQL)) {
	        // Execute the delete statement
	        preparedStatement.executeUpdate();

	        System.out.println("All records deleted successfully!");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void deleteRecordById(int recordId) {
	    String deleteSQL = "DELETE FROM Outcome WHERE OutcomeID = ?";

	    try (PreparedStatement preparedStatement = Database.connection.prepareStatement(deleteSQL)) {
	        preparedStatement.setInt(1, recordId);

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Record with ID " + recordId + " deleted successfully!");
	        } else {
	            System.out.println("No record found with ID " + recordId);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public String getOutcomeID() {
		return OutcomeID;
	}
	 
	
	}

 