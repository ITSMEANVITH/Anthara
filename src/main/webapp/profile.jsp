<%@ page import="com.eventbooking.model.User"%>

<%
User user = (User) session.getAttribute("user");

if(user == null){
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>My Profile | Anthara</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{
    background:#f5f7fb;
}

.card{
    border:none;
    border-radius:20px;
    box-shadow:0 10px 25px rgba(0,0,0,.08);
}

.profile{
    width:170px;
    height:170px;
    border-radius:50%;
    object-fit:cover;
    border:5px solid #0d6efd;
}

</style>

</head>

<body>

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-lg-7">

<div class="card">

<div class="card-body p-5">

<h2 class="text-center mb-4">

 My Profile

</h2>

<%
if(request.getParameter("success") != null){
%>

<div class="alert alert-success">

Profile Updated Successfully.

</div>

<%
}
%>

<%
if(request.getParameter("error") != null){
%>

<div class="alert alert-danger">

Unable to Update Profile.

</div>

<%
}
%>

<div class="text-center mb-4">

<%
String img = user.getProfileImage();

if(img == null || img.trim().isEmpty()){
    img = "uploads/default.png";
}
%>

<img
class="profile"
src="<%=img%>">

</div>

<form
action="UpdateProfileServlet"
method="post"
enctype="multipart/form-data">

<div class="mb-3">

<label class="form-label">

Full Name

</label>

<input
type="text"
name="fullName"
class="form-control"
value="<%=user.getFullName()%>"
required>

</div>

<div class="mb-3">

<label class="form-label">

Email

</label>

<input
type="email"
class="form-control"
value="<%=user.getEmail()%>"
readonly>

</div>

<div class="mb-3">

<label class="form-label">

Phone Number

</label>

<input
type="text"
name="phone"
class="form-control"
value="<%=user.getPhone()%>">

</div>

<div class="mb-4">

<label class="form-label">

Choose Profile Image

</label>

<input
type="file"
name="profileImage"
class="form-control"
accept=".png,.jpg,.jpeg">

</div>

<div class="d-grid">

<button
type="submit"
class="btn btn-primary btn-lg">

Update Profile

</button>

</div>

</form>

<hr>

<div class="text-center">

<a
href="dashboard.jsp"
class="btn btn-outline-secondary">

Back to Dashboard

</a>

</div>

</div>

</div>

</div>

</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>