<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Anthara | Login</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{

    font-family:Arial,sans-serif;

    background:#f2f2f2;

}

.container{

    width:400px;

    margin:80px auto;

    background:white;

    padding:30px;

    border-radius:10px;

    box-shadow:0 0 12px rgba(0,0,0,.2);

}

h2{

    text-align:center;

    margin-bottom:25px;

}

input{

    width:100%;

    padding:12px;

    margin:10px 0;

    border:1px solid #ccc;

    border-radius:5px;

    box-sizing:border-box;

}

button{

    width:100%;

    padding:12px;

    background:#1565C0;

    color:white;

    border:none;

    border-radius:5px;

    cursor:pointer;

    font-size:16px;

}

button:hover{

    background:#0D47A1;

}

a{

    text-decoration:none;

}

</style>

</head>

<body>

<div class="container">

<h2>User Login</h2>

<%
if("success".equals(request.getParameter("reset"))){
%>

<div class="alert alert-success">

Password Reset Successfully.
Please Login.

</div>

<%
}
%>

<%
if(request.getParameter("error")!=null){
%>

<div class="alert alert-danger">

Invalid Email or Password.

</div>

<%
}
%>

<form action="LoginServlet" method="post">

<input
type="email"
name="email"
placeholder="Enter Email"
required>

<input
type="password"
name="password"
placeholder="Enter Password"
required>

<button type="submit">

Login

</button>

<div class="text-center mt-3">

<a href="forgotPassword.jsp">

Forgot Password?

</a>

</div>

</form>

<hr>

<div class="text-center">

New User?

<a href="register.jsp">

Register Here

</a>

</div>

</div>

</body>

</html>