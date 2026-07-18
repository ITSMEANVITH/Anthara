package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.eventbooking.model.Contact;
import com.eventbooking.util.DBConnection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ContactDAO {

    Connection con = null;
    PreparedStatement ps = null;

    public boolean saveMessage(Contact contact) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql = "INSERT INTO contact_messages(name,email,subject,message) VALUES(?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setString(1, contact.getName());
            ps.setString(2, contact.getEmail());
            ps.setString(3, contact.getSubject());
            ps.setString(4, contact.getMessage());

            int row = ps.executeUpdate();

            if (row > 0) {
                status = true;
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;
    }
    public ArrayList<Contact> getAllMessages() {

        ArrayList<Contact> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql =
            "SELECT * FROM contact_messages ORDER BY id DESC";

            ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Contact contact = new Contact();

                contact.setId(
                    rs.getInt("id")
                );

                contact.setName(
                    rs.getString("name")
                );

                contact.setEmail(
                    rs.getString("email")
                );

                contact.setSubject(
                    rs.getString("subject")
                );

                contact.setMessage(
                    rs.getString("message")
                );

                contact.setCreatedAt(
                    rs.getString("created_at")
                );

                list.add(contact);

            }

        }

        catch(Exception e){

            e.printStackTrace();

        }

        return list;

    }
}