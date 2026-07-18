<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Admin Login</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{

background:#f5f7fb;

}

.card{

margin-top:120px;
border:none;
border-radius:20px;

}

</style>

</head>

<body>

<div class="container">

<div class="row justify-content-center">

<div class="col-md-5">

<div class="card shadow-lg">

<div class="card-body p-5">

<h2 class="text-center">

ANTHARA ADMIN

</h2>

<hr>

<%
if(request.getParameter("error")!=null){
%>

<div class="alert alert-danger">

Invalid Username or Password

</div>

<%
}
%>

<form action="AdminLoginServlet" method="post">

<div class="mb-3">

<label>

Username

</label>

<input
type="text"
name="username"
class="form-control"
required>

</div>

<div class="mb-3">

<label>

Password

</label>

<input
type="password"
name="password"
class="form-control"
required>

</div>

<button
class="btn btn-dark w-100">

Login

</button>

</form>

</div>

</div>

</div>

</div>

</div>

</body>

</html>