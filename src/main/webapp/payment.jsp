<%@ page import="com.eventbooking.dao.BookingDAO"%>
<%@ page import="com.eventbooking.model.Booking"%>

<%
int bookingId = Integer.parseInt(request.getParameter("bookingId"));

BookingDAO bookingDAO = new BookingDAO();

Booking booking = bookingDAO.getBookingById(bookingId);

if (booking == null) {
    out.println("<h2>Booking Not Found</h2>");
    return;
}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Anthara Payment</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<script src="https://checkout.razorpay.com/v1/checkout.js"></script>

<style>

body{

background:#f5f7fb;

}

.card{

border:none;
border-radius:18px;

}

.price{

font-size:35px;
font-weight:bold;
color:#0d6efd;

}

</style>

</head>

<body>

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-lg-6">

<div class="card shadow-lg">

<div class="card-body p-5">

<h2 class="text-center">

Secure Payment

</h2>

<hr>

<p>

<b>Booking ID</b>

<br>

<%=booking.getBookingId()%>

</p>

<p>

<b>Tickets</b>

<br>

<%=booking.getTickets()%>

</p>

<div class="price">

Rs. <%=booking.getTotalAmount()%>

</div>

<br>

<button

id="payBtn"

class="btn btn-primary btn-lg w-100">

Pay Securely with Razorpay

</button>

</div>

</div>

</div>

</div>

</div>

<script>

document.getElementById("payBtn").onclick=function(){

fetch("CreateOrderServlet",{

method:"POST",

headers:{

"Content-Type":"application/x-www-form-urlencoded"

},

body:"bookingId=<%=booking.getBookingId()%>"

})

.then(response=>response.json())

.then(data=>{

if(data.status==="failed"){

alert(data.message);
return;

}

var options = {

	    key: data.key,

	    amount: data.amount,

	    currency: data.currency,

	    name: "Anthara",

	    description: "Event Ticket Booking",

	    order_id: data.orderId,

	    theme: {
	        color: "#0d6efd"
	    },

	    prefill: {
	        name: "",
	        email: "",
	        contact: ""
	    },
	    config: {
	        display: {
	            blocks: {
	                upi: {
	                    name: "Pay using UPI",
	                    instruments: [
	                        {
	                            method: "upi"
	                        }
	                    ]
	                }
	            },
	            sequence: ["block.upi"],
	            preferences: {
	                show_default_blocks: true
	            }
	        }
	    },

	    modal: {
	        ondismiss: function () {
	            alert("Payment Cancelled");
	        }
	    },

	    handler: function (response) {

	        var form = document.createElement("form");

	        form.method = "POST";

	        form.action = "PaymentSuccessServlet";

	        function addField(name, value) {

	            var input = document.createElement("input");

	            input.type = "hidden";

	            input.name = name;

	            input.value = value;

	            form.appendChild(input);

	        }

	        addField("bookingId", "<%=booking.getBookingId()%>");

	        addField("razorpay_payment_id", response.razorpay_payment_id);

	        addField("razorpay_order_id", response.razorpay_order_id);

	        addField("razorpay_signature", response.razorpay_signature);

	        document.body.appendChild(form);

	        form.submit();

	    }

	};
var rzp=new Razorpay(options);

rzp.open();

});

}

</script>

</body>

</html>