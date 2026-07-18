<%@ page import="com.eventbooking.model.User"%>

<%
User user=(User)session.getAttribute("user");

if(user==null){

    response.sendRedirect("login.jsp");
    return;

}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Anthara Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

<style>

body{
    background:#f5f7fb;
}

.hero{

    background:linear-gradient(135deg,#0d6efd,#6610f2);
    color:white;
    padding:50px;
    border-radius:20px;

}

.card{

    border:none;
    border-radius:18px;
    transition:.3s;

}

.card:hover{

    transform:translateY(-6px);

}

.stat{

    font-size:40px;
    color:#0d6efd;

}

</style>

</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

<div class="container">

<a class="navbar-brand fw-bold"
href="dashboard.jsp">

Anthara

</a>

<ul class="navbar-nav ms-auto">

<li class="nav-item">

<a class="nav-link active"
href="dashboard.jsp">

Dashboard

</a>

</li>

<li class="nav-item">

<a class="nav-link"
href="events.jsp">

Events

</a>

</li>

<li class="nav-item">

<a class="nav-link"
href="myBookings.jsp">

Bookings

</a>

</li>

<li class="nav-item">

<a class="nav-link"
href="LogoutServlet">

Logout

</a>

</li>

<li class="nav-item">
    <a class="nav-link" href="profile.jsp">
        Profile
    </a>
</li>

</ul>

</div>

</nav>

<div class="container mt-4">

<div class="hero">

<h1>

Welcome,

<%=user.getFullName()%>

👋

</h1>

<p>

Book concerts, festivals, sports and much more.

</p>

<a href="events.jsp"
class="btn btn-light btn-lg">

Browse Events

</a>

</div>

<div class="row mt-5">

<div class="col-md-4">

<div class="card shadow p-4 text-center">

<div class="stat">

<i class="bi bi-calendar-event"></i>

</div>

<h4>

Events

</h4>

<p>

Explore upcoming events.

</p>

<a href="events.jsp"
class="btn btn-primary">

View Events

</a>

</div>

</div>

<div class="col-md-4">

<div class="card shadow p-4 text-center">

<div class="stat">

<i class="bi bi-ticket-perforated"></i>

</div>

<h4>

Bookings

</h4>

<p>

View your bookings.

</p>

<a href="myBookings.jsp"
class="btn btn-success">

My Bookings

</a>

</div>

</div>


<div class="col-md-4">

<div class="card shadow p-4 text-center">

<div class="stat">

<i class="bi bi-credit-card"></i>

</div>

<h4>

Payments

</h4>

<p>

Secure online payments.

</p>

<a href="events.jsp"
class="btn btn-warning">

Book Ticket

</a>

</div>

</div>

</div>

<hr class="my-5">

<h2 class="mb-4">

Featured Categories

</h2>

<div class="row">

<div class="col-md-3">

<div class="card shadow text-center p-4">

🎵

<h5>Music</h5>

</div>

</div>

<div class="col-md-3">

<div class="card shadow text-center p-4">

🏏

<h5>Sports</h5>

</div>

</div>

<div class="col-md-3">

<div class="card shadow text-center p-4">

😂

<h5>Comedy</h5>

</div>

</div>

<div class="col-md-3">

<div class="card shadow text-center p-4">

🍔

<h5>Food</h5>

</div>

</div>

</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>