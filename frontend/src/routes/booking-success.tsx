import { createFileRoute, Link } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import {
  FiCheckCircle,
  FiDownload,
  FiCalendar,
  FiEye,
} from "react-icons/fi";

import { BookingService } from "@/services/api";
import { Button } from "@/components/Button";
import { Loader } from "@/components/Loader";

export const Route = createFileRoute("/booking-success")({
  validateSearch: (search: Record<string, unknown>) => ({
    bookingId: Number(search.bookingId ?? 0),
  }),
  component: BookingSuccess,
});

function BookingSuccess() {

  const { bookingId } = Route.useSearch();

  const [booking, setBooking] = useState<any>(null);

  const [loading, setLoading] = useState(true);

  useEffect(() => {

    loadBooking();

  }, []);

  async function loadBooking() {

    try {

      const res = await BookingService.get(bookingId);

      setBooking(res.data);

    } catch (e) {

      console.error(e);

      alert("Unable to load booking.");

    } finally {

      setLoading(false);

    }

  }

  if (loading) {

    return <Loader label="Loading Ticket..." />;

  }

  if (!booking) {

    return (

      <div className="mx-auto max-w-3xl py-24 text-center">

        <h1 className="text-3xl font-bold">

          Booking not found

        </h1>

        <Link
          to="/events"
          className="mt-5 inline-block text-primary hover:underline"
        >
          ← Browse Events
        </Link>

      </div>

    );

  }

  const qr =

    `https://api.qrserver.com/v1/create-qr-code/?size=220x220&data=` +

    encodeURIComponent(

      `BOOKING:${booking.bookingId}|EVENT:${booking.eventId}|AMOUNT:${booking.amount}`

    );

  return (

    <div className="mx-auto max-w-3xl px-5 py-12">

      <div className="text-center">

        <FiCheckCircle className="mx-auto text-7xl text-green-600" />

        <h1 className="mt-4 text-4xl font-bold">

          Payment Successful

        </h1>

        <p className="mt-3 text-gray-500">

          Your booking has been confirmed successfully.

        </p>

      </div>

      <div className="mt-10 rounded-xl border bg-white p-6 shadow">

        <div className="mb-6 border-b pb-4">

          <h2 className="text-xl font-bold">

            Booking ID

          </h2>

          <p className="text-blue-600 font-semibold">

            #{booking.bookingId}

          </p>

        </div>

        <div className="space-y-3">

          <Row
            label="Event"
            value={booking.title}
          />

          <Row
            label="Venue"
            value={booking.venue}
          />

          <Row
            label="Date"
            value={booking.date}
          />

          <Row
            label="Time"
            value={booking.time}
          />

          <Row
            label="Tickets"
            value={booking.tickets.toString()}
          />

          <Row
            label="Amount Paid"
            value={`₹ ${booking.amount}`}
          />

          <Row
            label="Payment Status"
            value="SUCCESS"
            highlight
          />

        </div>

        <div className="mt-8 flex justify-center">

          <img

            src={qr}

            alt="QR"

            className="rounded-lg border"

          />

        </div>

        <p className="mt-3 text-center text-sm text-gray-500">

          Scan this QR code at the venue.

        </p>

      </div>

      <div className="mt-6 grid gap-3 sm:grid-cols-3">

  <Button
    leftIcon={<FiDownload />}
    fullWidth
    onClick={() =>
      window.open(
        `https://anthara-production.up.railway.app/DownloadTicketServlet?bookingId=${booking.bookingId}`,
        "_blank"
      )
    }
  >
    Download Ticket
  </Button>

  <Button
    variant="outline"
    leftIcon={<FiEye />}
    fullWidth
    onClick={() =>
        window.open(
            `https://anthara-production.up.railway.app/ViewTicketServlet?bookingId=${booking.bookingId}`,
            "_blank"
        )
    }
>
    View Ticket
</Button>

  <Button
    variant="secondary"
    leftIcon={<FiCalendar />}
    fullWidth
    onClick={() =>
        window.open(
            `https://anthara-production.up.railway.app/DownloadCalendarServlet?bookingId=${booking.bookingId}`,
            "_blank"
        )
    }
>
    Add to Calendar
</Button>
</div>

      <div className="mt-8 flex justify-center gap-5">

        <Link
          to="/my-bookings"
          className="text-primary hover:underline"
        >
          My Bookings
        </Link>

        <Link
          to="/events"
          className="text-primary hover:underline"
        >
          Browse Events
        </Link>

      </div>

    </div>

  );

}

function Row({

  label,

  value,

  highlight,

}: {

  label: string;

  value: string;

  highlight?: boolean;

}) {

  return (

    <div className="flex justify-between border-b py-2">

      <span className="text-gray-500">

        {label}

      </span>

      <span

        className={

          highlight

            ? "font-bold text-green-600"

            : "font-semibold"

        }

      >

        {value}

      </span>

    </div>

  );

}
