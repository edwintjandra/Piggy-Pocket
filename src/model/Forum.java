package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Forum {
	private String title;
	private String content;
	private String publisher;
	
	
	
	public Forum(String title, String content, String publisher) {
		super();
		this.title = title;
		this.content = content;
		this.publisher = publisher;
	}
	
	public static ArrayList<Forum> retreiveRecord() {
		ArrayList<Forum> forums=new ArrayList<>();
		String query = "SELECT title,content,publisher FROM Forum";
		try (PreparedStatement preparedStatement = Database.connection.prepareStatement(query)) {
		    ResultSet resultSet = preparedStatement.executeQuery();

		    while (resultSet.next()) {
		    	String title=resultSet.getString("title");
		    	String content=resultSet.getString("content");
		    	String publisher=resultSet.getString("publisher");
		        
		        // Create a new ArrayList to store the data
		    	Forum forum=new Forum(title,content,publisher);
		    	forums.add(forum);
		        
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return forums;
	}
	
	public static void insertRecord(String title, String content, String publisher) {
	    String query = "INSERT INTO Forum (title, content, publisher) VALUES (?, ?, ?)";
	    try (PreparedStatement preparedStatement = Database.connection.prepareStatement(query)) {
	        preparedStatement.setString(1, title);
	        preparedStatement.setString(2, content);
	        preparedStatement.setString(3, publisher);

	        // Execute the update
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	

}
