import { createFileRoute, Link } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { FiDownload, FiEye, FiCalendar } from "react-icons/fi";

import { BookingService } from "@/services/api";
import { Button } from "@/components/Button";
import { Loader } from "@/components/Loader";

export const Route = createFileRoute("/my-bookings")({
  component: MyBookings,
});

function MyBookings() {

  const [bookings, setBookings] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    loadBookings();

  }, []);

  async function loadBookings() {

    try {

      const res = await BookingService.mine();

      setBookings(res.data);

    } catch (e) {

      console.error(e);

      alert("Unable to load bookings");

    } finally {

      setLoading(false);

    }

  }

  if (loading) {

    return <Loader label="Loading your bookings..." />;

  }

  return (

    <div className="mx-auto max-w-5xl px-4 py-10">

      <h1 className="text-3xl font-bold">

        My Bookings

      </h1>

      <p className="text-muted-foreground mt-2">

        All your booked events

      </p>

      {bookings.length === 0 ? (

        <div className="mt-10 text-center">

          <h2>No bookings found.</h2>

          <Link
            to="/events"
            className="text-blue-600 underline"
          >

            Browse Events

          </Link>

        </div>

      ) : (

        <div className="mt-8 space-y-5">

          {bookings.map((booking) => (

            <div
              key={booking.bookingId}
              className="rounded-xl border p-5 shadow-sm flex flex-col md:flex-row md:justify-between md:items-center"
            >

              <div>

                <h2 className="text-xl font-bold">

                  {booking.title}

                </h2>

                <p>{booking.venue}</p>

                <p>{booking.date}</p>

                <p>{booking.tickets} Ticket(s)</p>

                <p className="font-semibold mt-2">

                  ₹ {booking.amount}

                </p>

              </div>

              <div className="flex gap-3 mt-5 md:mt-0">

                <Button
                  variant="outline"
                  leftIcon={<FiEye />}
                  onClick={() =>
                    window.open(
                      `https://anthara-production.up.railway.app/ViewTicketServlet?bookingId=${booking.bookingId}`,
                      "_blank"
                    )
                  }
                >

                  View

                </Button>

                <Button
                  leftIcon={<FiDownload />}
                  onClick={() =>
                    window.open(
                      `https://anthara-production.up.railway.app/DownloadTicketServlet?bookingId=${booking.bookingId}`,
                      "_blank"
                    )
                  }
                >

                  Download

                </Button>

                <Button
                  variant="secondary"
                  leftIcon={<FiCalendar />}
                  onClick={() =>
                    window.open(
                      `https://anthara-production.up.railway.app/DownloadCalendarServlet?bookingId=${booking.bookingId}`,
                      "_blank"
                    )
                  }
                >

                  Calendar

                </Button>

              </div>

            </div>

          ))}

        </div>

      )}

    </div>

  );

}
