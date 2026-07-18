<%@ page import="com.eventbooking.dao.BookingDAO"%>
<%@ page import="com.eventbooking.model.Booking"%>

<%
int bookingId = Integer.parseInt(request.getParameter("bookingId"));

BookingDAO dao = new BookingDAO();

Booking booking = dao.getBookingById(bookingId);
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Payment Success</title>

<style>

body{

font-family:Arial;
background:#f4f4f4;

}

.box{

width:550px;
margin:70px auto;
background:white;
padding:40px;
text-align:center;
border-radius:12px;
box-shadow:0px 0px 10px lightgray;

}

h1{

color:green;

}

table{

width:100%;
margin-top:20px;
border-collapse:collapse;

}

td{

padding:10px;
border-bottom:1px solid #ddd;
text-align:left;

}

.btn{

display:inline-block;
margin-top:20px;
padding:12px 20px;
background:#007bff;
color:white;
text-decoration:none;
border-radius:5px;

}

.btn:hover{

background:#0056b3;

}

</style>

</head>

<body>

<div class="box">

<h1>Payment Successful ✅</h1>

<h3>Your ticket has been booked successfully.</h3>

<table>

<tr>
<td><b>Booking ID</b></td>
<td><%=booking.getBookingId()%></td>
</tr>

<tr>
<td><b>Event ID</b></td>
<td><%=booking.getEventId()%></td>
</tr>

<tr>
<td><b>Tickets</b></td>
<td><%=booking.getTickets()%></td>
</tr>

<tr>
<td><b>Amount</b></td>
<td>Rs. <%=booking.getTotalAmount()%></td>
</tr>

<tr>
<td><b>Booking Date</b></td>
<td><%=booking.getBookingDate()%></td>
</tr>

</table>

<a class="btn"
href="DownloadTicketServlet?bookingId=<%=booking.getBookingId()%>">

Download Ticket

</a>

<br><br>

<a class="btn"
href="mybookings.jsp">

View My Bookings

</a>

</div>

</body>

</html>