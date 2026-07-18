<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eventbooking.dao.WishlistDAO"%>
<%@ page import="com.eventbooking.model.Event"%>
<%@ page import="com.eventbooking.model.User"%>

<%
User user = (User)session.getAttribute("user");

if(user==null){

    response.sendRedirect("login.jsp");

    return;

}

WishlistDAO dao = new WishlistDAO();

ArrayList<Event> list = dao.getWishlist(user.getUserId());

%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<meta name="viewport"
content="width=device-width, initial-scale=1">

<title>My Wishlist | Anthara</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{

background:#f8f9fa;

}

.card{

border:none;

border-radius:18px;

box-shadow:0 10px 25px rgba(0,0,0,.08);

transition:.3s;

}

.card:hover{

transform:translateY(-5px);

}

.price{

font-size:24px;

font-weight:bold;

color:#0d6efd;

}

</style>

</head>

<body>

<div class="container mt-5">

<h2 class="mb-4">

 My Wishlist

</h2>

<%

if(list.isEmpty()){

%>

<div class="alert alert-info">

No Events Added To Wishlist.

</div>

<%

}

else{

for(Event event:list){

%>

<div class="card mb-4">

<div class="card-body">

<h3>

<%=event.getEventName()%>

</h3>

<hr>

<p>

📅 <b>Date :</b>

<%=event.getEventDate()%>

</p>

<p>

🕒 <b>Time :</b>

<%=event.getEventTime()%>

</p>

<p>

📍 <b>Venue :</b>

<%=event.getVenue()%>

</p>

<p class="price">

Rs. <%=event.getTicketPrice()%>

</p>

<div class="row">

<div class="col-md-6">

<a

href="booking.jsp?eventId=<%=event.getEventId()%>"

class="btn btn-primary w-100">

 Book Ticket

</a>

</div>

<div class="col-md-6">

<a

href="WishlistServlet?action=remove&eventId=<%=event.getEventId()%>"

class="btn btn-danger w-100">

 Remove

</a>

</div>

</div>

</div>

</div>

<%

}

}

%>

<a

href="events.jsp"

class="btn btn-secondary">

 Back To Events

</a>

</div>

</body>

</html>