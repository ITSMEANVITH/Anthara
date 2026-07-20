import { createFileRoute, Link } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { FiArrowRight, FiCalendar, FiShield, FiSmartphone } from "react-icons/fi";
import { EventService } from "@/services/api";
import { CATEGORIES } from "@/services/mockData";
import { SearchBar } from "@/components/SearchBar";
import { CategoryChips } from "@/components/CategoryChips";
import { EventCard } from "@/components/EventCard";
import { Button } from "@/components/Button";

export const Route = createFileRoute("/")({
  component: Home,
});

function Home() {
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("All");
  const [events, setEvents] = useState<any[]>([]);

  useEffect(() => {
    loadEvents();
  }, []);

  async function loadEvents() {
    try {
      const res = await EventService.list();

      setEvents(
        res.data.map((e: any) => ({
          id: e.eventId,
          title: e.title,
          venue: e.venue,
          city: "",
          date: e.date,
          time: e.time,
          price: e.price,
          seatsAvailable: e.availableSeats,
          category: e.category || "Events",
          image: `https://anthara-production.up.railway.app/images/${e.image}`,
          featured: true,
          wishlisted: false,
          bookingOpen: e.bookingOpen,
          homeSection: e.homeSection, // was missing — broke the featured/upcoming filters below
        }))
      );
    } catch (e) {
      console.error(e);
    }
  }

  const filtered = events.filter(
    (e: any) =>
      (category === "All" || e.category === category) &&
      (e.title.toLowerCase().includes(search.toLowerCase()) ||
        e.venue.toLowerCase().includes(search.toLowerCase()))
  );

  const featured = filtered.filter((e) => e.homeSection === "featured");
  const upcoming = filtered.filter((e) => e.homeSection === "upcoming");

  return (
    <div>
      {/* Hero */}
      <section className="relative overflow-hidden">
        <div className="absolute inset-0 -z-10 bg-lightpurple/60" />
        <div
          className="absolute -right-24 -top-24 -z-10 h-96 w-96 rounded-full opacity-30 blur-3xl gradient-brand"
          aria-hidden
        />
        <div className="mx-auto max-w-7xl px-4 pb-10 pt-16 sm:px-6 lg:pt-24">
          <div className="mx-auto max-w-3xl text-center">
            <span className="inline-block rounded-[999px] border border-primary/30 bg-background px-4 py-1.5 text-sm font-medium text-primary">
              ✨ India's premium event booking platform
            </span>
            <h1 className="mt-5 text-4xl font-extrabold leading-tight sm:text-5xl lg:text-6xl">
              Book unforgettable <span className="text-gradient-brand">live experiences</span>
            </h1>
            <p className="mx-auto mt-5 max-w-xl text-base text-muted-foreground sm:text-lg">
              From electric concerts to laugh-out-loud comedy nights — discover, book and enjoy
              the best events near you, all in a few taps.
            </p>
            <div className="mt-7 flex flex-wrap justify-center gap-3">
              <Link to="/events">
                <Button size="lg" leftIcon={<FiArrowRight />}>
                  Explore Events
                </Button>
              </Link>
              <Link to="/about">
                <Button size="lg" variant="outline">
                  Learn More
                </Button>
              </Link>
            </div>
          </div>

          <div className="mx-auto mt-10 max-w-2xl">
            <SearchBar value={search} onChange={setSearch} />
            <div className="mt-4 flex justify-center">
              <CategoryChips categories={CATEGORIES} active={category} onSelect={setCategory} />
            </div>
          </div>
        </div>
      </section>

      {/* Featured */}
      <section className="mx-auto max-w-7xl px-4 py-12 sm:px-6">
        <div className="flex items-end justify-between">
          <div>
            <h2 className="text-2xl font-extrabold sm:text-3xl">Featured Events</h2>
            <p className="mt-1 text-muted-foreground">
              Handpicked experiences you won't want to miss
            </p>
          </div>
          <Link
            to="/events"
            className="hidden text-sm font-semibold text-primary hover:underline sm:block"
          >
            View all →
          </Link>
        </div>
        <div className="mt-6 grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {featured.map((e) => (
            <EventCard key={e.id} event={e} />
          ))}
        </div>
      </section>

      {/* Upcoming */}
      <section className="mx-auto max-w-7xl px-4 py-4 sm:px-6">
        <div className="flex items-end justify-between">
          <h2 className="text-2xl font-extrabold sm:text-3xl">Upcoming Events</h2>
          <Link
            to="/events"
            className="hidden text-sm font-semibold text-primary hover:underline sm:block"
          >
            View all →
          </Link>
        </div>
        <div className="mt-6 grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {upcoming.map((e) => (
            <EventCard key={e.id} event={e} />
          ))}
        </div>
      </section>

      {/* Why Anthara */}
      <section className="mx-auto max-w-7xl px-4 py-16 sm:px-6">
        <div className="grid gap-6 md:grid-cols-3">
          {[
            {
              icon: <FiCalendar />,
              title: "Seamless Booking",
              desc: "Reserve your seats in seconds with a smooth, secure checkout.",
            },
            {
              icon: <FiShield />,
              title: "Secure Payments",
              desc: "Razorpay-powered payments with signature verification.",
            },
            {
              icon: <FiSmartphone />,
              title: "Instant Tickets",
              desc: "Get a PDF ticket with QR code and a calendar invite by email.",
            },
          ].map((f) => (
            <div key={f.title} className="rounded-[16px] border border-border bg-card p-6 shadow-soft">
              <span className="grid h-12 w-12 place-items-center rounded-[12px] bg-secondary text-2xl text-primary">
                {f.icon}
              </span>
              <h3 className="mt-4 text-lg font-bold">{f.title}</h3>
              <p className="mt-1 text-sm text-muted-foreground">{f.desc}</p>
            </div>
          ))}
        </div>
      </section>

      {/* CTA */}
      <section className="mx-auto max-w-7xl px-4 pb-8 sm:px-6">
        <div className="overflow-hidden rounded-[16px] gradient-brand px-6 py-12 text-center text-primary-foreground sm:px-12">
          <h2 className="text-2xl font-extrabold sm:text-3xl">Ready for your next experience?</h2>
          <p className="mx-auto mt-2 max-w-lg opacity-90">
            Join thousands of event-goers booking with Anthara every day.
          </p>
          <div className="mt-6 flex justify-center">
            <Link to="/events">
              <Button size="lg" variant="secondary">
                Browse Events
              </Button>
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
}
