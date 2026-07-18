package com.eventbooking.controller;

import java.io.File;
import java.io.IOException;

import com.eventbooking.dao.UserDAO;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/UpdateProfileServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class UpdateProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        if(currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = new User();

        user.setUserId(currentUser.getUserId());
        user.setFullName(request.getParameter("fullName"));
        user.setPhone(request.getParameter("phone"));

        // Image Upload
        Part filePart = request.getPart("profileImage");

        String fileName = "";

        if(filePart != null && filePart.getSize() > 0) {

            fileName = System.currentTimeMillis() + "_"
                    + new File(filePart.getSubmittedFileName()).getName();

            String uploadPath = getServletContext().getRealPath("") +
                    File.separator + "uploads";

            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            filePart.write(uploadPath + File.separator + fileName);

            user.setProfileImage("uploads/" + fileName);

        } else {

            user.setProfileImage(currentUser.getProfileImage());

        }

        UserDAO dao = new UserDAO();

        if(dao.updateProfile(user)) {

            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());
            currentUser.setProfileImage(user.getProfileImage());

            session.setAttribute("user", currentUser);

            response.sendRedirect("profile.jsp?success=1");

        } else {

            response.sendRedirect("profile.jsp?error=1");

        }

    }

}