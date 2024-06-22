package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;


public abstract class Database {

	public static Connection connection;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bad-lec";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
	public Database() {
		// TODO Auto-generated constructor stub
		connect();
	}
	
	 public static void connect() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
 	        } catch (ClassNotFoundException e) {
	            System.err.println("Error: MySQL JDBC driver not found.");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.err.println("Error: Failed to connect to the database.");
	            e.printStackTrace();
	        }
	    }

	    public Connection getConnection() {
	        return connection;
	    }

	    public void closeConnection() {
	        if (connection != null) {
	            try {
	                connection.close();
	                System.out.println("Database connection closed.");
	            } catch (SQLException e) {
	                System.err.println("Error: Failed to close the database connection.");
	                e.printStackTrace();
	            }
	        }
	    }
        
    
        

}


 
