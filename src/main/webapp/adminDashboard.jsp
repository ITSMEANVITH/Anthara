<%@ page import="com.eventbooking.model.Admin"%>
<%@ page import="com.eventbooking.dao.UserDAO"%>
<%@ page import="com.eventbooking.dao.BookingDAO"%>
<%@ page import="com.eventbooking.dao.EventDAO"%>

<%
Admin admin = (Admin) session.getAttribute("admin");

if(admin == null){
    response.sendRedirect("adminLogin.jsp");
    return;
}

UserDAO userDAO = new UserDAO();
BookingDAO bookingDAO = new BookingDAO();
EventDAO eventDAO = new EventDAO();

int totalUsers = userDAO.getTotalUsers();
int totalBookings = bookingDAO.getTotalBookings();
int totalEvents = eventDAO.getTotalEvents();
double revenue = bookingDAO.getTotalRevenue();
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Anthara Admin Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>

body{
    background:#f5f7fb;
    font-family:Arial,sans-serif;
}

.card{
    border:none;
    border-radius:18px;
}

.stat{
    color:white;
    padding:25px;
    border-radius:18px;
    text-align:center;
    transition:.3s;
}

.stat:hover{
    transform:translateY(-5px);
}

.stat h2{
    font-size:36px;
    font-weight:bold;
}

.quick-btn{
    padding:15px;
    font-size:17px;
}

</style>

</head>

<body>

<div class="container mt-5">

<h2 class="mb-2">
Anthara Admin Dashboard
</h2>

<p>
Welcome,
<b><%=admin.getUsername()%></b>
</p>

<!-- Statistics -->

<div class="row g-4">

<div class="col-md-3">

<div class="stat bg-primary">

<h2><%=totalUsers%></h2>

<h5>Total Users</h5>

</div>

</div>

<div class="col-md-3">

<div class="stat bg-success">

<h2><%=totalBookings%></h2>

<h5>Bookings</h5>

</div>

</div>

<div class="col-md-3">

<div class="stat bg-warning text-dark">

<h2><%=totalEvents%></h2>

<h5>Events</h5>

</div>

</div>

<div class="col-md-3">

<div class="stat bg-danger">

<h2>

Rs. <%=String.format("%.0f",revenue)%>

</h2>

<h5>Revenue</h5>

</div>

</div>

</div>

<!-- Quick Actions -->

<div class="card shadow mt-4">

<div class="card-body">

<h4>Quick Actions</h4>

<hr>

<div class="row g-3">

<div class="col-md-3">

<a href="manageEvents.jsp"
class="btn btn-primary w-100 quick-btn">

Manage Events

</a>

</div>

<div class="col-md-3">

<a href="manageBookings.jsp"
class="btn btn-success w-100 quick-btn">

Manage Bookings

</a>

</div>

<div class="col-md-3">

<a href="manageUsers.jsp"
class="btn btn-warning w-100 quick-btn">

Manage Users

</a>

</div>

<div class="col-md-3">

<a href="AdminLogoutServlet"
class="btn btn-danger w-100 quick-btn">

Logout

</a>

</div>

</div>

</div>

</div>

<!-- Charts -->

<div class="row mt-4">

<div class="col-lg-8">

<div class="card shadow">

<div class="card-body">

<h4>Revenue Overview</h4>

<canvas id="revenueChart" height="110"></canvas>

</div>

</div>

</div>

<div class="col-lg-4">

<div class="card shadow">

<div class="card-body">

<h4>Platform Summary</h4>

<hr>

<p><b>Total Users</b></p>

<h3 class="text-primary">
<%=totalUsers%>
</h3>

<hr>

<p><b>Total Bookings</b></p>

<h3 class="text-success">
<%=totalBookings%>
</h3>

<hr>

<p><b>Total Events</b></p>

<h3 class="text-warning">
<%=totalEvents%>
</h3>

<hr>

<p><b>Total Revenue</b></p>

<h3 class="text-danger">
Rs. <%=String.format("%.0f",revenue)%>
</h3>

</div>

</div>

</div>

</div>

</div>

<script>

const ctx=document.getElementById("revenueChart");

new Chart(ctx,{

type:'bar',

data:{

labels:[
'Jan',
'Feb',
'Mar',
'Apr',
'May',
'Jun',
'Jul',
'Aug',
'Sep',
'Oct',
'Nov',
'Dec'
],

datasets:[{

label:'Revenue',

data:[
1200,
3400,
2400,
6500,
12000,
9000,
5000,
0,
0,
0,
0,
0
],

backgroundColor:[
'#0d6efd',
'#198754',
'#ffc107',
'#dc3545',
'#20c997',
'#6f42c1',
'#fd7e14',
'#0dcaf0',
'#6610f2',
'#198754',
'#ffc107',
'#dc3545'
],

borderRadius:8

}]

},

options:{

responsive:true,

plugins:{

legend:{

display:true

}

},

scales:{

y:{

beginAtZero:true

}

}

}

});

</script>

</body>

</html>