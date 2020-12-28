//THIS IS A SERVLET THAT MANAGES EDIT OPERATION TO DATABASE

package com.highradius.internship;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/Edit_Order")
public class Edit_Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Edit_Order() {
        super();
    }
    //POST METHOD
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Functionalities func_obj=new Functionalities();
		UserEntity usen=new UserEntity();
		PrintWriter out=response.getWriter();
		JsonObject obj=new JsonObject();
		try {
			
			//GET PARAMETERS
		usen.setOrder_ID(Integer.parseInt(request.getParameter("oid")));
		usen.setOrder_Amount(Integer.parseInt(request.getParameter("oamt")));
	    usen.setNotes(request.getParameter("notes"));
	    if(usen.getOrder_Amount()<=10000)
	    {
	    	usen.setApproved_By(func_obj.get_Username(User.Level));
	    	usen.setApproval_Status("Approved");
	    }
	    else {
	    	usen.setApproved_By("    ");
	    	usen.setApproval_Status("Awaiting Approval");
	    }
	   
	    //PREPARE QUERY
	    String qry=null;
	    qry="UPDATE order_details SET Order_Amount=";
	    qry=qry+usen.getOrder_Amount()+",Approval_Status='";
	    qry=qry+usen.getApproval_Status()+"',Approved_By='";
	    qry=qry+usen.getApproved_By()+"',Notes='";
	    qry=qry+usen.getNotes()+"' WHERE Order_ID=";
	    qry=qry+usen.getOrder_ID()+";";
	    func_obj.insert_into_database(qry);//EXECUTE QUERY
	    
	    //SET LIMIT AND FETCH RECORDS FROM DATABASE
	    int total=10;
		int pageid=(User.current_page-1)*total;
		String data_qry=null;
		if(User.search_flag==0)
		{
	    data_qry=data_qry="SELECT * FROM order_details limit "+pageid+","+total;
		}
		else {
			data_qry="SELECT * FROM order_details WHERE Order_ID LIKE '"+User.search_item+"%' limit "+pageid+","+total;
		}
		List<UserEntity> list=Functionalities.get_Records_database(data_qry);
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
