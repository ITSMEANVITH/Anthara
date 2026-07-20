import { createFileRoute, useNavigate, useParams } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { FiMinus, FiPlus } from "react-icons/fi";
import qs from "qs";

import { EventService, BookingService } from "@/services/api";

import { Button } from "@/components/Button";

export const Route = createFileRoute("/booking/$eventId")({
  component: BookingPage,
});

function BookingPage() {

  const { eventId } = useParams({ from: "/booking/$eventId" });

  const navigate = useNavigate();

  const [event, setEvent] = useState<any>(null);

  const [qty, setQty] = useState(1);

  useEffect(() => {

    loadEvent();

  }, []);

  async function loadEvent() {

    try {

      const res = await EventService.get(Number(eventId));

      setEvent(res.data);

    } catch (e) {

      alert("Cannot load event");

    }

  }

  if (!event) {

    return <h2 className="p-10">Loading...</h2>;

  }

  const total = qty * event.price;

  async function bookNow() {

    try {

      const res = await BookingService.create(
    qs.stringify({
        eventId: event.eventId,
        tickets: qty,
        totalAmount: total
    })
);

navigate({
    to: "/payment",
    search: {
        bookingId: res.data.bookingId
    }
});

    } catch (e) {

      alert("Booking Failed");

    }

  }

  return (

    <div className="max-w-4xl mx-auto py-10">

      <h1 className="text-3xl font-bold">

        {event.title}

      </h1>

      <img

        src={`https://anthara-production.up.railway.app/images/${event.image}`}

        className="w-full h-72 object-cover rounded-lg mt-5"

      />

      <div className="mt-5 space-y-2">

        <p>

          Venue : {event.venue}

        </p>

        <p>

          Date : {event.date}

        </p>

        <p>

          Time : {event.time}

        </p>

        <p>

          ₹ {event.price}

        </p>

      </div>

      <div className="flex items-center gap-5 mt-6">

        <button

          onClick={() => setQty(Math.max(1, qty - 1))}

        >

          <FiMinus/>

        </button>

        <h2>{qty}</h2>

        <button

          onClick={() =>

            setQty(Math.min(event.availableSeats, qty + 1))

          }

        >

          <FiPlus/>

        </button>

      </div>

      <h2 className="mt-5 text-xl font-bold">

        Total : ₹ {total}

      </h2>

      <Button

        fullWidth

        className="mt-6"

        onClick={bookNow}

      >

        Book Ticket

      </Button>

    </div>

  );

}
