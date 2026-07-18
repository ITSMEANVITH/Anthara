package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eventbooking.model.User;
import com.eventbooking.util.DBConnection;
import com.eventbooking.util.PasswordUtil;

import java.util.ArrayList;

public class UserDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Register User
    public boolean registerUser(User user) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO users(full_name,email,password,phone) VALUES(?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());

            int row = ps.executeUpdate();

            if (row > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // Login User
    public User loginUser(String email, String password) {

        User user = null;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE email=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {

                String storedPassword = rs.getString("password");

                System.out.println("Entered Password : " + password);
                System.out.println("Stored Hash      : " + storedPassword);

                boolean match = PasswordUtil.verifyPassword(password, storedPassword);

                System.out.println("Password Match = " + match);

                if (match) {

                    user = new User();

                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(storedPassword);
                    user.setPhone(rs.getString("phone"));
                    user.setProfileImage(rs.getString("profile_image"));

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
 // Total Users
    public int getTotalUsers() {

        int total = 0;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM users";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {

                total = rs.getInt(1);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return total;

    }
 // View All Users
    public ArrayList<User> getAllUsers() {

        ArrayList<User> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users ORDER BY user_id DESC";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));

                list.add(user);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }
 // Get User By ID
    public User getUserById(int userId) {

        User user = null;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE user_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setProfileImage(rs.getString("profile_image"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return user;

    }
 // Update Profile
    public boolean updateProfile(User user) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql =
            "UPDATE users SET full_name=?, phone=?, profile_image=? WHERE user_id=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getProfileImage());
            ps.setInt(4, user.getUserId());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }
 // Get User By Email
    public User getUserByEmail(String email) {

        User user = null;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE email=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setProfileImage(rs.getString("profile_image"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return user;

    }
 // Update Password
    public boolean updatePassword(String email, String password) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql = "UPDATE users SET password=? WHERE email=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, PasswordUtil.hashPassword(password));

            ps.setString(2, email);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }
}