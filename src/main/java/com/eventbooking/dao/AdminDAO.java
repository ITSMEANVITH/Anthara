package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eventbooking.model.Admin;
import com.eventbooking.util.DBConnection;
import com.eventbooking.util.PasswordUtil;

public class AdminDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Admin loginAdmin(String username, String password) {

        Admin admin = null;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM admin WHERE username=?";

            ps = con.prepareStatement(sql);

            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {

                String storedPassword = rs.getString("password");

                if (PasswordUtil.verifyPassword(password, storedPassword)) {

                    admin = new Admin();

                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setUsername(rs.getString("username"));
                    admin.setPassword(storedPassword);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return admin;

    }

}