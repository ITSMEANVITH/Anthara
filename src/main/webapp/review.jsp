<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eventbooking.dao.ReviewDAO"%>
<%@ page import="com.eventbooking.model.Review"%>
<%@ page import="com.eventbooking.model.User"%>

<%
User user=(User)session.getAttribute("user");

if(user==null){

    response.sendRedirect("login.jsp");

    return;

}

int eventId=Integer.parseInt(request.getParameter("eventId"));

ReviewDAO dao=new ReviewDAO();

ArrayList<Review> reviews=dao.getReviewsByEvent(eventId);

double avg=dao.getAverageRating(eventId);

int total=dao.getTotalReviews(eventId);

%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<meta name="viewport"
content="width=device-width, initial-scale=1">

<title>Event Reviews | Anthara</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{

background:#f5f7fb;

}

.card{

border:none;

border-radius:18px;

box-shadow:0 10px 20px rgba(0,0,0,.08);

margin-bottom:20px;

}

.star{

color:#ffc107;

font-size:22px;

}

</style>

</head>

<body>

<div class="container mt-5">

<h2>

⭐ Event Reviews

</h2>

<hr>

<h4>

Average Rating :

<span class="star">

★★★★★

</span>

<%=String.format("%.1f",avg)%>

(<%=total%> Reviews)

</h4>

<br>

<div class="card">

<div class="card-body">

<h4>

Write Review

</h4>

<form action="ReviewServlet" method="post">

<input
type="hidden"
name="eventId"
value="<%=eventId%>">

<div class="mb-3">

<label>

Rating

</label>

<select
name="rating"
class="form-select"
required>

<option value="5">⭐⭐⭐⭐⭐</option>

<option value="4">⭐⭐⭐⭐</option>

<option value="3">⭐⭐⭐</option>

<option value="2">⭐⭐</option>

<option value="1">⭐</option>

</select>

</div>

<div class="mb-3">

<label>

Review

</label>

<textarea

name="review"

class="form-control"

rows="4"

required>

</textarea>

</div>

<button
class="btn btn-primary">

Submit Review

</button>

</form>

</div>

</div>

<h4 class="mt-4">

All Reviews

</h4>

<%

if(reviews.isEmpty()){

%>

<div class="alert alert-info">

No Reviews Yet.

</div>

<%

}

for(Review review:reviews){

%>

<div class="card">

<div class="card-body">

<h5>

Rating :

<%=review.getRating()%>/5

</h5>

<p>

<%=review.getReview()%>

</p>

<small class="text-muted">

<%=review.getReviewDate()%>

</small>

</div>

</div>

<%

}

%>

<a

href="events.jsp"

class="btn btn-secondary">

⬅ Back To Events

</a>

</div>

</body>

</html>