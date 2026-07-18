<%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eventbooking.dao.SeatDAO"%>
<%@ page import="com.eventbooking.model.Seat"%>
<%@ page import="com.eventbooking.util.DBConnection"%>

<%
int eventId=Integer.parseInt(request.getParameter("eventId"));
Connection con=DBConnection.getConnection();
PreparedStatement ps=con.prepareStatement("SELECT * FROM events WHERE event_id=?");
ps.setInt(1,eventId);
ResultSet rs=ps.executeQuery();

String eventName="",eventDate="",venue="";
double price=0;
int seats=0;

ArrayList<Seat> seatList=new SeatDAO().getSeatsByEvent(eventId);

if(rs.next()){
eventName=rs.getString("event_name");
eventDate=rs.getString("event_date");
venue=rs.getString("venue");
price=rs.getDouble("ticket_price");
seats=rs.getInt("available_seats");
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Anthara Booking</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h2><%=eventName%></h2>
<p><b>Date:</b> <%=eventDate%></p>
<p><b>Venue:</b> <%=venue%></p>
<p><b>Price:</b> Rs. <%=price%></p>

<form action="BookingServlet" method="post">
<input type="hidden" name="eventId" value="<%=eventId%>">
<div class="alert alert-warning">
Starter template. Replace with full interactive seat selection together with matching backend changes.
</div>
</form>
</body>
</html>
