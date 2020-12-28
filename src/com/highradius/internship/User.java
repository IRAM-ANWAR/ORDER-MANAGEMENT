//THIS IS USER CLASS 
//THIS CLASS CONTAINS ATTRIBUTES AT USER LEVEL
package com.highradius.internship;

import java.sql.ResultSet;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class User {
	public static int no_of_records;//NO OF RECORDS
	public static int no_Of_Pages;//NO OF PAGES
	public static String Level;//USER LEVEL
	private static String order_range;//order range of user
	public static int current_page;//CURRENT PAGE BEING DISPLAYED
	public static int search_flag=0;//SEARCH FLAG TO DETERMINE RECORDS ARE BEING SEARCHED OR NOT
	public static String search_item=null;//ITEM BEING SEARCHED
	public static String data_qry=null;
	public static List<UserEntity> records=new ArrayList<UserEntity>();
	public static List<UserEntity> initial_records=new ArrayList<UserEntity>();
	public User()
	{
		
	}
	public static void set_No_of_Pages(int n) {
		if(n==0)
			no_Of_Pages=1;
		else if(n%10==0){
		no_Of_Pages=n/10;
		}
		else {
			no_Of_Pages=n/10+1;
		}
	}
	
	public static String getLevel() {
		return Level;
	}
	public static void setLevel(String level) {
		Level = level;
	}
	public static String getOrder_range() {
		return order_range;
	}
	public static void setOrder_range(String order_range) {
		User.order_range = order_range;
	}	
}

