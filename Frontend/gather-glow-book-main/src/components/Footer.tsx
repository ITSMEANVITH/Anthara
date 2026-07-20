import { Link } from "@tanstack/react-router";
import { useState } from "react";
import { NewsletterService } from "@/services/api";
import { FiInstagram, FiTwitter, FiFacebook, FiMail } from "react-icons/fi";
import antharaLogo from "../assets/logos/anthara-logo.png";

export function Footer() {
  const [email, setEmail] = useState("");
  async function subscribe() {

    if (!email.trim()) {

        alert("Please enter your email.");

        return;

    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(email)) {

        alert("Please enter a valid email address.");

        return;

    }

    try {

        const data = new URLSearchParams();

        data.append("email", email);

        const res = await NewsletterService.subscribe(data);

        alert(res.data.message);

        setEmail("");

    }

    catch (err: any) {

        console.error(err);

        if (err.response?.data?.message) {

            alert(err.response.data.message);

        } else {

            alert("Subscription failed.");

        }

    }

}
  return (
    <footer className="mt-20 border-t border-border bg-muted/40">
      <div className="mx-auto grid max-w-7xl gap-10 px-4 py-12 sm:px-6 md:grid-cols-4">
        <div>
          <div className="flex flex-col items-start">
  <img
    src={antharaLogo}
    alt="Anthara"
    className="h-14 w-auto"
  />

  <p className="mt-2 text-sm text-muted-foreground">
    Discover • Book • Experience unforgettable events.
  </p>
</div>
         <p className="mt-4 text-sm leading-6 text-muted-foreground">
  India's premium destination for discovering and booking concerts, comedy shows,
  sports events, workshops and unforgettable experiences.
</p>
          <div className="mt-4 flex gap-2">
            {[FiInstagram, FiTwitter, FiFacebook, FiMail].map((Icon, i) => (
              <a
                key={i}
                href="#"
                className="grid h-9 w-9 place-items-center rounded-[12px] border border-border bg-background text-muted-foreground transition-colors hover:border-primary hover:text-primary"
                aria-label="social"
              >
                <Icon />
              </a>
            ))}
          </div>
        </div>

        <div>
          <h4 className="text-sm font-semibold">Explore</h4>
          <ul className="mt-3 space-y-2 text-sm text-muted-foreground">
            <li><Link to="/events" className="hover:text-primary">All Events</Link></li>
            <li><Link to="/" className="hover:text-primary">Featured</Link></li>
            <li><Link to="/my-bookings" className="hover:text-primary">My Bookings</Link></li>
          </ul>
        </div>

        <div>
          <h4 className="text-sm font-semibold">Company</h4>
          <ul className="mt-3 space-y-2 text-sm text-muted-foreground">
            <li><Link to="/about" className="hover:text-primary">About</Link></li>
            <li><Link to="/contact" className="hover:text-primary">Contact</Link></li>
            
          </ul>
        </div>

        <div>
          <h4 className="text-sm font-semibold">Stay in the loop</h4>
          <p className="mt-3 text-sm text-muted-foreground">
            Get event updates and exclusive offers.
          </p>
          <div className="mt-3 flex gap-2">
           <input
  placeholder="Email address"
  value={email}
  onChange={(e) => setEmail(e.target.value)}
  className="w-full rounded-[12px] border border-input bg-background px-3 py-2.5 text-sm outline-none focus:border-primary"
/>
            <button
    onClick={subscribe}
    className="rounded-[12px] gradient-brand px-4 text-sm font-semibold text-primary-foreground"
>
    Join
</button>
          </div>
        </div>
      </div>
      <div className="border-t border-border py-5 text-center text-xs text-muted-foreground">
        © {new Date().getFullYear()} Anthara. All rights reserved.
      </div>
    </footer>
  );
}

