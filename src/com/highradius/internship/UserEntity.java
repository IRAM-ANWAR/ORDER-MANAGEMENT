//THIS IS USER-ENTITY CLASS 
//THIS CLASS CONTAINS ATTRIBUTES AT ORDER LEVEL

package com.highradius.internship;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
public class UserEntity {
	private int Order_ID;
	private String Customer_Name;
	private int Customer_ID;
	private int Order_Amount;
	private String Approval_Status;
	private String Approved_By;
	private String Notes;
	private String Order_Date;
	public int getOrder_ID() {
	
		return Order_ID;
	}
	public void setOrder_ID(int order_ID) {
		Order_ID = order_ID;
	}
	public String getCustomer_Name() {
		return Customer_Name;
	}
	public void setCustomer_Name(String customer_Name) {
		Customer_Name = customer_Name;
	}
	public int getCustomer_ID() {
		return Customer_ID;
	}
	public void setCustomer_ID(int customer_ID) {
		Customer_ID = customer_ID;
	}
	public int getOrder_Amount() {
		return Order_Amount;
	}
	public void setOrder_Amount(int order_Amount) {
		Order_Amount = order_Amount;
	}
	public String getOrder_Date() {
		
		return Order_Date;
	}
	public void setOrder_Date(Date order_Date) {
		DateFormat date1=new SimpleDateFormat("dd/MM/yyyy");
		Order_Date=date1.format(order_Date);
	}
	public String getApproved_By() {
		return Approved_By;
	}
	public void setApproved_By(String approved_By) {
		Approved_By = approved_By;
		if(Approved_By==null)
		{
			Approved_By="";
		}
	}
	public String getApproval_Status() {
		return Approval_Status;
	}
	public void setApproval_Status(String approval_Status) {
		Approval_Status = approval_Status;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
		
		if(Notes==null)
		{
			Notes="";
		}
	}
	

}
