package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eventbooking.model.Event;
import com.eventbooking.util.DBConnection;

public class EventDAO {

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	// Add Event
	public boolean addEvent(Event event) {

		boolean status = false;

		try {

			con = DBConnection.getConnection();

			String sql = "INSERT INTO events "
			        + "(event_name,event_date,event_time,venue,ticket_price,available_seats,category,event_image,booking_open,home_section) "
			        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			ps.setString(1, event.getEventName());
			ps.setString(2, event.getEventDate());
			ps.setString(3, event.getEventTime());
			ps.setString(4, event.getVenue());
			ps.setDouble(5, event.getTicketPrice());
			ps.setInt(6, event.getAvailableSeats());
			ps.setString(7, event.getCategory());
			ps.setString(8, event.getEventImage());
			ps.setBoolean(9, event.isBookingOpen());
			ps.setString(10, event.getHomeSection());

			status = ps.executeUpdate() > 0;
			System.out.println("Executing INSERT...");

		} catch (Exception e) {

			e.printStackTrace();

		}

		return status;
	}

	// Get Event By ID
	public Event getEventById(int eventId) {

		Event event = null;

		try {

			con = DBConnection.getConnection();

			String sql = "SELECT * FROM events WHERE event_id=?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, eventId);

			rs = ps.executeQuery();

			if (rs.next()) {

				System.out.println("========== EVENT DAO ==========");
				System.out.println("DB Time = " + rs.getString("event_time"));

				event = new Event();

				event.setEventId(rs.getInt("event_id"));
				event.setEventName(rs.getString("event_name"));
				event.setEventDate(rs.getString("event_date"));
				event.setEventTime(rs.getString("event_time"));
				event.setVenue(rs.getString("venue"));
				event.setTicketPrice(rs.getDouble("ticket_price"));
				event.setAvailableSeats(rs.getInt("available_seats"));
				event.setEventImage(rs.getString("event_image"));
				event.setCategory(rs.getString("category"));
				event.setBookingOpen(rs.getBoolean("booking_open"));
				event.setHomeSection(
					    rs.getString("home_section")
					);

				System.out.println("Object Time = " + event.getEventTime());
				System.out.println("==============================");

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return event;
	}

	// View All Events
	public ArrayList<Event> getAllEvents() {

		ArrayList<Event> list = new ArrayList<>();

		try {

			con = DBConnection.getConnection();

			String sql = "SELECT * FROM events";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

				Event event = new Event();

				event.setEventId(rs.getInt("event_id"));
				event.setEventName(rs.getString("event_name"));
				event.setEventDate(rs.getString("event_date"));
				event.setEventTime(rs.getString("event_time"));
				event.setVenue(rs.getString("venue"));
				event.setTicketPrice(rs.getDouble("ticket_price"));
				event.setAvailableSeats(rs.getInt("available_seats"));
				event.setEventImage(rs.getString("event_image"));
				event.setCategory(rs.getString("category"));
				event.setBookingOpen(rs.getBoolean("booking_open"));
				event.setHomeSection(
					    rs.getString("home_section")
					);

				list.add(event);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return list;
	}

	// Update Available Seats
	public void updateSeats(int eventId, int tickets) {

		try {

			con = DBConnection.getConnection();

			String sql = "UPDATE events SET available_seats = available_seats - ? WHERE event_id=?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, tickets);
			ps.setInt(2, eventId);

			ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	// Total Events
	public int getTotalEvents() {

		int total = 0;

		try {

			con = DBConnection.getConnection();

			String sql = "SELECT COUNT(*) FROM events";

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

	// Update Event
	public boolean updateEvent(Event event) {

	    boolean status = false;

	    try {

	        con = DBConnection.getConnection();

	        String sql =
	        "UPDATE events SET " +
	        "event_name=?, " +
	        "event_date=?, " +
	        "event_time=?, " +
	        "venue=?, " +
	        "ticket_price=?, " +
	        "available_seats=?, " +
	        "category=?, " +
	        "event_image=?, " +
	        "booking_open=?, " +
	        "home_section=? " +
	        "WHERE event_id=?";

	        ps = con.prepareStatement(sql);

	        ps.setString(1, event.getEventName());
	        ps.setString(2, event.getEventDate());
	        ps.setString(3, event.getEventTime());
	        ps.setString(4, event.getVenue());
	        ps.setDouble(5, event.getTicketPrice());
	        ps.setInt(6, event.getAvailableSeats());
	        ps.setString(7, event.getCategory());
	        ps.setString(8, event.getEventImage());
	        ps.setBoolean(9, event.isBookingOpen());
	        ps.setString(10, event.getHomeSection());
	        ps.setInt(11, event.getEventId());

	        status = ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return status;
	}
	// Delete Event
	public boolean deleteEvent(int eventId) {

		boolean status = false;

		try {

			con = DBConnection.getConnection();

			String sql = "DELETE FROM events WHERE event_id=?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, eventId);

			int rows = ps.executeUpdate();

			System.out.println("Deleted Rows = " + rows);

			status = rows > 0;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return status;

	}

	// Search Events
	public ArrayList<Event> searchEvents(String keyword, String category) {

		ArrayList<Event> list = new ArrayList<>();

		try {

			con = DBConnection.getConnection();

			String sql = "SELECT * FROM events WHERE " + "(event_name LIKE ? OR venue LIKE ?) "
					+ "AND (?='' OR category=?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, category);
			ps.setString(4, category);

			rs = ps.executeQuery();

			while (rs.next()) {

			    Event event = new Event();

			    event.setEventId(rs.getInt("event_id"));
			    event.setEventName(rs.getString("event_name"));
			    event.setEventDate(rs.getString("event_date"));
			    event.setEventTime(rs.getString("event_time"));
			    event.setVenue(rs.getString("venue"));
			    event.setTicketPrice(rs.getDouble("ticket_price"));
			    event.setAvailableSeats(rs.getInt("available_seats"));
			    event.setEventImage(rs.getString("event_image"));

			    event.setCategory(rs.getString("category"));
			    event.setBookingOpen(rs.getBoolean("booking_open"));
			    event.setHomeSection(rs.getString("home_section"));

			    list.add(event);

			}
		} catch (Exception e) {

			e.printStackTrace();

		}

		return list;
	}

	// Restore Seats
	public void restoreSeats(int eventId, int tickets) {

		try {

			con = DBConnection.getConnection();

			String sql = "UPDATE events SET available_seats=available_seats+? WHERE event_id=?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, tickets);

			ps.setInt(2, eventId);

			ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}