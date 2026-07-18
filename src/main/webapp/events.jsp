<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eventbooking.dao.EventDAO"%>
<%@ page import="com.eventbooking.model.Event"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Anthara | Events</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

<style>

body{
    background:#f8f9fa;
}

.navbar-brand{
    font-size:28px;
    font-weight:bold;
}

.hero{
    background:linear-gradient(135deg,#0d6efd,#6610f2);
    color:white;
    padding:60px;
    border-radius:20px;
    margin-bottom:40px;
}

.event-card{
    border:none;
    border-radius:18px;
    transition:.3s;
}

.event-card:hover{
    transform:translateY(-8px);
    box-shadow:0 20px 35px rgba(0,0,0,.15);
}

.price{
    color:#0d6efd;
    font-size:26px;
    font-weight:bold;
}

</style>

</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

<div class="container">

<a class="navbar-brand" href="dashboard.jsp">

🎫 Anthara

</a>

<button
class="navbar-toggler"
type="button"
data-bs-toggle="collapse"
data-bs-target="#navbarNav">

<span class="navbar-toggler-icon"></span>

</button>

<div
class="collapse navbar-collapse"
id="navbarNav">

<ul class="navbar-nav ms-auto">

<li class="nav-item">
<a class="nav-link" href="dashboard.jsp">

Dashboard

</a>
</li>

<li class="nav-item">
<a class="nav-link active" href="events.jsp">

Events

</a>
</li>

<li class="nav-item">
<a class="nav-link" href="wishlist.jsp">

Wishlist

</a>
</li>

<li class="nav-item">
<a class="nav-link" href="myBookings.jsp">

My Bookings

</a>
</li>

<li class="nav-item">
<a class="nav-link" href="profile.jsp">

Profile

</a>
</li>

<li class="nav-item">
<a class="nav-link" href="logout.jsp">

Logout

</a>
</li>

</ul>

</div>

</div>

</nav>

<div class="container mt-4">

<div class="hero">

<h1 class="display-5 fw-bold">

Discover Amazing Events

</h1>

<p class="lead">

Book concerts, sports, festivals and much more with Anthara.

</p>

</div>

<form action="SearchServlet" method="get" class="row mb-4">

<div class="col-md-5">

<input
type="text"
name="keyword"
class="form-control"
placeholder="Search by Event or Venue">

</div>

<div class="col-md-4">

<select
name="category"
class="form-select">

<option value="">All Categories</option>
<option value="Music">Music</option>
<option value="Tech">Tech</option>
<option value="Sports">Sports</option>
<option value="Comedy">Comedy</option>
<option value="Food">Food</option>

</select>

</div>

<div class="col-md-3">

<button
class="btn btn-primary w-100">

Search

</button>

</div>

</form>

<div class="row">

<%

ArrayList<Event> list =
(ArrayList<Event>)request.getAttribute("events");

if(list==null){

    EventDAO dao = new EventDAO();

    list = dao.getAllEvents();

}

for(Event event : list){

%>

<div class="col-lg-4 col-md-6 mb-4">

<div class="card event-card h-100">

<div class="card-body">

<h3>

<%=event.getEventName()%>

</h3>

<hr>

<p>

🏷 <b>Category :</b>

<%=event.getCategory()%>

</p>

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

<p>

🎟 Available Seats :

<b>

<%=event.getAvailableSeats()%>

</b>

</p>

<div class="d-grid gap-2">

<a
href="WishlistServlet?action=add&eventId=<%=event.getEventId()%>"
class="btn btn-outline-danger">

❤️ Add to Wishlist

</a>

<form action="booking.jsp" method="get">

<input
type="hidden"
name="eventId"
value="<%=event.getEventId()%>">

<button
type="submit"
class="btn btn-primary w-100">

🎫 Book Ticket

</button>

</form>

<a
href="review.jsp?eventId=<%=event.getEventId()%>"
class="btn btn-warning">

⭐ Reviews

</a>

</div>

</div>

</div>

</div>

<%

}

%>

</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>