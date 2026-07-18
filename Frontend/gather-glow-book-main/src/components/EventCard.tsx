import { Link } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import {
  FiMapPin,
  FiCalendar,
  FiClock,
  FiHeart
} from "react-icons/fi";
import { FaHeart } from "react-icons/fa";

import { WishlistService } from "@/services/api";
import { type AnthEvent, formatCurrency, formatDate } from "@/services/mockData";
import { Button } from "./Button";


export function EventCard({
  event,
  onWishlistChange,
}: {
  event: AnthEvent;
  onWishlistChange: (eventId: number, wishlisted: boolean) => void;
}) {


const [wishlisted, setWishlisted] = useState(event.wishlisted);

useEffect(() => {
    setWishlisted(event.wishlisted);
}, 
[event.wishlisted]);

async function toggleWishlist() {

  try {

    if (wishlisted) {

      await WishlistService.remove(event.id);

      setWishlisted(false);

      onWishlistChange(event.id, false);

    } else {

      await WishlistService.add(event.id);

      setWishlisted(true);

      onWishlistChange(event.id, true);

    }

  } catch (e) {

    console.error(e);

    alert("Unable to update wishlist.");

  }

}



  const lowSeats = event.seatsAvailable <= 20;



  
  return (
    <div className="card-lift group flex flex-col overflow-hidden rounded-[16px] border border-border bg-card shadow-soft">
      <Link to="/events/$eventId" params={{ eventId: event.id }} className="relative block overflow-hidden">
        <img
    src={event.image}
    alt={event.title}
    loading="lazy"
    className="h-48 w-full object-cover transition-transform duration-500 group-hover:scale-105"
/>
        <span
  className={`absolute left-3 top-3 rounded-[999px] px-3 py-1 text-xs font-semibold text-white ${
    event.bookingOpen ? "gradient-brand" : "bg-orange-500"
  }`}
>
  {event.bookingOpen ? event.category : "Coming Soon"}
</span>
        <div className="absolute right-3 top-3 flex gap-2">

  <button
    onClick={(e) => {

      e.preventDefault();

      toggleWishlist();

    }}
    className="rounded-full bg-white p-2 shadow"
  >

    {

      wishlisted

      ?

      <FaHeart className="text-red-500" />

      :

      <FiHeart />

    }

  </button>

  <span className="rounded-[999px] bg-background/90 px-3 py-1 text-xs font-semibold text-foreground backdrop-blur">

    {formatCurrency(event.price)}

  </span>

</div>
      </Link>

      <div className="flex flex-1 flex-col p-4">
        <Link to="/events/$eventId" params={{ eventId: event.id }}>
          <h3 className="line-clamp-1 text-lg font-bold transition-colors hover:text-primary">
            {event.title}
          </h3>
        </Link>

        <div className="mt-2 space-y-1.5 text-sm text-muted-foreground">
          <p className="flex items-center gap-2">
            <FiMapPin className="text-primary" /> {event.venue}
          </p>
          <p className="flex items-center gap-2">
            <FiCalendar className="text-primary" /> {formatDate(event.date)}
            <FiClock className="ml-1 text-primary" /> {event.time}
          </p>
        </div>

        <div className="mt-3 flex items-center justify-between">
          <span className={`text-xs font-medium ${lowSeats ? "text-destructive" : "text-muted-foreground"}`}>
            {lowSeats ? `Only ${event.seatsAvailable} seats left!` : `${event.seatsAvailable} seats available`}
          </span>
        </div>

        <div className="mt-4">
  {event.bookingOpen ? (
    <Link to="/booking/$eventId" params={{ eventId: event.id }}>
      <Button fullWidth size="sm">
        Book Tickets
      </Button>
    </Link>
  ) : (
    <Button fullWidth size="sm" disabled>
      Coming Soon
    </Button>
  )}
</div>
      </div>
    </div>
  );
}