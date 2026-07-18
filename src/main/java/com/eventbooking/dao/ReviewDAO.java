package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eventbooking.model.Review;
import com.eventbooking.util.DBConnection;

public class ReviewDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Add Review
    public boolean addReview(Review review){

        boolean status = false;

        try{

            con = DBConnection.getConnection();

            String sql =
            "INSERT INTO reviews(user_id,event_id,rating,review) VALUES(?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getEventId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getReview());

            status = ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();

        }

        return status;

    }

    // Get Reviews By Event
    public ArrayList<Review> getReviewsByEvent(int eventId){

        ArrayList<Review> list = new ArrayList<>();

        try{

            con = DBConnection.getConnection();

            String sql =
            "SELECT * FROM reviews WHERE event_id=? ORDER BY review_date DESC";

            ps = con.prepareStatement(sql);

            ps.setInt(1,eventId);

            rs = ps.executeQuery();

            while(rs.next()){

                Review review = new Review();

                review.setReviewId(rs.getInt("review_id"));
                review.setUserId(rs.getInt("user_id"));
                review.setEventId(rs.getInt("event_id"));
                review.setRating(rs.getInt("rating"));
                review.setReview(rs.getString("review"));
                review.setReviewDate(rs.getString("review_date"));

                list.add(review);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return list;

    }

    // Average Rating
    public double getAverageRating(int eventId){

        double avg = 0;

        try{

            con = DBConnection.getConnection();

            String sql =
            "SELECT AVG(rating) FROM reviews WHERE event_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1,eventId);

            rs = ps.executeQuery();

            if(rs.next()){

                avg = rs.getDouble(1);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return avg;

    }

    // Total Reviews
    public int getTotalReviews(int eventId){

        int total = 0;

        try{

            con = DBConnection.getConnection();

            String sql =
            "SELECT COUNT(*) FROM reviews WHERE event_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1,eventId);

            rs = ps.executeQuery();

            if(rs.next()){

                total = rs.getInt(1);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return total;

    }

}