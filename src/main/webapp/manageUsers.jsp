<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eventbooking.dao.UserDAO"%>
<%@ page import="com.eventbooking.model.User"%>
<%@ page import="com.eventbooking.model.Admin"%>

<%
Admin admin = (Admin)session.getAttribute("admin");

if(admin == null){

    response.sendRedirect("adminLogin.jsp");
    return;

}

UserDAO dao = new UserDAO();

ArrayList<User> users = dao.getAllUsers();
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Manage Users</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-5">

<div class="card shadow">

<div class="card-body">

<h2>Manage Users</h2>

<hr>

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>

<th>Name</th>

<th>Email</th>

<th>Phone</th>

</tr>

</thead>

<tbody>

<%
for(User u : users){
%>

<tr>

<td><%=u.getUserId()%></td>

<td><%=u.getFullName()%></td>

<td><%=u.getEmail()%></td>

<td><%=u.getPhone()%></td>

</tr>

<%
}
%>

</tbody>

</table>

<br>

<a href="adminDashboard.jsp"
class="btn btn-primary">

Back

</a>

</div>

</div>

</div>

</body>

</html>