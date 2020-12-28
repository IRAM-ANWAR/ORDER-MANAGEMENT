//THIS IS THE CLASS THAT CONTAIN MAIN FUNCTIONALITIES TO BE PERFORMED

package com.highradius.internship;

import java.sql.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysql.cj.xdevapi.JsonString;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Functionalities {
    
	public static Connection con;
	
	//VALIDATE METHOD-CHECKS DATABASE FOR GIVEN USERNAME AND PASSWORD
	public User validate(String username,String password)
	{
		User user=null;
		try {
			
			con = DatabaseConnection.initializeDatabase();//INITIALIZE DATABASE CONNECTION
			String qry="SELECT * FROM user_details WHERE username=? and password=?";//WRITE QUERY TO BE EXECUTED
			
			//PREPARING QUERY
			PreparedStatement pstm=con.prepareStatement(qry);
			pstm.setString(1, username);
			pstm.setString(2, password);
			
			//EXECUTING QUERY
			ResultSet set = pstm.executeQuery();
			
			//IF SET IS NOT EMPTY
			if(set.next())
			{
				user=new User();
				user.setLevel(set.getString("user_level"));
				user.setOrder_range(set.getString("order_range"));
				User.no_of_records=calculate_NoOfRecords();//SET THE TOTAL NUMBER OF RECORDS FOR THE USER LEVEL
				User.set_No_of_Pages(User.no_of_records);//CALCULATE NUMBER OF PAGES FOR THE USER LEVEL
			}
			
			//IF RESULTSET IS EMPTY--NO USER FOR GIVEN USERNAME AND PASSWORD
			else
			{
				user=null;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//CALCULATE THE NUMBER OF RECORDS FOR THE GIVEN USER
	public int calculate_NoOfRecords()
	{
		User user=new User();
		try {
			String count_qry=null;
			if(User.Level.equals("Level 1"))
			count_qry="SELECT COUNT(*) FROM order_details";
			else if(User.Level.equals("Level 2"))
				count_qry="SELECT COUNT(*) FROM order_details WHERE order_amount>10000 and order_amount<=50000";	
			else if(User.Level.equals("Level 3"))
				count_qry="SELECT COUNT(*) FROM order_details WHERE order_amount>50000";
			else{return 0;}
			PreparedStatement pr=con.prepareStatement(count_qry);
			ResultSet r=pr.executeQuery();
			if(r.next()) {
			return (r.getInt(1));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	//GET USERNAME FOR USER LEVEL
	public static String get_Username(String level)
	{
		try {
			String qry="SELECT username FROM user_details WHERE user_level='"+level+"';";
		PreparedStatement pstm=con.prepareStatement(qry);
		ResultSet set = pstm.executeQuery();
		if(set.next())
		{
			String username=set.getString(1);
			return username;
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//CALCULATE THE  NUMBER OF RECORDS FOR ORDER ID BEING SEARCHED
	public int calculate_searched_NoOfRecords(String search_item)
	{
		User user=new User();
		try {
			String count_qry=null;
			if(User.Level.equals("Level 1"))
			count_qry="SELECT COUNT(*) FROM order_details WHERE Order_ID LIKE '"+search_item+"%'";
			else if(User.Level.equals("Level 2"))
				count_qry="SELECT COUNT(*) FROM order_details WHERE order_amount>10000 and order_amount<=50000 and Order_ID LIKE '"+search_item+"%'";	
			else if(User.Level.equals("Level 3"))
				count_qry="SELECT COUNT(*) FROM order_details WHERE order_amount>50000 and Order_ID LIKE '"+search_item+"%'";
			else{return 0;}
			PreparedStatement pr=con.prepareStatement(count_qry);
			ResultSet r=pr.executeQuery();
			if(r.next()) {
			return (r.getInt(1));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	//GET THE RECORDS FROM THE DATABASE BY EXECUTING QRY AND SET THE VALUES IN USERENTITY CLASS
	public static List<UserEntity> get_Records_database(String qry)
	{
		List<UserEntity> list=new ArrayList<UserEntity>();
		User user=new User();
		try {
		PreparedStatement ps=con.prepareStatement(qry);
		ResultSet ds=ps.executeQuery();
		while(ds.next()) {
		UserEntity use=new UserEntity();
		use.setOrder_ID(ds.getInt(1));
		use.setCustomer_Name(ds.getString(2));
		use.setCustomer_ID(ds.getInt(3));
		use.setOrder_Amount(ds.getInt(4));
		use.setApproval_Status(ds.getString(5));
		use.setApproved_By(ds.getString(6));
		use.setNotes(ds.getString(7));
		use.setOrder_Date(ds.getDate(8));
		list.add(use);
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void insert_into_database(String qry) {
		PreparedStatement pr;
		try {
		pr = con.prepareStatement(qry);
		pr.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}