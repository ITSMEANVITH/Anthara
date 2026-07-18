<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Event Ticket Booking System</title>

<style>

body{
    margin:0;
    font-family:Arial;
    background:#f5f5f5;
}

header{
    background:#1565C0;
    color:white;
    padding:20px;
    text-align:center;
}

.container{
    width:80%;
    margin:auto;
    margin-top:80px;
    text-align:center;
}

a{
    text-decoration:none;
}

button{
    padding:15px 35px;
    margin:20px;
    border:none;
    border-radius:5px;
    font-size:18px;
    cursor:pointer;
}

.login{
    background:#2196F3;
    color:white;
}

.register{
    background:#4CAF50;
    color:white;
}

footer{
    position:fixed;
    bottom:0;
    width:100%;
    background:#1565C0;
    color:white;
    text-align:center;
    padding:10px;
}

</style>

</head>

<body>

<header>

<h1>Event Ticket Booking System</h1>

</header>

<div class="container">

<h2>Welcome</h2>

<p>Book your favourite events quickly and securely.</p>

<a href="login.jsp">
<button class="login">Login</button>
</a>

<a href="register.jsp">
<button class="register">Register</button>
</a>

</div>

<footer>

© 2026 Event Ticket Booking System

</footer>

</body>
</html>