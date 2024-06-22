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

public class Income extends Record{
	
	private final String IncomeID;
	public Income(String IncomeID,String name, Double total, String date,int userId) {
		super(name, total, date,userId);
		this.IncomeID=IncomeID;
	}
	
	public static ArrayList<Income> retreiveRecord() {
		ArrayList<Income> incomes=new ArrayList<>();
		String query = "SELECT IncomeID,Name,TotalIncome,DateIncome,userId FROM Income";
		try (PreparedStatement preparedStatement = Database.connection.prepareStatement(query)) {
		    ResultSet resultSet = preparedStatement.executeQuery();

		    while (resultSet.next()) {
		    	String IncomeID = resultSet.getString("IncomeID");
		    	String Name = resultSet.getString("Name");
		    	Double TotalIncome = resultSet.getDouble("TotalIncome");
		    	String DateIncome = resultSet.getString("DateIncome");
		    	int userId= resultSet.getInt("userId");
		        
		        // Create a new ArrayList to store the data
		        Income income=new Income(IncomeID,Name,TotalIncome,DateIncome,userId);
		        incomes.add(income);
		        
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return incomes;
	}
	
	public static void insertRecord(String Name,double TotalIncome, String DateIncome, User userSession) throws SQLException {
	    
        String insertSQL = "INSERT INTO Income (Name,  TotalIncome,DateIncome,userId) VALUES (?, ?, ?,?)";

        try (PreparedStatement preparedStatement = Database.connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, Name);
            preparedStatement.setDouble(2, TotalIncome);
            preparedStatement.setString(3, DateIncome);
            preparedStatement.setInt(4, userSession.getUserId());


            // Execute the insert statement
            preparedStatement.executeUpdate();

            System.out.println("Product added successfully!");
        }
	 }
	
	public static void deleteAllRecords() {
	    String deleteSQL = "DELETE FROM Income";

	    try (PreparedStatement preparedStatement = Database.connection.prepareStatement(deleteSQL)) {
	        // Execute the delete statement
	        preparedStatement.executeUpdate();

	        System.out.println("All records deleted successfully!");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void deleteRecordById(int recordId) {
	    String deleteSQL = "DELETE FROM Income WHERE IncomeID = ?";

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


	public String getIncomeID() {
		return IncomeID;
	}

	 
 
	
}

 
