<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Reset Password | Anthara</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{

    background:#f5f7fb;

}

.card{

    border:none;

    border-radius:15px;

    box-shadow:0 10px 25px rgba(0,0,0,.1);

}

</style>

</head>

<body>

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-md-5">

<div class="card">

<div class="card-body p-4">

<h2 class="text-center mb-4">

Reset Password

</h2>

<%
if(request.getParameter("error")!=null){
%>

<div class="alert alert-danger">

Passwords do not match.

</div>

<%
}
%>

<form action="ResetPasswordServlet" method="post">

<div class="mb-3">

<label class="form-label">

New Password

</label>

<input
type="password"
name="password"
class="form-control"
placeholder="Enter New Password"
required>

</div>

<div class="mb-3">

<label class="form-label">

Confirm Password

</label>

<input
type="password"
name="confirmPassword"
class="form-control"
placeholder="Confirm New Password"
required>

</div>

<button
type="submit"
class="btn btn-primary w-100">

Reset Password

</button>

</form>

<hr>

<div class="text-center">

<a href="login.jsp">

Back to Login

</a>

</div>

</div>

</div>

</div>

</div>

</div>

</body>

</html>