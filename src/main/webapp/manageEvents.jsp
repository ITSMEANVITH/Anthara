<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eventbooking.dao.EventDAO"%>
<%@ page import="com.eventbooking.model.Event"%>
<%@ page import="com.eventbooking.model.Admin"%>

<%
Admin admin = (Admin) session.getAttribute("admin");

if (admin == null) {
    response.sendRedirect("adminLogin.jsp");
    return;
}

EventDAO dao = new EventDAO();
ArrayList<Event> events = dao.getAllEvents();
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Manage Events</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{
    background:#f5f7fb;
    font-family:Arial;
}

.card{
    border:none;
    border-radius:18px;
}

img{
    object-fit:cover;
    border-radius:8px;
}

</style>

</head>

<body>

<div class="container mt-5">

<div class="card shadow">

<div class="card-body">

<h2>Manage Events</h2>

<hr>

<a href="addEvent.jsp"
class="btn btn-success mb-3">

+ Add Event

</a>

<table class="table table-bordered table-hover align-middle">

<thead class="table-dark">

<tr>

<th>ID</th>

<th>Poster</th>

<th>Name</th>

<th>Date</th>

<th>Time</th>

<th>Venue</th>

<th>Price</th>

<th>Seats</th>

<th>Edit</th>

<th>Delete</th>

</tr>

</thead>

<tbody>

<%
for(Event e : events){
%>

<tr>

<td><%=e.getEventId()%></td>

<td>

<%
if(e.getEventImage()!=null && !e.getEventImage().isEmpty()){
%>

<img
src="eventImages/<%=e.getEventImage()%>"
width="120"
height="70">

<%
}else{
%>

<span class="text-muted">No Image</span>

<%
}
%>

</td>

<td><%=e.getEventName()%></td>

<td><%=e.getEventDate()%></td>

<td><%=e.getEventTime()%></td>

<td><%=e.getVenue()%></td>

<td>Rs. <%=e.getTicketPrice()%></td>

<td><%=e.getAvailableSeats()%></td>

<td>

<a
href="editEvent.jsp?id=<%=e.getEventId()%>"
class="btn btn-warning btn-sm">

Edit

</a>

</td>

<td>

<a
href="DeleteEventServlet?id=<%=e.getEventId()%>"
class="btn btn-danger btn-sm"
onclick="return confirm('Are you sure you want to delete this event?');">

Delete

</a>

</td>

</tr>

<%
}
%>

</tbody>

</table>

<br>

<a
href="adminDashboard.jsp"
class="btn btn-primary">

← Back to Dashboard

</a>

</div>

</div>

</div>

</body>

</html>