<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%><%@ page import="com.eventbooking.model.Booking"%>

<%

Booking booking=(Booking)request.getAttribute("booking");

String status=(String)request.getAttribute("status");

String message=(String)request.getAttribute("message");

%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Verify Ticket | Anthara</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{

background:#f5f7fb;

}

.card{

margin-top:70px;

border:none;

border-radius:20px;

box-shadow:0 10px 30px rgba(0,0,0,.12);

}

.valid{

color:#198754;

font-weight:bold;

font-size:34px;

}

.invalid{

color:#dc3545;

font-weight:bold;

font-size:34px;

}

.info{

font-size:18px;

margin:12px 0;

}

</style>

</head>

<body>

<div class="container">

<div class="row justify-content-center">

<div class="col-md-7">

<div class="card">

<div class="card-body p-5 text-center">

<h2>

🎫 Ticket Verification

</h2>

<hr>

<%

if("VALID".equals(status)){

%>

<div class="valid">

✅ VALID TICKET

</div>

<%

}else{

%>

<div class="invalid">

❌ <%=message%>

</div>

<%

}

%>

<%

if(booking!=null){

%>

<hr>

<p class="info">

<b>Booking ID :</b>

<%=booking.getBookingId()%>

</p>

<p class="info">

<b>Tickets :</b>

<%=booking.getTickets()%>

</p>

<p class="info">

<b>Total Amount :</b>

₹ <%=booking.getTotalAmount()%>

</p>

<p class="info">

<b>Booking Date :</b>

<%=booking.getBookingDate()%>

</p>

<p class="info">

<b>Status :</b>

<%=booking.getStatus()%>

</p>

<%

}

%>

<hr>

<a

href="myBookings.jsp"

class="btn btn-primary">

⬅ Back to My Bookings

</a>

</div>

</div>

</div>

</div>

</div>

</body>

</html>