import { createFileRoute, Link } from "@tanstack/react-router";
import { FiTarget, FiHeart, FiZap } from "react-icons/fi";
import { Button } from "@/components/Button";

export const Route = createFileRoute("/about")({
  head: () => ({
    meta: [
      { title: "About — Anthara" },
      { name: "description", content: "Anthara is on a mission to make discovering and booking live events effortless." },
      { property: "og:title", content: "About Anthara" },
      { property: "og:description", content: "Making live events effortless to discover and book." },
    ],
  }),
  component: About,
});

function About() {
  return (
    <div className="mx-auto max-w-4xl px-4 py-14 sm:px-6">
      <div className="text-center">
        <h1 className="text-4xl font-extrabold">About <span className="text-gradient-brand">Anthara</span></h1>
        <p className="mx-auto mt-4 max-w-2xl text-muted-foreground">
          Anthara is a premium event booking platform built to connect people with unforgettable
          live experiences. From concerts to comedy, we make discovering and booking events simple,
          secure and delightful.
        </p>
      </div>

      <div className="mt-12 grid gap-6 sm:grid-cols-3">
        {[
          { icon: <FiTarget />, title: "Our Mission", desc: "Make live events accessible to everyone with a seamless booking experience." },
          { icon: <FiHeart />, title: "Our Values", desc: "Transparency, security and a genuine love for great experiences." },
          { icon: <FiZap />, title: "Our Promise", desc: "Instant tickets, secure payments and support whenever you need it." },
        ].map((c) => (
          <div key={c.title} className="rounded-[16px] border border-border bg-card p-6 shadow-soft">
            <span className="grid h-12 w-12 place-items-center rounded-[12px] bg-secondary text-2xl text-primary">{c.icon}</span>
            <h3 className="mt-4 text-lg font-bold">{c.title}</h3>
            <p className="mt-1 text-sm text-muted-foreground">{c.desc}</p>
          </div>
        ))}
      </div>

      <div className="mt-12 grid gap-4 rounded-[16px] gradient-brand p-8 text-center text-primary-foreground sm:grid-cols-3">
        {[
          ["4,800+", "Happy users"],
          ["120+", "Events hosted"],
          ["9,400+", "Tickets booked"],
        ].map(([n, l]) => (
          <div key={l}>
            <p className="text-3xl font-extrabold">{n}</p>
            <p className="opacity-90">{l}</p>
          </div>
        ))}
      </div>

      <div className="mt-12 text-center">
        <Link to="/events"><Button size="lg">Explore Events</Button></Link>
      </div>
    </div>
  );
}

