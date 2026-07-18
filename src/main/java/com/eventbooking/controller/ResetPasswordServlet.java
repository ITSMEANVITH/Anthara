package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null){

            response.sendRedirect("forgotPassword.jsp");

            return;

        }

        String email = (String) session.getAttribute("resetEmail");

        String password = request.getParameter("password");

        String confirm = request.getParameter("confirmPassword");

        if(!password.equals(confirm)){

            response.sendRedirect("resetPassword.jsp?error=1");

            return;

        }

        UserDAO dao = new UserDAO();

        boolean status = dao.updatePassword(email, password);

        if(status){

            session.removeAttribute("otp");
            session.removeAttribute("resetEmail");

            response.sendRedirect("login.jsp?reset=success");

        }

        else{

            response.sendRedirect("resetPassword.jsp?error=1");

        }

    }

}