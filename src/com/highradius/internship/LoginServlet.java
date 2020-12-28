//THIS IS LOGIN-SERVLET...THIS SERVLET HANDLES THE AUTHENTICITY OF USER OF LOGIN TO THE APPLICATION

package com.highradius.internship;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public LoginServlet() {
        super();
    }

    //POST METHOD
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user=new User();//OBJECT OF USER CLASS that contains attributes at user level
		
		JsonObject obj=new JsonObject();//JsonObject object-obj
		PrintWriter out=response.getWriter();//PRINTWRITER
		
	    String username=request.getParameter("username");//GET USERNAME
	    String password=request.getParameter("password");//GET PASSWORD
	    
		
        Functionalities functionalities=new Functionalities();//object of functionalities class
        
	 	user=functionalities.validate(username,password);//CALL THE VALIDATE METHOD FOR THE USER
	 	
	 	//GET SESSION AND PREPARE QUERY TO DISPLAY RECORDS
	 	String msg=null;//MESSAGE TO BE SENT AS RESPONSE
	 	
	 	//IF A USER FOR GIVEN USERNAME AND PASSWORD EXISTS
	 	if (user!=null) {
	    	
	 		//GET HTTP SESSION
	    	HttpSession session=request.getSession();
	    	session.setAttribute("currentUser", user);
	    	
	    	//PREPARING QUERY TO GET RECORDS
	    	String data_qry=null;
			if(User.Level.equals("Level 1"))
			{
			data_qry="SELECT * FROM order_details limit 0,10";
			}
			else if(User.Level.equals("Level 2"))
			{
				data_qry="SELECT * FROM order_details WHERE order_amount>10000 and order_amount<=50000 limit 0,10";
			}
			else if(User.Level.equals("Level 3"))
			{
				data_qry="SELECT * FROM order_details WHERE order_amount>50000 limit 0,10";
			}
			
			//CALL THE METHOD TO EXECUTE THE PREPARED QUERY
			List<UserEntity> list=Functionalities.get_Records_database(data_qry);
			
			//SEND A RESPONSE AS "1" IF SUCCESSFULLY PERFORMED OPERATIONS
			msg="1";
			obj.addProperty("msg",msg);
			
			out.println(obj.toString());
    		User.records=list;
    		User.initial_records=list;
    		//SET CURRENTPAGE ATTRIBUTE TO 1
    		session.setAttribute("CurrentPage", "1");
        }
	 	
	 	//IF NO USER EXISTS SEND RESPONSE AS "3" AND DISPLAY ERROR
	 	else {
	 		msg="3";
	 		obj.addProperty("msg",msg);
	 		out.println(obj.toString());
	 	}
     }
}
