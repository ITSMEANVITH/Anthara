import { createFileRoute } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { SearchBar } from "@/components/SearchBar";
import { CategoryChips } from "@/components/CategoryChips";
import { EventCard } from "@/components/EventCard";
import { EventService, WishlistService } from "@/services/api";

export const Route = createFileRoute("/events")({
  component: Events,
});

const CATEGORIES = [
  "Music",
  "Sports",
  "Comedy",
  "Food",
  "Tech"
];

function Events() {

  const [events, setEvents] = useState<any[]>([]);
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("All");
  const [sort, setSort] = useState("date");

  useEffect(() => {

    loadEvents();

  }, []);

  async function loadEvents() {

    try {

      const eventsRes = await EventService.list();
      const wishlistRes = await WishlistService.get();

      const wishlistIds = wishlistRes.data.map(
        (w: any) => w.eventId
      );

      const updatedEvents = eventsRes.data.map((e: any) => ({
        ...e,
        wishlisted: wishlistIds.includes(e.eventId),
      }));

      setEvents(updatedEvents);

    } catch (e) {

      console.error(e);

      alert("Cannot load events");

    }

  }

  let list = events.filter((e) =>

    (category === "All" || e.category === category) &&

    (
      e.title.toLowerCase().includes(search.toLowerCase()) ||
      e.venue.toLowerCase().includes(search.toLowerCase())
    )

  );

  list.sort((a, b) =>

    sort === "price"

      ? a.price - b.price

      : a.date.localeCompare(b.date)

  );


  function updateWishlist(eventId: number, wishlisted: boolean) {

    setEvents((prev) =>

      prev.map((e: any) =>

        e.eventId === eventId

          ? { ...e, wishlisted }

          : e

      )

    );

  }

  return (

    <div className="mx-auto max-w-7xl px-4 py-10">

      <h1 className="text-3xl font-bold">

        Discover Events

      </h1>

      <SearchBar

        value={search}

        onChange={setSearch}

      />

      <div className="mt-4">

        <CategoryChips

          categories={CATEGORIES}

          active={category}

          onSelect={setCategory}

        />

      </div>

      <div className="mt-4">

        <select

          value={sort}

          onChange={(e)=>setSort(e.target.value)}

        >

          <option value="date">Sort by Date</option>

          <option value="price">Sort by Price</option>

        </select>

      </div>

      <p className="mt-4">

        {list.length} events found

      </p>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">

        {

          list.map((event)=>
            <EventCard
              key={event.eventId}
              event={{
                id: event.eventId,
                title: event.title,
                venue: event.venue,
                date: event.date,
                time: event.time,
                image: event.image
                  ? `http://localhost:8080/Anthara/images/${encodeURIComponent(event.image)}`
                  : "/placeholder.jpg",
                price: event.price,
                category: event.category || "General",
                seatsAvailable: event.availableSeats,
                wishlisted: event.wishlisted,
                bookingOpen: event.bookingOpen,
              }}
              onWishlistChange={updateWishlist}
            />

          )

        }

      </div>

    </div>

  );

}