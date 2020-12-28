<!-- THIS IS ORDER DASHBOARD PAGE -->

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
	<link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	

</head>
<body>
	<div class="mainclass">
		<div class="header">
			<div class="hrclogo"></div>
			<div class="abcimage"></div>
		</div>
	
		<!-- <div class="tableborder">-->
			<div class="tablelayout">
				<div class="AddEditSearch">
					<div class="addedit">

						<%if(User.Level.equals("Level 1")){%>
						<button type="button" name="Add" value="Add" class="Add" id="Add" onclick="myFunc()">ADD</button>
						<button type="button" name="Edit" value="Edit" class="Edit" id="edit-button" onclick="myFunc_edit()" disabled>EDIT</button>
							<%}
							else{
							%>
									<button type="button" name="my-button" value="Approve" class="Add" id="Approve-button" onclick="approve_reject_ajax('1')" disabled>APPROVE</button>
						 			<button type="button" name="my-button" value="Reject" class="Edit" id="Reject-button" onclick="approve_reject_ajax('2')" disabled>REJECT</button>
						 <%} %>	
						 </div>
						<div id="alertbox"></div>
						<div class="searchhere">
						
							<table class="searchbox" cellspacing="0">
								<tr>
									<form id="searchform">
									
									<td style="background-color:#EFF9FD;"><div class="search-icon"><span class="material-icons">search</span></div>
									</td>
									<td><input type="search" placeholder="Search Order_ID" class="search" id="search" name="search" onkeyup="search_ajaxcall()">
									</td>
									
									</form>
								</tr>
							</table>
						</div>
						
					<!-- <table class="colname">-->
						
					</div>
					<!--</table>-->
				
				<div id="table_pagination">
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
					<%
					int i=0;
						List<UserEntity> rlist = User.records;
					if (rlist != null)
						for (UserEntity e : rlist) {
							
							i++;
					
					if(User.Level.equals("Level 1")){%>
					<tr height=35px class="data_rows" id="data-rows">
					
					<%} else{%>
					<tr height=35px class="data_rows" id="data-rows">
					<%} %>
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
					
						<%}
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
				<div class="space"></div>
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
				</div>
				
				
			</div>
		<!--</div>-->
	</div>
	
	<!-- POPUP FOR ADD AND EDIT -->
	<% if(User.Level.equals("Level 1")){%> 
	<div class="popup" style="display:none">
							<div class="popup-content">
								<div class="close"><button class="close_add_button" onclick="close_add()">&times;</button></div>
								<h3 style="color:grey;font-size:24px;">ADD ORDER</h3><span id="error"></span><br><br>
								<form id="add_form" style="font-size: 15px; color:grey;text-align: left;padding-left:100px">
									<label>Order ID</label><input type="text" name="oid_add" id="oid_add"><br><br>              <!-- oid_add means OrderId of ADD FORM -->
									<label>Order Date</label><input type="date" name="od_add" id="od_add"><br><br>              <!-- od_add means OrderDate of ADD FORM -->
									<label>Customer Name</label><input type="text" name="cname_add" id="cname_add"><br><br>     <!-- cname_add means CustomerName of ADD FORM -->
									<label>Customer Number</label><input type="text" name="cnum_add" id="cnum_add"><br><br>     <!-- cnum_add means CustomerNumber of ADD FORM -->
									<label>Order Amount</label><input type="text" name="oamt_add" id="oamt_add"><br><br>        <!-- oamt_add means OrderAmount of ADD FORM -->
									<label>Notes</label><input type="text" name="notes_add" id="notes_add"><br><br>
									<br><br>
									<input type="button" onclick="add_order_ajax()" value="ADD">
								</form>
							</div>
						</div>
						 <div class="popup-edit" style="display: none">
						    <div class="popup-content-edit">
								<div class="close-edit"><button class="close-edit-button" onclick="close_edit()">&times;</button></div>
								<h3 style="color:grey;font-size:24px;">EDIT ORDER</h3><span id="error_edit"></span><br><br><br><br><br>
								<form id="edit_form" style="font-size: 15px; color:grey; text-align:left;padding-left: 100px">
									<label>Order ID</label><input type="text" name="oid" id="oid" readonly><br><br><br>           <!-- oid means OrderId -->
									<label>Order Amount</label><input type="text" name="oamt" id="oamt" onkeyup="set_ApprovalBy()"><br><br><br> <!-- oamt means OrderAmount-->
									<label>Notes</label><input type="text" name="notes" id="notes"><br><br><br>
									<label>Approval_By</label><input type="text" name="aby" id="aby" readonly><br><br><br>    <!-- aby means Approval_By --> 
									<input type="button" onclick="edit_order_ajax()" value="SUBMIT">
								</form>
							</div>
						</div>
						
	<!-- POPUP ENDS HERE -->
	
						<table id="to_get_username" style="display:none;">
						<tr id="user">
						<%
							out.print("<td>" + Functionalities.get_Username("Level 1") + "</td>");
							
						%>
						<%
							out.print("<td>" + Functionalities.get_Username("Level 2") + "</td>");
						%>
						<%
							out.print("<td>" + Functionalities.get_Username("Level 3") + "</td>");
						%>
						</tr>
						</table>

	<!-- JAVASCRIPT+JQUERY -->
<%} %>
	 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
       <script type="text/javascript">
       
       var msg=null;
      	
      	/*PAGINATION CALL*/
      	
      	function pagination(page)
      	{
    		var pageno=page;
      		$.ajax({
      			
      			method:'POST',
      			url:'ViewServlet',
      			data:{"pageno":pageno},
      			dataType:'JSON',
      			
      			success:function(data)
      			{
      				$("#table_pagination").load("NewTable.jsp");
      				const button=document.getElementById("edit-button");
					button.disabled=true;
					
      			}
      		})
      	}
     
      	
     	
      	/*SEARCH-SERVLET CALL AND SEARCH FIELD VALIDATION----Validation is done to check the input value is an integer*/
     	function validate_searchfield()
     	{
     		var field_search=document.getElementById("search").value;
     		if(field_search=="")
     			return true;
     		if(!(parseInt(field_search))){
     			$("#alertbox").text("SEARCH FIELD MUST BE AN INTEGER!").show().fadeOut(5000);
     			return false;
     		}
     		return true;
     		
     	}
     
     	function search_ajaxcall()
       	{	
     		if(validate_searchfield()){
       		$.ajax({
       			type:'POST',
       			url:'SearchServlet',
       			data:{"search":$("#search").val()},
       			dataType:'JSON',
       			
       			success:function(data)
       			{
       				var msg=data.msg
       				if(msg==1){
       				 $("#table_pagination").load("NewTable.jsp");
       				 
       				}
       				else if(msg==0)
       				{
       					$("#table_pagination").load("NewTable.jsp");
       					$("#alertbox").text("NO MATCHING RECORDS FOUND!").show().fadeOut(5000);
       					
       				}
       				const button=document.getElementById("edit-button");
					button.disabled=true;
					const button1=document.getElementById("Approve-button");
					button1.disabled=true;
					const button2=document.getElementById("Reject-button");
					button2.disabled=true;
       			}
		
       	})
     		}
       	
       	}

    	/*ADD-ORDER-SERVLET CALL AND ADD-ORDER-FORM VALIDATION*/
     	function validate_addform(){
     		
     		var field_order_id=document.getElementById("oid_add").value;
     		var field_order_date=document.getElementById("od_add").value;
    		var field_cname=document.getElementById("cname_add").value;
    		var field_cnum=document.getElementById("cnum_add").value;
    		var field_oamt=document.getElementById("oamt_add").value;
     		if(field_order_id=="")
     		{
     			error.textContent="ORDER ID CANNOT BE BLANK";
     			error.style.color="red";
     			console.log(typeof field_order_id);
     			return false;
     		}
     		else if(!(parseInt(field_order_id))){
     			error.textContent="ORDER ID MUST BE A NUMBER";
     			error.style.color="red";
     			return false;
     		}
     		else{
     			error.textContent="";
     		}
     		if(field_order_date=="")
     		{
     			error.textContent="ORDER DATE CANNOT BE BLANK";
     			error.style.color="red";
     			return false;
     		}
     		else{
     			error.textContent="";
     		}
     		if(field_cname=="")
     		{
     			error.textContent="CUSTOMER NAME CANNOT BE BLANK";
     			error.style.color="red";
     			return false;
     		}
     		else if(parseInt(field_cname)){
     			error.textContent="CUSTOMER NAME MUST BE A STRING";
     			error.style.color="red";
     			return false;
     		}
     		else{
     			error.textContent="";
     		}
     		if(field_cnum=="")
     		{
     			error.textContent="CUSTOMER NUMBER CANNOT BE BLANK";
     			error.style.color="red";
     			return false;
     		}
     		else if(!(parseInt(field_cnum))){
     			error.textContent="CUSTOMER NUMBER MUST BE A NUMBER";
     			error.style.color="red";
     			return false;
     		}
     		else{
     			error.textContent="";
     		}
     		if(field_oamt=="")
     		{
     			error.textContent="ORDER AMOUNT CANNOT BE BLANK";
     			error.style.color="red";
     			return false;
     		}
     		else if(!(parseInt(field_oamt))){
     			error.textContent="ORDER AMOUNT MUST BE A NUMBER";
     			error.style.color="red";
     			return false;
     		}
     		else{
     			error.textContent="";
     		}
    		
     		return true;
     		
     	}
    	function add_order_ajax()
       	{
    		if(validate_addform())
    		{
     		var data=$("#add_form").serialize();
       		$.ajax({
       			type:'POST',
       			url:'Add_Order',
       			data:data,
       			dataType:'JSON',
       			
       			success:function(data)
       			{
       				var msg=data.msg
       				if(msg==1){
       				$("#table_pagination").load("NewTable.jsp");
       				}
       				else if(msg==0){
       					$("#table_pagination").load("NewTable.jsp");
       					$("#alertbox").text("DUPLICATE ORDER ID!").show().fadeOut(5000);
       				}
       				const button=document.getElementById("edit-button");
					button.disabled=true;
       			}
       	})
       	document.querySelector('.popup').style.display="none";
       		$("#add_form input[type=text]").val("");
    		}
       	}
    	
    	/*EDIT-ORDER-SERVLET CALL AND EDIT-ORDER-FORM VALIDATION*/
			function validate_editform(){
    		var field_oamt=document.getElementById("oamt").value;
     		if(field_oamt=="")
     		{
     			error_edit.textContent="ORDER AMOUNT CANNOT BE BLANK";
     			error_edit.style.color="red";
     			console.log(typeof field_order_id);
     			return false;
     		}
     		else if(!(parseInt(field_oamt))){
     			error_edit.textContent="ORDER AMOUNT MUST BE A NUMBER";
     			error_edit.style.color="red";
     			return false;
     		}
     		else{
     			error_edit.textContent="";
     		}
     		return true;
     		
     	}
    	function edit_order_ajax()
       	{
    		if(validate_editform()){
     		var data=$("#edit_form").serialize();
       		$.ajax({
       			type:'POST',
       			url:'Edit_Order',
       			data:data,
       			dataType:'JSON',
       			
       			success:function(data)
       			{
       				$("#table_pagination").load("NewTable.jsp");
       				const button=document.getElementById("edit-button");
					button.disabled=true;
       			}
       	})
       	document.querySelector('.popup-edit').style.display="none";
       	$("#add_form input").text()="";
    	}
       	}
    	
    	/*APPROVE-REJECT-ORDER-SERVLET CALL*/
    	function approve_reject_ajax(flag)
       	{
    		var row=$("#table input:checked").parents("tr");
    		
    		var order_id=row.find("td:eq(2)").text();
    		if(flag==1)
    		{
    			var button_value="Approve";
    		}
    		else if(flag==2)
    		{
    			var button_value="Reject";
    		}
       		$.ajax({
       			type:'POST',
       			url:'Approve_Reject_Servlet',
       			data:{"approve_oid":order_id,"my-button":button_value},
       			dataType:'JSON',
       			
       			success:function(data)
       			{
       				$("#table_pagination").load("NewTable.jsp");
       				const button1=document.getElementById("Approve-button");
					button1.disabled=true;
					const button2=document.getElementById("Reject-button");
					button2.disabled=true;
       			}
       	})
       	}
    	/*CALLED TO OPEN ADD DIALOG BOX*/
		function myFunc(){
				document.querySelector('.popup').style.display="flex";
		};
		
		/*CALLED TO CLOSE ADD DIALOG BOX*/
		function close_add(){
				document.querySelector('.popup').style.display="none";
				$("#add_form input[type=text]").val("");
		};
		
		/*CALLED TO OPEN EDIT DIALOG BOX*/
		function myFunc_edit(){
				document.querySelector('.popup-edit').style.display="flex";
				set_ApprovalBy();
		};
		
		/*CALLED TO CLOSE EDIT DIALOG BOX*/
		function close_edit(){
				document.querySelector('.popup-edit').style.display="none";
				
		};
		
			/*SET APPROVAL BY FIELD IN EDIT ORDER FORM*/
			function set_ApprovalBy(){
			var order_amount=document.getElementById("oamt").value;
			var table=document.getElementById("to_get_username");
			if(order_amount>50000)
			{
				var name=$('#user td:eq(2)').text();
			}
			if(order_amount<=10000)
			{
				var name=$('#user td:eq(0)').text();
			}
			if(order_amount>10000 && order_amount<=50000)
			{
				var name=$('#user td:eq(1)').text();
			}
			document.getElementById("aby").value=name;
		};
		
						
					
		</script>
		
</body>
</html>