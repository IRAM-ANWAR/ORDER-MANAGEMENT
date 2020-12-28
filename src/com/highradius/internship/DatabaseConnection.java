package com.highradius.internship;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

public class DatabaseConnection {
	
	public static Connection con=null;
	public static Connection initializeDatabase() 
			throws SQLException,ClassNotFoundException{
		
		if(con!=null)
			return con;
		String dbDriver = "com.mysql.jdbc.Driver"; 
	    String dbURL = "jdbc:mysql:// localhost:3306/"; 
	    String dbName = "winter_internship"; 
	    String dbUsername = "root"; 
	    String dbPassword = "IrAnSh@25"; 
	    
	    Class.forName(dbDriver);
	    con=DriverManager.getConnection(dbURL+dbName,dbUsername,dbPassword);
	    return con;
	}
}
