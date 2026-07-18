<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Verify OTP | Anthara</title>

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

Verify OTP

</h2>

<%
if(request.getParameter("error")!=null){
%>

<div class="alert alert-danger">

Invalid OTP. Please try again.

</div>

<%
}
%>

<form action="VerifyOtpServlet" method="post">

<div class="mb-3">

<label class="form-label">

Enter 6-Digit OTP

</label>

<input
type="text"
name="otp"
class="form-control"
placeholder="Enter OTP"
maxlength="6"
required>

</div>

<button
type="submit"
class="btn btn-success w-100">

Verify OTP

</button>

</form>

<hr>

<div class="text-center">

<a href="forgotPassword.jsp">

← Back

</a>

</div>

</div>

</div>

</div>

</div>

</div>

</body>

</html>