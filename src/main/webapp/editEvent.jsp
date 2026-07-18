<%@ page import="com.eventbooking.dao.EventDAO"%>
<%@ page import="com.eventbooking.model.Event"%>

<%
int eventId = Integer.parseInt(request.getParameter("id"));

EventDAO dao = new EventDAO();
Event event = dao.getEventById(eventId);

if(event == null){
    out.println("Event Not Found");
    return;
}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Edit Event</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container mt-5">

<div class="card shadow">

<div class="card-body">

<h2>Edit Event</h2>

<hr>

<form action="UpdateEventServlet" method="post">

<input
type="hidden"
name="eventId"
value="<%=event.getEventId()%>">

<div class="mb-3">

<label>Event Name</label>

<input
type="text"
name="eventName"
class="form-control"
value="<%=event.getEventName()%>"
required>

</div>

<div class="mb-3">

<label>Date</label>

<input
type="date"
name="eventDate"
class="form-control"
value="<%=event.getEventDate()%>"
required>

</div>

<div class="mb-3">

<label>Time</label>

<input
type="time"
name="eventTime"
class="form-control"
value="<%=event.getEventTime()%>"
required>

</div>

<div class="mb-3">

<label>Venue</label>

<input
type="text"
name="venue"
class="form-control"
value="<%=event.getVenue()%>"
required>

</div>

<div class="mb-3">

<label>Price</label>

<input
type="number"
step="0.01"
name="ticketPrice"
class="form-control"
value="<%=event.getTicketPrice()%>"
required>

</div>

<div class="mb-3">

<label>Seats</label>

<input
type="number"
name="availableSeats"
class="form-control"
value="<%=event.getAvailableSeats()%>"
required>

</div>

<button class="btn btn-success">
Update Event
</button>

<a href="manageEvents.jsp"
class="btn btn-secondary">
Cancel
</a>

</form>

</div>

</div>

</div>

</body>

</html>