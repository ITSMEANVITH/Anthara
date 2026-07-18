<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eventbooking.dao.BookingDAO"%>
<%@ page import="com.eventbooking.dao.EventDAO"%>
<%@ page import="com.eventbooking.model.Booking"%>
<%@ page import="com.eventbooking.model.Event"%>
<%@ page import="com.eventbooking.model.User"%>

<%
User user = (User) session.getAttribute("user");

if(user == null){
    response.sendRedirect("login.jsp");
    return;
}

BookingDAO bookingDAO = new BookingDAO();
EventDAO eventDAO = new EventDAO();

ArrayList<Booking> bookings =
        bookingDAO.getBookingsByUser(user.getUserId());
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<meta name="viewport"
content="width=device-width, initial-scale=1">

<title>My Bookings</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>

body{

background:#eef3fb;

font-family:Arial;

}

h1{

font-weight:bold;

margin-top:30px;

margin-bottom:40px;

color:#0d6efd;

}

.booking-card{

border:none;

border-radius:20px;

overflow:hidden;

transition:.3s;

box-shadow:0 10px 25px rgba(0,0,0,.10);

margin-bottom:30px;

}

.booking-card:hover{

transform:translateY(-8px);

box-shadow:0 20px 35px rgba(0,0,0,.20);

}

.card-header{

background:#0d6efd;

color:white;

font-size:22px;

font-weight:bold;

padding:18px;

}

.icon{

width:30px;

color:#0d6efd;

}

.info{

font-size:17px;

margin-bottom:12px;

}

.price{

font-size:28px;

font-weight:bold;

color:#198754;

}

.btn-custom{

border-radius:30px;

padding:10px 18px;

margin-right:10px;

margin-top:10px;

}

.badge-paid{

background:#198754;

padding:10px 18px;

font-size:14px;

border-radius:30px;

}

.empty{

margin-top:120px;

text-align:center;

color:gray;

}

</style>

</head>

<body>

<div class="container">

<h1 class="text-center">

<i class="fa-solid fa-ticket"></i>

My Bookings

</h1>

<%

if(bookings.isEmpty()){

%>

<div class="empty">

<h2>No bookings yet 😔</h2>

<p>Book an event to see it here.</p>

</div>

<%

}

for(Booking booking : bookings){

Event event =
eventDAO.getEventById(booking.getEventId());

%>

<div class="card booking-card">

<div class="card-header">

<%=event.getEventName()%>

</div>

<div class="card-body">

<p class="info">

<i class="fa-solid fa-location-dot icon"></i>

<b>Venue :</b>

<%=event.getVenue()%>

</p>

<p class="info">

<i class="fa-solid fa-calendar-days icon"></i>

<b>Date :</b>

<%=event.getEventDate()%>

</p>

<p class="info">

<i class="fa-solid fa-users icon"></i>

<b>Tickets :</b>

<%=booking.getTickets()%>

</p>

<p class="info">

<i class="fa-solid fa-receipt icon"></i>

<b>Booking ID :</b>

<%=booking.getBookingId()%>

</p>

<p class="info">

<i class="fa-solid fa-clock icon"></i>

<b>Booked On :</b>

<%=booking.getBookingDate()%>

</p>

<div class="price">

₹ <%=booking.getTotalAmount()%>

</div>

<br>

<%

String status = booking.getStatus();

if(status==null){

    status="CONFIRMED";

}

if(status.equalsIgnoreCase("CONFIRMED")){

%>

<span class="badge bg-success p-2">

✅ CONFIRMED

</span>

<%

}else{

%>

<span class="badge bg-danger p-2">

❌ CANCELLED

</span>

<%

}

%>

<br><br>
<a href="DownloadTicketServlet?bookingId=<%=booking.getBookingId()%>"
class="btn btn-success btn-custom">

<i class="fa-solid fa-download"></i>

Download Ticket

</a>

<a href="VerifyTicketServlet?bookingId=<%=booking.getBookingId()%>"
class="btn btn-primary btn-custom">

<i class="fa-solid fa-circle-check"></i>

Verify Ticket

</a>

<%

if(!"CANCELLED".equalsIgnoreCase(status)){

%>

<a
href="CancelBookingServlet?bookingId=<%=booking.getBookingId()%>"
class="btn btn-danger btn-custom"
onclick="return confirm('Are you sure you want to cancel this booking?');">

<i class="fa-solid fa-ban"></i>

Cancel Booking

</a>

<%

}

%>

<a href="dashboard.jsp"
class="btn btn-dark btn-custom">

<i class="fa-solid fa-house"></i>

Dashboard

</a>

</div>

</div>

<%

}

%>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>