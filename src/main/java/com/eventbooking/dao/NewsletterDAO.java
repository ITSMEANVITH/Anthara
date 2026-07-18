package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.eventbooking.util.DBConnection;

public class NewsletterDAO {

	public boolean subscribe(String email) throws Exception {

	    try (Connection con = DBConnection.getConnection()) {

	        PreparedStatement check = con.prepareStatement(
	                "SELECT COUNT(*) FROM newsletter_subscribers WHERE email=?");

	        check.setString(1, email);

	        var rs = check.executeQuery();

	        rs.next();

	        if (rs.getInt(1) > 0) {

	            return false;

	        }

	        PreparedStatement insert = con.prepareStatement(
	                "INSERT INTO newsletter_subscribers(email) VALUES(?)");

	        insert.setString(1, email);

	        insert.executeUpdate();

	        return true;

	    }

	}
	
}