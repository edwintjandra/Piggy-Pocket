package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int userId;
	
	PreparedStatement asd;
	
	public User(String firstName,String lastName, String email, String password,int userId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userId=userId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	 public static User validateUser(String email, String password) {
			String query = "SELECT * FROM MsUser WHERE email = ? AND password = ?";

	        try (PreparedStatement preparedStatement =  Database.connection.prepareStatement(query)) {
	            preparedStatement.setString(1, email);
	            preparedStatement.setString(2, password);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            	if (resultSet.next()) {
	                    // If a user with provided credentials exists, return the User object
	                    String firstName = resultSet.getString("firstName");
	                    String lastName = resultSet.getString("lastName");
	                    int userId=resultSet.getInt("userId");
	                    return new User(firstName,lastName, email, password,userId);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle or log the exception appropriately
 	        }
	        
	        return null;
	    }
	 
	 public static void updateUser(String userID,String firstName,String lastName,String email,String password) {
		  if (firstName.isEmpty() || lastName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
			  System.out.println("please fill the required form");
	            return;
	        }
	        
	    	String updateSQL = "UPDATE msUser SET firstName = ?, lastName = ?, email = ?,password = ?  WHERE userID = ?";
 	        try (PreparedStatement preparedStatement = Database.connection.prepareStatement(updateSQL)) {
	            preparedStatement.setString(1, firstName);
	            preparedStatement.setString(2, lastName);
	            preparedStatement.setString(3, email);
	            preparedStatement.setString(4, password);
	            preparedStatement.setString(5, userID);
	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Product with userID " + userID + " updated successfully.");
	            } else {
	                System.out.println("Product with userID " + userID + " not found or not updated.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public static ArrayList<User> retreiveRecord() {
			ArrayList<User> users=new ArrayList<>();
			
			String query = "SELECT userId,firstName,lastName,email,password FROM msuser";
			try (PreparedStatement preparedStatement = Database.connection.prepareStatement(query)) {
			    ResultSet resultSet = preparedStatement.executeQuery();

			    while (resultSet.next()) {
			    	String firstName = resultSet.getString("firstName");
			    	String lastName= resultSet.getString("lastName");
			    	String email= resultSet.getString("email");
			    	String password= resultSet.getString("password");
			    	int userId=  resultSet.getInt("userId");

			        // Create a new ArrayList to store the data
			    	User user=new User(firstName,lastName, email, password,userId);
			    	users.add(user);
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			}
			
			return users;
		}

	 
	
}
