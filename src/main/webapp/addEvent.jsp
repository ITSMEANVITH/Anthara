<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Add Event</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{

background:#f5f7fb;

}

.card{

margin-top:40px;
border:none;
border-radius:18px;

}

</style>

</head>

<body>

<div class="container">

<div class="row justify-content-center">

<div class="col-md-7">

<div class="card shadow">

<div class="card-body">

<h2>Add New Event</h2>

<hr>

<form action="AddEventServlet"
      method="post"
      enctype="multipart/form-data">

<div class="mb-3">

<label>Event Name</label>

<input
type="text"
name="eventName"
class="form-control"
required>

</div>

<div class="mb-3">

<label>Date</label>

<input
type="date"
name="eventDate"
class="form-control"
required>

</div>

<div class="mb-3">

<label>Event Time</label>

<input
type="time"
name="eventTime"
class="form-control"
required>

</div>

<div class="mb-3">

<label>Venue</label>

<input
type="text"
name="venue"
class="form-control"
required>

</div>

<div class="mb-3">

<label>Ticket Price</label>

<input
type="number"
step="0.01"
name="ticketPrice"
class="form-control"
required>

</div>

<div class="mb-3">

<label>Available Seats</label>

<input
type="number"
name="availableSeats"
class="form-control"
required>

</div>

<div class="mb-3">

<label>Event Poster</label>

<input

type="file"

name="eventImage"

class="form-control"

accept="image/*"

required>

</div>

<button class="btn btn-success">

Add Event

</button>

<a
href="manageEvents.jsp"
class="btn btn-secondary">

Cancel

</a>

</form>

</div>

</div>

</div>

</div>

</div>

</body>

</html>