//THIS IS VIEW SERVLET.THIS HANDLES PAGINATION FUNCTIONALITY

package com.highradius.internship;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewServlet() {
        super();
    }
    
    //POST METHOD
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    
		    response.setContentType("text/html;character=UTF-8");//SET THE CONTENT TYPE OF  RESPONSE PAGE
			User user=new User();//OBJECT OF USER CLASS
			String data_qry;
			
			PrintWriter out=response.getWriter();//PRINTWRITER
			JsonObject obj=new JsonObject();//JSONMOBJECT
			
			String spageid=request.getParameter("pageno");//GET THE PARAMETER PAGE TO DECIDE WHICH PAGE IS TO DISPLAYED
			
			//PERFORMED REQUIRED OPERATIONS ON PAGEID TO SET THE LIMIT FOR WHICH RECORDS ARE TO BE FETCHED
			int pageid=0;
			if(spageid.equals("nextpage"))
			{
				pageid=User.current_page+1;
			}
			else if(spageid.equals("previouspage"))
			{
				pageid=User.current_page-1;
			}
			else if(spageid.equals("1"))
			{
	 	    pageid=Integer.parseInt(spageid);
			}
			else {
				pageid=User.no_Of_Pages;
			}
	        request.getSession(false).setAttribute("CurrentPage", Integer.toString(pageid));//SET CURRENTPAGE ATTRIBUTE
	 	    int total=10;
	 	    if(pageid==1){pageid=pageid-1;}
	 	    else {
	 	    	pageid=pageid-1;
	 	    	pageid=pageid*total;
	 	    }
	 	    
	 	    //PREPARE QUERY FOR FETCHING RECORDS
	 	    	List<UserEntity> list=null;
	 	    	
	 	    	//IF RECORDS ARE FETCHED FOR OVERALL RECORDS
	 	    	if(User.search_flag==0) {
	 	    	data_qry=null;
	 	   		if(User.Level.equals("Level 1"))
	 	   		{
	 	   		data_qry="SELECT * FROM order_details limit "+pageid+","+total;
	 	   		}
	 	   		else if(User.Level.equals("Level 2"))
	 	   		{
	 	   			data_qry="SELECT * FROM order_details WHERE order_amount>10000 and order_amount<=50000 limit "+pageid+","+total;
	 	   		}
	 	   		else if(User.Level.equals("Level 3"))
	 	   		{
	 	   			data_qry="SELECT * FROM order_details WHERE order_amount>50000 limit "+pageid+","+total;
	 	   		
	 	    	}
	 	   		list=Functionalities.get_Records_database(data_qry);//CALL THE FUNCTION TO FETCH RECORDS AND STORE IN LIST
	 	    	}
	 	    	
	 	    	//IF RECORDS ARE FETCHED FOR FILTERED RECORDS
	 	    	else {
	 	    	data_qry=null;
	 	   		if(User.Level.equals("Level 1"))
	 	   		{
	 	   		data_qry="SELECT * FROM order_details WHERE Order_ID LIKE '"+User.search_item+"%' limit "+pageid+","+total;
	 	   		}
	 	   		else if(User.Level.equals("Level 2"))
	 	   		{
	 	   			data_qry="SELECT * FROM order_details WHERE order_amount>10000 and order_amount<=50000 and Order_ID LIKE '"+User.search_item+"%' limit "+pageid+","+total;
	 	   		}
	 	   		else if(User.Level.equals("Level 3"))
	 	   		{
	 	   			data_qry="SELECT * FROM order_details WHERE order_amount>50000 and Order_ID LIKE '"+User.search_item+"%' limit "+pageid+","+total;
	 	   		}
	 	   		list=Functionalities.get_Records_database(data_qry);
	 	    	}
	 	    	
	 	    	User.records=list;
	    		String msg="1";
		 		obj.addProperty("msg",msg);
		 		
		 		out.println(obj.toString());
				out.close();
				
	    }
}