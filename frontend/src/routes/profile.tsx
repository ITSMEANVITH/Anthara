import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import {
  FiMail,
  FiPhone,
  FiMapPin,
  FiSettings,
  FiDownload,
  FiLogOut,
} from "react-icons/fi";

import { BookingService, ProfileService } from "@/services/api";
import { Button } from "@/components/Button";
import { ProtectedRoute } from "@/components/ProtectedRoute";
import { useAuth } from "@/context/AuthContext";

export const Route = createFileRoute("/profile")({
  head: () => ({
    meta: [{ title: "My Profile — Anthara" }],
  }),
  component: Profile,
});

function Profile() {
  const navigate = useNavigate();
  const { logout } = useAuth();

  const [user, setUser] = useState<any>(null);
  const [bookings, setBookings] = useState<any[]>([]);

  useEffect(() => {
    loadProfile();
    loadBookings();
  }, []);

  async function loadProfile() {
    try {
      const res = await ProfileService.get();
      setUser(res.data.user);
    } catch (e) {
      console.error(e);
    }
  }

  async function loadBookings() {
    try {
      const res = await BookingService.mine();
      setBookings(res.data);
    } catch (e) {
      console.error(e);
    }
  }

  const upcoming = bookings.filter(
    (b: any) => b.status !== "COMPLETED"
  );

  const past = bookings.filter(
    (b: any) => b.status === "COMPLETED"
  );

  return (
    <ProtectedRoute>
      <div className="mx-auto max-w-5xl px-4 py-10 sm:px-6">

        <div className="grid grid-cols-[minmax(0,1fr)_auto] items-center gap-4 rounded-[16px] border border-border bg-card p-6 shadow-soft sm:flex sm:justify-between">

          <div className="flex min-w-0 items-center gap-4">
            <span className="grid h-16 w-16 shrink-0 place-items-center rounded-full gradient-brand text-2xl font-extrabold text-primary-foreground">
              {user?.fullName?.charAt(0) || "U"}
            </span>

            <div className="min-w-0">
              <h1 className="truncate text-2xl font-extrabold">
                {user?.fullName || "Loading..."}
              </h1>

              <p className="flex items-center gap-2 text-sm text-muted-foreground">
                <FiMail />
                {user?.email}
              </p>
            </div>
          </div>

          <Button
            variant="outline"
            size="sm"
            leftIcon={<FiLogOut />}
            onClick={() => {
              logout();
              navigate({ to: "/login" });
            }}
          >
            Log out
          </Button>

        </div>

        <div className="mt-6 grid gap-6 lg:grid-cols-3">

          <div className="space-y-6">

            <div className="rounded-[16px] border border-border bg-card p-6 shadow-soft">

              <h3 className="text-lg font-bold">
                Account details
              </h3>

              <div className="mt-4 space-y-3 text-sm">

                <p className="flex items-center gap-3">
                  <FiPhone className="text-primary" />
                  {user?.phone || "Not Available"}
                </p>

                <p className="flex items-center gap-3">
                  <FiMapPin className="text-primary" />
                  India
                </p>

              </div>

              <Button
                variant="secondary"
                size="sm"
                fullWidth
                leftIcon={<FiSettings />}
                className="mt-4"
                onClick={() =>
                  navigate({
                    to: "/edit-profile",
                  })
                }
              >
                Account Settings
              </Button>

            </div>

            <div className="rounded-[16px] gradient-brand p-6 text-primary-foreground shadow-soft">

              <p className="text-sm opacity-90">
                Total bookings
              </p>

              <p className="text-4xl font-extrabold">
                {bookings.length}
              </p>

              <p className="mt-1 text-sm opacity-90">
                Thanks for being with Anthara ✨
              </p>

            </div>

          </div>

          <div className="space-y-6">

            <BookingList
              title="Upcoming bookings"
              bookings={upcoming}
            />

            <BookingList
              title="Previous bookings"
              bookings={past}
              muted
            />

          </div>

        </div>

      </div>
    </ProtectedRoute>
  );
}

function BookingList({
  title,
  bookings,
  muted,
}: {
  title: string;
  bookings: any[];
  muted?: boolean;
}) {
  return (
    <div className="rounded-[16px] border border-border bg-card p-6 shadow-soft">

      <h3 className="text-lg font-bold">
        {title}
      </h3>

      {bookings.length === 0 ? (
        <p className="mt-4 text-sm text-muted-foreground">
          No bookings here yet.
        </p>
      ) : (
        <div className="mt-4 space-y-3">

          {bookings.map((b) => (
            <div
              key={b.bookingId}
              className="grid grid-cols-[minmax(0,1fr)_auto] items-center gap-3 rounded-[12px] border border-border p-4"
            >
              <div className="min-w-0">

                <p className="truncate font-semibold">
                  {b.title}
                </p>

                <p className="text-sm text-muted-foreground">
                  {b.date} · {b.venue}
                </p>

                <p className="text-sm text-muted-foreground">
                  {b.tickets} ticket(s) · ₹ {b.amount} · #{b.bookingId}
                </p>

              </div>

              <Button
                size="sm"
                variant={muted ? "outline" : "primary"}
                leftIcon={<FiDownload />}
                onClick={() =>
                  window.open(
                    `https://anthara-production.up.railway.app/DownloadTicketServlet?bookingId=${b.bookingId}`,
                    "_blank"
                  )
                }
              >
                Ticket
              </Button>

            </div>
          ))}

        </div>
      )}

    </div>
  );
}
