package com.highradius.internship;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Approve_Reject_Servlet
 */
@WebServlet("/Approve_Reject_Servlet")
public class Approve_Reject_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Approve_Reject_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    //POST METHOD
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Functionalities func_obj=new Functionalities();
		UserEntity usen=new UserEntity();
		
		PrintWriter out=response.getWriter();
		JsonObject obj=new JsonObject();
		try {
			
		//GET PARAMETERS
		usen.setOrder_ID(Integer.parseInt(request.getParameter("approve_oid")));
		String button_value=(String)request.getParameter("my-button");
		if(button_value.equals("Approve"))
		{
			usen.setApproval_Status("Approved");
			usen.setApproved_By(func_obj.get_Username(User.Level));
		}
		else
		{
			usen.setApproval_Status("Rejected");
			usen.setApproved_By("   ");
		}
		
		//PREPARE QUERY
		String qry=null;
	    qry="UPDATE order_details SET Approval_Status='";
	    qry=qry+usen.getApproval_Status()+"',Approved_By='"+usen.getApproved_By()+"' WHERE Order_ID="+usen.getOrder_ID()+";";
	    func_obj.insert_into_database(qry);
	    request.getSession(false).setAttribute("CurrentPage", Integer.toString(User.current_page));
	    int total=10;
		int pageid=(User.current_page-1)*total;
		
	    String data_qry=null;
	    if(User.search_flag==0) {
	    if(User.Level.equals("Level 2"))
		{
			data_qry="SELECT * FROM order_details WHERE order_amount>10000 and order_amount<=50000 limit "+pageid+","+total;
			
		}
		else if(User.Level.equals("Level 3"))
		{
			data_qry="SELECT * FROM order_details WHERE order_amount>50000 limit "+pageid+","+total;
			
		}
	    }
	    else {
	    	
	    	 if(User.Level.equals("Level 2"))
	  		{
	    		data_qry="SELECT * FROM order_details WHERE order_amount>10000 and order_amount<=50000 and Order_ID LIKE '"+User.search_item+"%' limit "+pageid+","+total;
	  			
	  		}
	  		else if(User.Level.equals("Level 3"))
	  		{
	  			data_qry="SELECT * FROM order_details WHERE order_amount>50000 and Order_ID LIKE '"+User.search_item+"%' limit "+pageid+","+total;
	  			
	  		}
	    	
	    }
		List<UserEntity> list=Functionalities.get_Records_database(data_qry);//GET MODIFIED RECORDS
		
		User.records=list;
		User.initial_records=User.records;
		String msg="1";
		obj.addProperty("msg",msg);
	
		out.println(obj.toString());
		out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	}
