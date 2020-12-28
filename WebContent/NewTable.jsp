<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="com.highradius.internship.*"%>
<%@page import="javax.servlet.*" %>
<%
	User user = (User) session.getAttribute("currentUser");
	String lastpage=Integer.toString(User.no_Of_Pages);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Dashboard</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet"></link>
	
	<link rel="stylesheet" type="text/css" href="./css/OrderDashboard.css"></link>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	

</head>
<body>
	<!-- TABLE WITH PAGINATION -->
				<div id="table_pagination">
				
				<!-- TABLE -->
				<table class="tableboard" id="table">
					<tr class="first-tr">
							<th>&nbsp;</th>
							<th>Order Date</th>
							<th>Order ID</th>
							<th>Customer Name</th>
							<th>Customer_ID</th>
							<th>Order Amount</th>
							<th>Approval Status</th>
							<th>Approved By</th>
							<th>Notes</th>
							
						</tr>
						
					<!-- CALL LIST FROM USER CLASS AND DISPLAY RECORDS IN TABLE -->
					<%
					int i=0;
						List<UserEntity> rlist = User.records;
					if (rlist != null)
						for (UserEntity e : rlist) {
							
							i++;
					
					%>
					<tr height=35px class="data_rows" id="data-rows">
				
						<td>
						<label class="container">&nbsp;
						<input type="checkbox" id="select-row" class="chb" name="chk" style="outline:1px solid orange;">
						<span class="checkmark"></span>
						</label>
						</td>
						<%
							
							out.print("<td>" + e.getOrder_Date() + "</td>");
						%>
						<%
						    out.print("<td >" + Integer.toString(e.getOrder_ID()) + "</td>");
						%>
						<%
							out.print("<td>" + e.getCustomer_Name() + "</td>");
						%>
						<%
							out.print("<td>" + Integer.toString(e.getCustomer_ID()) + "</td>");
						%>
						<%
							out.print("<td>" + Integer.toString(e.getOrder_Amount()) + "</td>");
						%>
						<%
							out.print("<td>" + e.getApproval_Status() + "</td>");
						%>
						<%
							out.print("<td>" + e.getApproved_By() + "</td>");
						%>
						<%
							out.print("<td>" + e.getNotes() + "</td>");
						%>
					</tr>
					<%
						}
					
					//IF NUMBER OF RECORDS ARE LESS THAN 10 DISPLAY E
					int j=10-i;
					for(int k=0;k<j;k++)
					{
					%>
					<tr height=35px;>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
					<%} %>
				</table>
					<script>
					$('input[type="checkbox"]').click(function(e){
						this.checked=!this.checked;
					});
						
						<%if(User.Level.equals("Level 1")){%>
						
						
						function selectedRowToInput()
						{
							var rowIndex,table=document.getElementById("table");
							for(var i=0;i<table.rows.length;i++)
							{
								table.rows[i].onclick=function()
								{
									var a = document.getElementsByName('chk');
									var newvar=0;
									var flag=-1;
									var flag1=-2;
									var count;
									for(count=0; count<a.length; count++){
										if(a[count].checked===true){
											newvar = newvar+1;
											flag=count;
											
											break;
											
										}
									}
									flag1=this.rowIndex-1;
									
									
									if(newvar>=1 && flag!=flag1){return false;}
						
									rIndex=this.rowIndex;
									const checkbox=this.querySelector("input[type='checkbox']");
									if(checkbox)
									{
										checkbox.checked=!checkbox.checked;
									}
									else{return false;}
									if(this.style.background==="orange")
									{
										if(rIndex%2===0)
										{
											this.style.background="#EFF9FD";
										}
										else{
											this.style.background="white";
										}
										checkbox.checked=false;
										const button=document.getElementById("edit-button");
										button.disabled=true;
									}
									else{
									this.style.background="orange";
									checkbox.checked=true;
									const button=document.getElementById("edit-button");
									button.disabled=false;
									}
									
									document.getElementById("oid").value=this.cells[2].innerHTML;
									document.getElementById("oamt").value=this.cells[5].innerHTML;
									document.getElementById("notes").value=this.cells[8].innerHTML;
									
									
								};
							}
						}
							
						selectedRowToInput();
						<%}else{%>
						function selectedRowToApproveRejectInput()
						{
							var rowIndex,table=document.getElementById("table");
							for(var i=0;i<table.rows.length;i++)
							{
								table.rows[i].onclick=function()
								{
									var a = document.getElementsByName('chk');
									var newvar=0;
									var flag=-1;
									var flag1=-2;
									var count;
									for(count=0; count<a.length; count++){
										if(a[count].checked===true){
											newvar = newvar+1;
											flag=count;
											break;
										}
									}
									flag1=this.rowIndex-1;
									if(newvar>=1 && flag!=flag1){return false;}
						
									rIndex=this.rowIndex;
									const checkbox=this.querySelector("input[type='checkbox']");
									if(checkbox)
									{
										checkbox.checked=!checkbox.checked;
									}
									else{return false;}
									if(this.style.background==="orange")
									{
										if(rIndex%2===0)
										{
											this.style.background="#EFF9FD";
										}
										else{
											this.style.background="white";
										}
										checkbox.checked=false;
										const button1=document.getElementById("Approve-button");
										button1.disabled=true;
										const button2=document.getElementById("Reject-button");
										button2.disabled=true;
									}
									else{
									this.style.background="orange";
									checkbox.checked=true;
									
									if(this.cells[6].innerHTML==="Awaiting Approval")
									{
										const button1=document.getElementById("Approve-button");
										button1.disabled=false;
										const button2=document.getElementById("Reject-button");
										button2.disabled=false;
									}
									}
								};
							}
						}selectedRowToApproveRejectInput();
						<%}%>
	</script>
				<!-- TABLE ENDS HERE -->
				<%
				String currentPage=(String)session.getAttribute("CurrentPage");
				String records_this_Page;
				int thispage=Integer.parseInt(currentPage);
				User.current_page=thispage;
				int start=((thispage-1)*10)+1;
				int end;
				if((thispage*10)<=User.no_of_records){
					end=start+9;
				}
				else{
					end=(User.no_of_records);	
				}
				%>
				<div class="space"></div><!-- SPACE BETWEEN TABLE AND PAGE NUMBER -->
				
				<!-- PAGINATION AREA -->
				<div class="pagination">
					<section>
					<%if(thispage!=1)
					{%>
						<div>
								<button class="signIn" onclick="pagination('1')"> << </button>
						</div>
						<%} else{%>
						<div>
							<button class="signIn" disabled> << </button>
					</div>
					<% }%>
						
						<%if(thispage!=1)
					{%>
						<div>
							
								<button class="signIn" onclick="pagination('previouspage')"> < </button>
							
						</div>
						<%} 
					else{%>
						<div>
					
							<button class="signIn" disabled> < </button>
						
					</div>
					<% }%>
						<div class="pagex" style="border:none">
							<h5 style="text-align:center">Page</h5>
						</div>
						<div id="pageno" style="border:none">
							<h5 style="color:blue;text-align:center"><%out.print("  "+currentPage);%></h5>
						</div>
						<div class="pagex" style="border:none">
							<h5>&nbsp;&nbsp;of&nbsp;<%out.print("	  "+User.no_Of_Pages); %></h5>
						</div>
					<%if(thispage!=User.no_Of_Pages)
					{%>
						<div>
								<button class="signIn" onclick="pagination('nextpage')"> > </button>
						</div>
						<%} 
					else{%>
						<div>
						<form>
							<button class="signIn" disabled> > </button>
						</form>
					</div>
					<%}%>
							<%if(thispage!=User.no_Of_Pages)
					{%>
						<div>
								<button class="signIn" onclick="pagination('lastpage')"> >> </button>
						</div>
						<%} 
					else{%>
						<div>
							<button class="signIn" disabled> >> </button>
					</div>
					<% }%>
					</section>
					<next>
					<div><%out.print("<h4>Customer "+start+"-"+end+" of  "+User.no_of_records+"</h4>");%>
					</div>
					</next>
				</div>
				</div>
</body>
</html>