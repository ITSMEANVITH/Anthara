<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Forgot Password | Anthara</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{

background:#f5f7fb;

}

.card{

border:none;

border-radius:18px;

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

Forgot Password

</h2>

<form action="ForgotPasswordServlet" method="post">

<div class="mb-3">

<label class="form-label">

Registered Email

</label>

<input

type="email"

name="email"

class="form-control"

required>

</div>

<button

class="btn btn-primary w-100">

Send OTP

</button>

</form>

<br>

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