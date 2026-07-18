<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Registration</title>

<style>

body{
    font-family:Arial;
    background:#f2f2f2;
}

.container{
    width:400px;
    margin:60px auto;
    background:white;
    padding:30px;
    border-radius:8px;
    box-shadow:0 0 10px gray;
}

h2{
    text-align:center;
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

<h2>User Registration</h2>

<form action="RegisterServlet" method="post">

<input type="text"
name="fullname"
placeholder="Enter Full Name"
required>

<input type="email"
name="email"
placeholder="Enter Email"
required>

<input type="text"
name="phone"
placeholder="Enter Phone Number"
required>

<input type="password"
name="password"
placeholder="Enter Password"
required>

<button type="submit">
Register
</button>

</form>

<br>

<center>

Already have an account?

<a href="login.jsp">

Login Here

</a>

</center>

</div>

</body>
</html>