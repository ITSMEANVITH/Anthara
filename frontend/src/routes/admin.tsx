import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useEffect, useState } from "react";

import { FiUsers, FiCalendar, FiShoppingBag, FiDollarSign } from "react-icons/fi";
import {
  ResponsiveContainer,
  BarChart,
  Bar,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  Legend,
} from "recharts";

import { formatCurrency } from "@/services/mockData";
import { AdminService, ContactService, AdminAuthService } from "@/services/api";
import { StatCard } from "@/components/StatCard";
import { DataTable, type Column } from "@/components/DataTable";
import { Button } from "@/components/Button";

// Base URL for uploaded event images. Set VITE_API_BASE_URL in your env
// so this doesn't break outside local dev.
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "https://anthara-production.up.railway.app";

export const Route = createFileRoute("/admin")({
  head: () => ({ meta: [{ title: "Admin Dashboard — Anthara" }] }),
  component: Admin,
});

const EMPTY_NEW_EVENT = {
  eventName: "",
  venue: "",
  eventDate: "",
  eventTime: "",
  ticketPrice: "",
  availableSeats: "",
  category: "",
  bookingOpen: "true",
  homeSection: "hide",
};

function Admin() {
  const navigate = useNavigate();

  const userCols: Column<any>[] = [
    {
      header: "Name",
      accessor: (u) => <span className="font-medium">{u.fullName}</span>,
    },
    {
      header: "Email",
      accessor: (u) => <span className="text-muted-foreground">{u.email}</span>,
    },
    {
      header: "Phone",
      accessor: (u) => u.phone,
    },
  ];

  const bookingCols: Column<any>[] = [
    {
      header: "Booking",
      accessor: (b) => <span className="font-medium">#{b.bookingId}</span>,
    },
    { header: "User", accessor: (b) => b.user },
    { header: "Event", accessor: (b) => b.event },
    { header: "Tickets", accessor: (b) => b.tickets, align: "center" },
    { header: "Amount", accessor: (b) => formatCurrency(b.amount), align: "right" },
    {
      header: "Status",
      accessor: (b) => (
        <span
          className={
            b.paymentStatus === "SUCCESS"
              ? "text-green-600 font-semibold"
              : "text-red-600 font-semibold"
          }
        >
          {b.paymentStatus}
        </span>
      ),
    },
  ];

  const [stats, setStats] = useState({
    totalUsers: 0,
    totalEvents: 0,
    totalBookings: 0,
    totalRevenue: 0,
  });

  const [monthlyRevenue, setMonthlyRevenue] = useState<any[]>([]);
  const [users, setUsers] = useState<any[]>([]);
  const [bookings, setBookings] = useState<any[]>([]);
  const [events, setEvents] = useState<any[]>([]);
  const [messages, setMessages] = useState<any[]>([]);
  const [editEvent, setEditEvent] = useState<any>(null);
  const [image, setImage] = useState<File | null>(null);
  const [showEdit, setShowEdit] = useState(false);
  const [showAdd, setShowAdd] = useState(false);
  const [newEvent, setNewEvent] = useState({ ...EMPTY_NEW_EVENT });

  const eventCols: Column<any>[] = [
    { header: "Event", accessor: (e) => <span className="font-medium">{e.title}</span> },
    { header: "Venue", accessor: (e) => e.venue },
    { header: "Date", accessor: (e) => e.date },
    { header: "Time", accessor: (e) => e.time },
    { header: "Price", accessor: (e) => formatCurrency(e.price) },
    { header: "Seats", accessor: (e) => e.seats, align: "center" },
    {
      header: "Actions",
      align: "right",
      accessor: (e) => (
        <div className="flex gap-2 justify-end">
          <Button size="sm" onClick={() => openEdit(e.eventId)}>
            Edit
          </Button>
          <Button
            size="sm"
            variant="ghost"
            className="text-red-600"
            onClick={() => deleteEvent(e.eventId)}
          >
            Delete
          </Button>
        </div>
      ),
    },
  ];

  const contactCols: Column<any>[] = [
    { header: "Name", accessor: (m) => m.name },
    { header: "Email", accessor: (m) => m.email },
    { header: "Subject", accessor: (m) => m.subject },
    {
      header: "Message",
      accessor: (m) => <div className="max-w-xs truncate">{m.message}</div>,
    },
    { header: "Date", accessor: (m) => m.createdAt },
  ];

  useEffect(() => {
    checkAdmin();
    loadAnalytics();
    loadUsers();
    loadBookings();
    loadEvents();
    loadMessages();
  }, []);

  async function checkAdmin() {
    try {
      await AdminService.me();
    } catch {
      navigate({ to: "/adminLogin" });
    }
  }

  async function logout() {
    try {
      await AdminAuthService.logout();
      navigate({ to: "/adminLogin" });
    } catch (e) {
      console.error(e);
    }
  }

  async function loadAnalytics() {
    try {
      const res = await AdminService.analytics();
      setStats(res.data);

      const months = [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
      ];

      const graphData = months
        .map((month, index) => ({
          month,
          revenue: res.data.monthlyRevenue[index],
          bookings: res.data.monthlyBookings[index],
        }))
        .filter((item) => item.revenue > 0 || item.bookings > 0);

      setMonthlyRevenue(graphData);
    } catch (e) {
      console.error(e);
    }
  }

  async function loadUsers() {
    try {
      const res = await AdminService.users();
      setUsers(res.data);
    } catch (e) {
      console.error(e);
    }
  }

  async function loadBookings() {
    try {
      const res = await AdminService.bookings();
      setBookings(res.data);
    } catch (e) {
      console.error(e);
    }
  }

  async function loadEvents() {
    try {
      const res = await AdminService.events();
      setEvents(res.data);
    } catch (e) {
      console.error(e);
    }
  }

  async function loadMessages() {
    try {
      const res = await ContactService.getAll();
      setMessages(res.data);
    } catch (e) {
      console.error(e);
    }
  }

  async function openEdit(eventId: number) {
    try {
      const res = await AdminService.getEvent(eventId);
      setEditEvent(res.data);
      setShowEdit(true);
    } catch (err: any) {
      console.error(err);
      alert(JSON.stringify(err.response?.data ?? "Unable to load event"));
    }
  }

  async function deleteEvent(eventId: number) {
    const ok = window.confirm("Delete this event?");
    if (!ok) return;

    try {
      await AdminService.deleteEvent(eventId);
      loadEvents();
      alert("Event Deleted");
    } catch (err: any) {
      alert(err.response?.data?.message ?? "Unable to delete event");
    }
  }

  async function saveEvent() {
    console.log("saveEvent() called, editEvent =", editEvent); // TEMP DEBUG

    try {
      const data = new FormData();
      data.append("eventId", editEvent.eventId.toString());
      data.append("eventName", editEvent.eventName);
      data.append("eventDate", editEvent.eventDate);
      data.append("eventTime", editEvent.eventTime);
      data.append("venue", editEvent.venue);
      data.append("ticketPrice", editEvent.ticketPrice.toString());
      data.append("availableSeats", editEvent.availableSeats.toString());
      data.append("bookingOpen", editEvent.bookingOpen.toString());
      data.append("homeSection", editEvent.homeSection);

      if (editEvent.imageFile) {
        data.append("image", editEvent.imageFile);
      }

      console.log("FormData built. homeSection =", editEvent.homeSection); // TEMP DEBUG
      for (const [key, value] of data.entries()) {
        console.log("  FormData ->", key, "=", value); // TEMP DEBUG
      }

      console.log("Calling AdminService.updateEvent..."); // TEMP DEBUG
      const res = await AdminService.updateEvent(data);
      console.log("AdminService.updateEvent response:", res); // TEMP DEBUG

      alert("Event Updated Successfully");
      setShowEdit(false);
      setEditEvent(null);
      loadEvents();
    } catch (e) {
      console.error("saveEvent() FAILED:", e); // TEMP DEBUG (louder)
      alert("Update Failed");
    }
  }

  async function addEvent() {
    try {
      const data = new FormData();
      data.append("eventName", newEvent.eventName);
      data.append("venue", newEvent.venue);
      data.append("eventDate", newEvent.eventDate);
      data.append("eventTime", newEvent.eventTime);
      data.append("ticketPrice", newEvent.ticketPrice);
      data.append("availableSeats", newEvent.availableSeats);
      data.append("category", newEvent.category);
      data.append("bookingOpen", newEvent.bookingOpen);
      data.append("homeSection", newEvent.homeSection);

      if (image) {
        data.append("image", image);
      }

      await AdminService.addEvent(data);
      alert("Event Added Successfully");
      setShowAdd(false);
      setNewEvent({ ...EMPTY_NEW_EVENT });
      setImage(null);
      loadEvents();
    } catch (err: any) {
      console.error(err);
      alert("Unable to add event");
    }
  }

  return (
    <div className="mx-auto max-w-7xl px-4 py-10 sm:px-6">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div>
          <h1 className="text-3xl font-extrabold">Admin Dashboard</h1>
          <p className="mt-1 text-muted-foreground">Overview of Anthara performance</p>
        </div>
        <div className="flex gap-3">
          <Button leftIcon={<FiCalendar />} onClick={() => setShowAdd(true)}>
            Add Event
          </Button>
          <Button variant="outline" onClick={logout}>
            Logout
          </Button>
        </div>
      </div>

      <div className="mt-6 grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
        <StatCard
          label="Total Users"
          value={stats.totalUsers.toLocaleString("en-IN")}
          icon={<FiUsers />}
        />
        <StatCard label="Total Events" value={stats.totalEvents.toString()} icon={<FiCalendar />} />
        <StatCard
          label="Total Bookings"
          value={stats.totalBookings.toLocaleString("en-IN")}
          icon={<FiShoppingBag />}
        />
        <StatCard
          label="Total Revenue"
          value={formatCurrency(stats.totalRevenue)}
          icon={<FiDollarSign />}
        />
      </div>

      {/* Revenue chart */}
      <div className="mt-6 rounded-[16px] border border-border bg-card p-6 shadow-soft">
        <h3 className="text-lg font-bold">Monthly Revenue</h3>
        <div className="mt-4 h-72 w-full">
          <ResponsiveContainer width="100%" height={340}>
            <BarChart data={monthlyRevenue} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
              <CartesianGrid strokeDasharray="5 5" opacity={0.3} />
              <XAxis dataKey="month" tick={{ fontSize: 13, fontWeight: 600 }} />
              <YAxis
                yAxisId="left"
                tickFormatter={(v) => (v >= 1000 ? `${Math.round(v / 1000)}k` : v)}
              />
              <YAxis yAxisId="right" orientation="right" />
              <Tooltip
                contentStyle={{
                  borderRadius: 12,
                  border: "none",
                  boxShadow: "0 8px 20px rgba(0,0,0,.15)",
                }}
                formatter={(value, name) =>
                  name === "Revenue" ? formatCurrency(Number(value)) : `${value} Bookings`
                }
              />
              <Legend verticalAlign="top" height={40} />
              <Bar
                yAxisId="left"
                dataKey="revenue"
                name="Revenue"
                fill="#8B5CF6"
                radius={[10, 10, 0, 0]}
                barSize={80}
              />
              <Line
                yAxisId="right"
                type="monotone"
                dataKey="bookings"
                name="Bookings"
                stroke="#3B82F6"
                strokeWidth={4}
                dot={{ r: 6, fill: "#3B82F6" }}
                activeDot={{ r: 8 }}
              />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Tables */}
      <div className="mt-6 grid gap-6 lg:grid-cols-2">
        <div>
          <h3 className="mb-3 text-lg font-bold">Recent Users</h3>
          <DataTable columns={userCols} data={users} />
        </div>
        <div>
          <h3 className="mb-3 text-lg font-bold">Recent Bookings</h3>
          <DataTable columns={bookingCols} data={bookings} />
        </div>
      </div>

      <div className="mt-6">
        <h3 className="mb-3 text-lg font-bold">Manage Events</h3>
        <DataTable columns={eventCols} data={events} />

        {showAdd && (
          <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
            <div className="bg-white rounded-xl w-[500px] max-h-[90vh] flex flex-col">
              <h2 className="text-2xl font-bold mb-6 px-6 pt-6">Add Event</h2>

              <div className="space-y-4 p-6 overflow-y-auto flex-1">
                <input
                  className="w-full border p-2 rounded"
                  placeholder="Event Name"
                  value={newEvent.eventName}
                  onChange={(e) => setNewEvent({ ...newEvent, eventName: e.target.value })}
                />
                <input
                  className="w-full border p-2 rounded"
                  placeholder="Venue"
                  value={newEvent.venue}
                  onChange={(e) => setNewEvent({ ...newEvent, venue: e.target.value })}
                />
                <input
                  type="date"
                  className="w-full border p-2 rounded"
                  value={newEvent.eventDate}
                  onChange={(e) => setNewEvent({ ...newEvent, eventDate: e.target.value })}
                />
                <input
                  type="time"
                  className="w-full border p-2 rounded"
                  value={newEvent.eventTime}
                  onChange={(e) => setNewEvent({ ...newEvent, eventTime: e.target.value })}
                />
                <input
                  type="number"
                  className="w-full border p-2 rounded"
                  placeholder="Ticket Price"
                  value={newEvent.ticketPrice}
                  onChange={(e) => setNewEvent({ ...newEvent, ticketPrice: e.target.value })}
                />
                <input
                  type="number"
                  className="w-full border p-2 rounded"
                  placeholder="Available Seats"
                  value={newEvent.availableSeats}
                  onChange={(e) => setNewEvent({ ...newEvent, availableSeats: e.target.value })}
                />
                <input
                  className="w-full border p-2 rounded"
                  placeholder="Category"
                  value={newEvent.category}
                  onChange={(e) => setNewEvent({ ...newEvent, category: e.target.value })}
                />

                <label className="block">
                  <span className="mb-1 block font-medium">Booking Status</span>
                  <select
                    className="w-full rounded-lg border p-2"
                    value={newEvent.bookingOpen}
                    onChange={(e) => setNewEvent({ ...newEvent, bookingOpen: e.target.value })}
                  >
                    <option value="true">Booking Open</option>
                    <option value="false">Coming Soon</option>
                  </select>
                </label>

                <label className="block">
                  <span className="mb-1 block font-medium">Show On Home</span>
                  <select
                    className="w-full rounded-lg border p-2"
                    value={newEvent.homeSection}
                    onChange={(e) => setNewEvent({ ...newEvent, homeSection: e.target.value })}
                  >
                    <option value="featured">Featured</option>
                    <option value="upcoming">Upcoming</option>
                    <option value="hide">Hide</option>
                  </select>
                </label>

                <label className="font-medium">Event Image</label>
                <input
                  type="file"
                  accept="image/*"
                  onChange={(e) => {
                    if (e.target.files) {
                      setImage(e.target.files[0]);
                    }
                  }}
                />
              </div>

              <div className="border-t p-4 flex justify-end gap-3 bg-white">
                <Button
                  variant="outline"
                  onClick={() => {
                    setShowAdd(false);
                    setNewEvent({ ...EMPTY_NEW_EVENT });
                    setImage(null);
                  }}
                >
                  Cancel
                </Button>
                <Button onClick={addEvent}>Add Event</Button>
              </div>
            </div>
          </div>
        )}

        {showEdit && editEvent && (
          <div className="fixed inset-0 bg-black/50 z-50 overflow-y-auto">
            <div className="min-h-screen flex items-center justify-center p-6">
              <div className="bg-white rounded-xl w-[500px] max-h-[90vh] flex flex-col">
                <h2 className="text-2xl font-bold mb-6 px-6 pt-6">Edit Event</h2>

                <div className="space-y-4 p-6 overflow-y-auto flex-1">
                  <input
                    className="w-full border p-2 rounded"
                    value={editEvent.eventName}
                    onChange={(e) => setEditEvent({ ...editEvent, eventName: e.target.value })}
                  />
                  <input
                    className="w-full border p-2 rounded"
                    value={editEvent.venue}
                    onChange={(e) => setEditEvent({ ...editEvent, venue: e.target.value })}
                  />
                  <input
                    type="date"
                    className="w-full border p-2 rounded"
                    value={editEvent.eventDate}
                    onChange={(e) => setEditEvent({ ...editEvent, eventDate: e.target.value })}
                  />
                  <input
                    type="time"
                    className="w-full border p-2 rounded"
                    value={editEvent.eventTime}
                    onChange={(e) => setEditEvent({ ...editEvent, eventTime: e.target.value })}
                  />
                  <input
                    type="number"
                    className="w-full border p-2 rounded"
                    value={editEvent.ticketPrice}
                    onChange={(e) =>
                      setEditEvent({ ...editEvent, ticketPrice: Number(e.target.value) })
                    }
                  />
                  <input
                    type="number"
                    className="w-full border p-2 rounded"
                    value={editEvent.availableSeats}
                    onChange={(e) =>
                      setEditEvent({ ...editEvent, availableSeats: Number(e.target.value) })
                    }
                  />

                  <label className="block">
                    <span className="mb-1 block font-medium">Booking Status</span>
                    <select
                      className="w-full rounded-lg border p-2"
                      value={editEvent.bookingOpen.toString()}
                      onChange={(e) =>
                        setEditEvent({ ...editEvent, bookingOpen: e.target.value === "true" })
                      }
                    >
                      <option value="true">Booking Open</option>
                      <option value="false">Coming Soon</option>
                    </select>
                  </label>

                  <label className="block">
                    <span className="mb-1 block font-medium">Show On Home</span>
                    <select
                      className="w-full rounded-lg border p-2"
                      value={editEvent.homeSection}
                      onChange={(e) => setEditEvent({ ...editEvent, homeSection: e.target.value })}
                    >
                      <option value="featured">Featured</option>
                      <option value="upcoming">Upcoming</option>
                      <option value="hide">Hide</option>
                    </select>
                  </label>

                  {editEvent.eventImage && (
                    <img
                      src={`${API_BASE_URL}/Anthara/images/${encodeURIComponent(editEvent.eventImage)}`}
                      className="w-48 h-32 object-cover rounded border"
                      alt={editEvent.eventName}
                    />
                  )}

                  <input
                    type="file"
                    accept="image/*"
                    onChange={(e) => {
                      if (!e.target.files?.length) return;
                      setEditEvent({ ...editEvent, imageFile: e.target.files[0] });
                    }}
                  />
                </div>

                <div className="border-t p-4 flex justify-end gap-3 bg-white sticky bottom-0">
                  <Button
                    variant="outline"
                    onClick={() => {
                      setShowEdit(false);
                      setEditEvent(null);
                    }}
                  >
                    Cancel
                  </Button>
                  <Button onClick={saveEvent}>Save Changes</Button>
                </div>
              </div>
            </div>
          </div>
        )}

        <div className="mt-8">
          <h3 className="mb-3 text-lg font-bold">Contact Messages</h3>
          <DataTable columns={contactCols} data={messages} />
        </div>
      </div>
    </div>
  );
}
