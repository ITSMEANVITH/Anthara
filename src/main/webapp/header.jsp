<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Anthara</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

<style>

body{
    background:#f5f5f5;
}

.navbar-brand{
    font-weight:bold;
    font-size:28px;
}

.card{
    border:none;
    border-radius:15px;
}

.card:hover{
    transform:translateY(-5px);
    transition:.3s;
}

</style>

</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

<div class="container">

<a class="navbar-brand" href="dashboard.jsp">

🎫 Anthara

</a>

<button class="navbar-toggler"
type="button"
data-bs-toggle="collapse"
data-bs-target="#menu">

<span class="navbar-toggler-icon"></span>

</button>

<div class="collapse navbar-collapse"
id="menu">

<ul class="navbar-nav ms-auto">

<li class="nav-item">

<a class="nav-link"
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
href="logout.jsp">

Logout

</a>

</li>

</ul>

</div>

</div>

</nav>

<div class="container mt-4">