import { createFileRoute, Link, useParams } from "@tanstack/react-router";
import { FiMapPin, FiCalendar, FiClock, FiUser, FiTag } from "react-icons/fi";
import { getEvent, formatCurrency, formatDate, EVENTS } from "@/services/mockData";
import { Button } from "@/components/Button";
import { EventCard } from "@/components/EventCard";

export const Route = createFileRoute("/events/$eventId")({
  component: EventDetails,
});

function EventDetails() {
  const { eventId } = useParams({ from: "/events/$eventId" });
  const event = getEvent(eventId);

  if (!event) {
    return (
      <div className="mx-auto max-w-3xl px-4 py-24 text-center">
        <h1 className="text-2xl font-bold">Event not found</h1>
        <Link to="/events" className="mt-4 inline-block text-primary hover:underline">
          ← Back to all events
        </Link>
      </div>
    );
  }

  const soldPct = Math.round(((event.totalSeats - event.seatsAvailable) / event.totalSeats) * 100);
  const related = EVENTS.filter((e) => e.category === event.category && e.id !== event.id).slice(0, 3);

  return (
    <div>
      <div className="relative h-[320px] w-full overflow-hidden sm:h-[420px]">
        <img src={event.image} alt={event.title} className="h-full w-full object-cover" />
        <div className="absolute inset-0 bg-gradient-to-t from-foreground/80 via-foreground/30 to-transparent" />
        <div className="absolute inset-x-0 bottom-0 mx-auto max-w-7xl px-4 pb-8 sm:px-6">
          <span className="rounded-[999px] gradient-brand px-3 py-1 text-xs font-semibold text-primary-foreground">
            {event.category}
          </span>
          <h1 className="mt-3 max-w-3xl text-3xl font-extrabold text-white sm:text-4xl">
            {event.title}
          </h1>
        </div>
      </div>

      <div className="mx-auto grid max-w-7xl gap-8 px-4 py-10 sm:px-6 lg:grid-cols-3">
        <div className="lg:col-span-2">
          <div className="grid gap-3 sm:grid-cols-2">
            {[
              { icon: <FiCalendar />, label: "Date", value: formatDate(event.date) },
              { icon: <FiClock />, label: "Time", value: event.time },
              { icon: <FiMapPin />, label: "Venue", value: `${event.venue}, ${event.city}` },
              { icon: <FiUser />, label: "Organizer", value: event.organizer },
            ].map((i) => (
              <div key={i.label} className="flex items-center gap-3 rounded-[16px] border border-border bg-card p-4 shadow-soft">
                <span className="grid h-10 w-10 place-items-center rounded-[12px] bg-secondary text-primary">{i.icon}</span>
                <div>
                  <p className="text-xs text-muted-foreground">{i.label}</p>
                  <p className="font-semibold">{i.value}</p>
                </div>
              </div>
            ))}
          </div>

          <h2 className="mt-8 text-xl font-bold">About this event</h2>
          <p className="mt-3 leading-relaxed text-muted-foreground">{event.description}</p>

          <div className="mt-6">
            <div className="flex items-center justify-between text-sm">
              <span className="font-medium">Seats filling up</span>
              <span className="text-muted-foreground">{soldPct}% booked</span>
            </div>
            <div className="mt-2 h-2.5 w-full overflow-hidden rounded-[999px] bg-muted">
              <div className="h-full gradient-brand" style={{ width: `${soldPct}%` }} />
            </div>
            <p className="mt-2 text-sm text-muted-foreground">
              {event.seatsAvailable} of {event.totalSeats} seats available
            </p>
          </div>
        </div>

        {/* Sticky booking box */}
        <aside className="lg:sticky lg:top-24 lg:self-start">
          <div className="rounded-[16px] border border-border bg-card p-6 shadow-card">
            <p className="text-sm text-muted-foreground">Starting from</p>
            <p className="flex items-center gap-2 text-3xl font-extrabold text-gradient-brand">
              <FiTag className="text-primary" /> {formatCurrency(event.price)}
            </p>
            <p className="mt-1 text-sm text-muted-foreground">per ticket</p>
            <Link to="/booking/$eventId" params={{ eventId: event.id }} className="mt-5 block">
              <Button fullWidth size="lg" disabled={event.seatsAvailable === 0}>
                {event.seatsAvailable === 0 ? "Sold Out" : "Book Now"}
              </Button>
            </Link>
            <p className="mt-3 text-center text-xs text-muted-foreground">
              Secure checkout · Instant PDF ticket
            </p>
          </div>
        </aside>
      </div>

      {related.length > 0 && (
        <section className="mx-auto max-w-7xl px-4 pb-12 sm:px-6">
          <h2 className="text-2xl font-extrabold">You may also like</h2>
          <div className="mt-6 grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
            {related.map((e) => (
              <EventCard key={e.id} event={e} />
            ))}
          </div>
        </section>
      )}
    </div>
  );
}

