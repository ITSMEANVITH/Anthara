# 🎟️ Anthara

<p align="center">
  <strong>A Modern Full-Stack Event Ticket Booking Platform</strong>
</p>

<p align="center">
Built with React, Java Servlets, MySQL, Railway, Cloudflare and Razorpay
</p>

<p align="center">
<a href="https://anthara.anthara.workers.dev">🌐 Live Demo</a> •
<a href="https://github.com/ITSMEANVITH/Anthara">📂 GitHub Repository</a>
</p>

---

# 📖 About

Anthara is a modern full-stack event ticket booking platform designed to provide a seamless experience for discovering events, booking tickets securely, managing bookings, and verifying digital tickets.

The application uses a React frontend with a Java Servlet backend connected to a MySQL database. It integrates Razorpay for secure online payments and Brevo for transactional email notifications.

The platform is deployed using Cloudflare Workers for the frontend and Railway for the backend.

---

# ✨ Features

## 👤 User Features

- Secure User Registration
- Login Authentication
- BCrypt Password Encryption
- Forgot Password with OTP Verification
- Profile Management

---

## 🎉 Event Features

- Browse Events
- Event Details
- Search Events
- Wishlist
- Book Tickets
- Booking History
- Cancel Booking

---

## 💳 Payment

- Razorpay Integration
- Secure Payment Verification
- Order Creation
- Booking Confirmation

---

## 📧 Email Services

- Booking Confirmation Email
- Booking Cancellation Email
- OTP Verification Email
- Newsletter Subscription
- Contact Form Support

---

## 🎟 Ticket Management

- Download PDF Ticket
- QR Code Ticket
- QR Ticket Verification
- Calendar (.ics) Download

---

## 👨‍💼 Admin Features

- Secure Admin Login
- Dashboard
- Event Management
- Booking Management
- User Management
- Analytics

---

# 🛠 Tech Stack

## Frontend

- React
- Vite
- HTML5
- CSS3
- JavaScript

## Backend

- Java
- Java Servlets
- JSP
- Maven

## Database

- MySQL

## Cloud

- Railway
- Cloudflare Workers

## Payment Gateway

- Razorpay

## Email Service

- Brevo API

---

# 🏗 System Architecture

```
                 React + Vite
          (Cloudflare Workers)
                     │
                     │ REST API
                     ▼
        Java Servlets + JSP (Railway)
                     │
                  JDBC
                     │
                  MySQL
               /            \
         Razorpay        Brevo API
```

---

# 📂 Project Structure

```
Anthara
│
├── Frontend/
│
├── src/
│   └── main/
│       ├── java/
│       ├── resources/
│       └── webapp/
│
├── pom.xml
├── Dockerfile
├── README.md
└── .gitignore
```

---

# 🚀 Installation

## Clone Repository

```bash
git clone https://github.com/ITSMEANVITH/Anthara.git
```

## Backend

```bash
mvn clean install
```

Deploy the generated WAR file on Apache Tomcat.

---

## Frontend

```bash
npm install
npm run dev
```

---

# 🌍 Deployment

| Component | Platform |
|------------|----------|
| Frontend | Cloudflare Workers |
| Backend | Railway |
| Database | MySQL |
| Payment | Razorpay |
| Email | Brevo |

---

# 📸 Screenshots

Screenshots added in the screenshots folder.

---

# 📋 Future Enhancements

- Seat Selection
- Google Authentication
- AI Event Recommendations
- Event Categories
- Mobile Application
- Dark Mode
- Push Notifications

---

# 👥 Contributors

## Ranjitha S Shetty

**Frontend Developer & UI/UX Designer**

- UI/UX Design
- React Frontend Development
- Responsive Design
- User Interface Development
- User Experience Design

---

## Anvith C D

**Backend Developer**

- Java Backend Development
- REST API Development
- Database Design
- Authentication System
- Razorpay Integration
- Brevo Email Integration
- Railway Deployment
- System Integration

---



# 🎓 Academic Information

**College**

K S Institute of Technology

**Department**

Computer Science and Design (CSD)

---

# 🌐 Live Demo

https://anthara.anthara.workers.dev

---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.

---

# 📄 License

This project is licensed under the MIT License.
