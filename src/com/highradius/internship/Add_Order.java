//THIS IS A SERVLET THAT MANAGES ADD OPERATION TO DATABASE

package com.highradius.internship;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/Add_Order")
public class Add_Order extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Add_Order() {
		super();
	}

	//POST METHOD
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Functionalities func_obj=new Functionalities();
		UserEntity usen=new UserEntity();
		PrintWriter out=response.getWriter();
		JsonObject obj=new JsonObject();
		int count=0;
		try {
			
			//GET ORDER_ID
			int Order_ID=Integer.parseInt(request.getParameter("oid_add"));
			
			//CHECK IF ORDER ID ALREADY EXISTS IN THE DATABASE
			String query="SELECT COUNT(*) FROM order_details WHERE Order_ID="+Order_ID+";";
			PreparedStatement pr=Functionalities.con.prepareStatement(query);
			ResultSet r=pr.executeQuery();
			if(r.next()) {
			count=r.getInt(1);
			}
			
			//IF NO RECORD WITH PROVIDED ORDER ID EXISTS
			if(count==0)
			{
				//GET THE PARAMETERS AND SET IN METHODS OF USERENTITY CLASS
			usen.setOrder_ID(Integer.parseInt(request.getParameter("oid_add")));
			String Sdate=request.getParameter("od_add");
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			Date date1=formatter.parse(Sdate);
			usen.setOrder_Date(date1);
			usen.setCustomer_Name(request.getParameter("cname_add"));
			usen.setCustomer_ID(Integer.parseInt(request.getParameter("cnum_add")));
			usen.setOrder_Amount(Integer.parseInt(request.getParameter("oamt_add")));
			usen.setNotes(request.getParameter("notes_add"));
			
			if(usen.getOrder_Amount()<=10000) {
				usen.setApproval_Status("Approved");
				usen.setApproved_By(func_obj.get_Username(User.Level));
			}else {
				usen.setApproval_Status("Awaiting Approval");
				usen.setApproved_By(" ");
			}
			
			//PREPARE QUERY TO INSERT RECORD
			String qry=null;
			qry="INSERT INTO order_details VALUES(";
			qry=qry+usen.getOrder_ID()+",'";
			qry=qry+usen.getCustomer_Name()+"',";
			qry=qry+usen.getCustomer_ID()+",";
			qry=qry+usen.getOrder_Amount()+",'";
			qry=qry+usen.getApproval_Status()+"','";
			qry=qry+usen.getApproved_By()+"','";
			qry=qry+usen.getNotes()+"','";
			qry=qry+Sdate+" 00:00:00');";
			
			func_obj.insert_into_database(qry);
			request.getSession(false).setAttribute("CurrentPage", Integer.toString(User.current_page));//SET CURRENT PAGE 
			//SET LIMIT TO FETCH RECORDS
			int total=10;
			int pageid=(User.current_page-1)*total;

			//FETCH RECORDS AND SET IN USER.l LIST
			String data_qry=null;
			if(User.search_flag==0)
			{
			data_qry=data_qry="SELECT * FROM order_details limit "+pageid+","+total;
			}
			else
			{
				data_qry="SELECT * FROM order_details WHERE Order_ID LIKE '"+User.search_item+"%' limit "+pageid+","+total;
			}
			List<UserEntity> list=Functionalities.get_Records_database(data_qry);
			User user=new User();
			User.records=list;
			
			User.initial_records=User.records;
			User.no_of_records+=1;//INCREEMENT NUMBER OF RECORDS BY 1
			User.set_No_of_Pages(User.no_of_records);
			String msg="1";
			obj.addProperty("msg",msg);
			
			out.println(obj.toString());
			out.close();
		}
		
		//IF RECORD WITH PROVIDED ORDER ID ALREADY EXISTS
		else {
			request.getSession(false).setAttribute("CurrentPage", Integer.toString(User.current_page));
			String msg="0";
			obj.addProperty("msg",msg);
			
			out.println(obj.toString());
			out.close();
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
