//THIS IS SEARCH SERVLET.THIS HANDLES SEARCH OPERATION

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public SearchServlet() {
        super();
      
    }

	//POST METHOD
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;character=UTF-8");//SET THE CONTENT TYPE OF  RESPONSE PAGE
		//USER CLASS OBJECT
		User user=new User();
		
		PrintWriter out=response.getWriter();
		JsonObject obj=new JsonObject();
		
		//GET THE ITEM TO BE SEARCHED
	    User.search_item=request.getParameter("search");
	    
	    //IF ITEM TO BE SEARCHED IS NULL
		if(User.search_item==null) {
			
		//WHEN SEARCHING WAS GOING ON INITIALLY
		if(User.search_flag==1)
		{
			User.search_flag=0;
			User.records=User.initial_records;
			request.getSession(false).setAttribute("CurrentPage","1");
		}
		//if searching was not going on
		else {
			request.getSession(false).setAttribute("CurrentPage", Integer.toString(User.current_page));
		}
		}
		
		//IF THE ITEM TO BE SEARCHED IS NOT NULL
		else {
		
		Functionalities func_obj=new Functionalities();
		int no_of_searched_items=func_obj.calculate_searched_NoOfRecords(User.search_item);//CALCULATE NUMBER OF FILTERED RECORDS
		
		//IF SOME RECORD EXIST
		if(no_of_searched_items!=0)
		{
		User.search_flag=1;
		User.no_of_records=no_of_searched_items;
		User.set_No_of_Pages(User.no_of_records);
		
		//PREPARE QUERY
		String data_qry=null;
		if(User.Level.equals("Level 1"))
		{
		data_qry="SELECT * FROM order_details WHERE Order_ID LIKE '"+User.search_item+"%' limit 0,10";
		}
		else if(User.Level.equals("Level 2"))
		{
			data_qry="SELECT * FROM order_details WHERE order_amount>10000 and order_amount<=50000 and Order_ID LIKE '"+User.search_item+"%' limit 0,10";
		}
		else if(User.Level.equals("Level 3"))
		{
			data_qry="SELECT * FROM order_details WHERE order_amount>50000 and Order_ID LIKE '"+User.search_item+"%' limit 0,10";
		}
		List<UserEntity> list=Functionalities.get_Records_database(data_qry);//GET RECORDS
		
		User.records=list;
		request.getSession(false).setAttribute("CurrentPage", "1");
		String msg="1";
		obj.addProperty("msg",msg);
		
		out.println(obj.toString());
		
		}
		
		//IF NO SUCH RECORD EXIST
		else {
			User.search_flag=0;
			String msg="0";
			obj.addProperty("msg",msg);
			
			out.println(obj.toString());
			out.close();
		}
		}
	}
}
