package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.eventbooking.model.Payment;
import com.eventbooking.util.DBConnection;

public class PaymentDAO {

    Connection con = null;
    PreparedStatement ps = null;

    public boolean savePayment(Payment payment) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql =
            "INSERT INTO payments " +
            "(booking_id,payment_method,payment_status," +
            "razorpay_order_id,razorpay_payment_id,razorpay_signature) " +
            "VALUES(?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, payment.getBookingId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setString(3, payment.getPaymentStatus());
            ps.setString(4, payment.getRazorpayOrderId());
            ps.setString(5, payment.getRazorpayPaymentId());
            ps.setString(6, payment.getRazorpaySignature());

            status = ps.executeUpdate() > 0;

        }
        catch(Exception e) {

            e.printStackTrace();

        }

        return status;

    }

}