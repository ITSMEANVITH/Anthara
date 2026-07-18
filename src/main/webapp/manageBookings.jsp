<%@ page import="java.sql.ResultSet"%>
<%@ page import="com.eventbooking.dao.BookingDAO"%>
<%@ page import="com.eventbooking.model.Admin"%>

<%
Admin admin=(Admin)session.getAttribute("admin");

if(admin==null){

response.sendRedirect("adminLogin.jsp");
return;

}

BookingDAO dao=new BookingDAO();

ResultSet rs=dao.getBookingDetails();
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Manage Bookings</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-5">

<div class="card shadow">

<div class="card-body">

<h2>Manage Bookings</h2>

<hr>

<table class="table table-striped table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>

<th>Customer</th>

<th>Event</th>

<th>Tickets</th>

<th>Amount</th>

<th>Payment</th>

<th>Status</th>

<th>Date</th>

</tr>

</thead>

<tbody>

<%

while(rs.next()){

%>

<tr>

<td><%=rs.getInt("booking_id")%></td>

<td><%=rs.getString("full_name")%></td>

<td><%=rs.getString("event_name")%></td>

<td><%=rs.getInt("tickets")%></td>

<td>Rs. <%=rs.getDouble("total_amount")%></td>

<td><%=rs.getString("payment_method")%></td>

<td>

<%

String status=rs.getString("payment_status");

if(status.equalsIgnoreCase("SUCCESS")){

%>

<span class="badge bg-success">

SUCCESS

</span>

<%

}else{

%>

<span class="badge bg-warning text-dark">

PENDING

</span>

<%

}

%>

</td>

<td><%=rs.getString("booking_date")%></td>

</tr>

<%

}

%>

</tbody>

</table>

<a href="adminDashboard.jsp"
class="btn btn-primary">

Back

</a>

</div>

</div>

</div>

</body>

</html>