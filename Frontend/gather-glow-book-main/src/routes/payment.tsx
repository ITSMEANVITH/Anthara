import { createFileRoute, Link, useNavigate } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { FiCreditCard, FiLock, FiCheckCircle } from "react-icons/fi";

import {
  BookingService,
  PaymentService,
} from "@/services/api";

import { Button } from "@/components/Button";
import { Loader } from "@/components/Loader";
import { loadRazorpay } from "@/lib/loadRazorpay";

export const Route = createFileRoute("/payment")({
  validateSearch: (search: Record<string, unknown>) => ({
    bookingId: Number(search.bookingId ?? 0),
  }),
  component: PaymentPage,
});

const METHODS = [
  {
    id: "card",
    label: "Credit / Debit Card",
    desc: "Visa, Mastercard, RuPay"
  },
  {
    id: "netbanking",
    label: "Net Banking",
    desc: "All major Indian banks"
  }
];
function PaymentPage() {

  const { bookingId } = Route.useSearch();

  const navigate = useNavigate();

  const [booking, setBooking] = useState<any>(null);

  const [loading, setLoading] = useState(true);

  const [processing, setProcessing] = useState(false);

  const [method, setMethod] = useState("upi");

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

  async function handlePay() {

    const loaded = await loadRazorpay();

    if (!loaded) {

      alert("Unable to load Razorpay.");

      return;

    }

    setProcessing(true);

    try {

      const orderResponse =
        await PaymentService.createOrder(
          booking.bookingId
        );

      const order = orderResponse.data;

      const options = {

        key: order.key,

        amount: order.amount,

        currency: order.currency,

        name: "Anthara",

        description: booking.title,

        order_id: order.orderId,

        prefill: {

          name: "",

          email: "",

          contact: "",

        },

        notes: {

          bookingId:
            booking.bookingId.toString(),

        },

        theme: {

          color: "#7C3AED",

        },

        handler: async function (response: any) {

          try {

            const form =
              new URLSearchParams();

            form.append(
              "bookingId",
              booking.bookingId.toString()
            );

            form.append(
              "razorpay_payment_id",
              response.razorpay_payment_id
            );

            form.append(
              "razorpay_order_id",
              response.razorpay_order_id
            );

            form.append(
              "razorpay_signature",
              response.razorpay_signature
            );

            await PaymentService.paymentSuccess(
              form
            );

            navigate({

              to: "/booking-success",

              search: {

                bookingId:
                  booking.bookingId,

              },

            });

          } catch (err) {

            console.error(err);

            alert(
              "Payment verification failed."
            );

          }

        },

        modal: {

          ondismiss: () => {

            setProcessing(false);

          },

        },

      };

      const razorpay =
        new (window as any).Razorpay(options);

      razorpay.open();

    } catch (err) {

      console.error(err);

      alert(
        "Unable to create Razorpay order."
      );

      setProcessing(false);

    }

  }

  if (loading) {

    return (
      <Loader label="Loading Booking..." />
    );

  }

  if (!booking) {

    return (

      <div className="mx-auto max-w-3xl py-20 text-center">

        <h1 className="text-2xl font-bold">

          Booking not found

        </h1>

        <Link
          to="/events"
          className="mt-5 inline-block text-primary"
        >
          Back to Events
        </Link>

      </div>

    );

  }

  if (processing) {

    return (
      <Loader label="Opening Razorpay..." />
    );

  }

  return (

    <div className="mx-auto max-w-5xl px-5 py-10">

      <h1 className="text-3xl font-bold">

        Payment

      </h1>

      <p className="mt-2 flex items-center gap-2 text-sm text-gray-500">

        <FiLock />

        Secured by Razorpay

      </p>

      <div className="mt-8 grid gap-8 lg:grid-cols-[1fr_380px]">

        <div className="rounded-xl border p-6">

          <h2 className="mb-5 text-xl font-bold">

            Payment Method

          </h2>

          <div className="space-y-3">

            {METHODS.map((m) => (

              <button

                key={m.id}

                onClick={() => setMethod(m.id)}

                className={`w-full rounded-xl border p-4 text-left ${
                  method === m.id
                    ? "border-blue-600 bg-blue-50"
                    : ""
                }`}

              >

                <div className="flex items-center justify-between">

                  <div>

                    <div className="flex items-center gap-2">

                      <FiCreditCard />

                      <span className="font-semibold">

                        {m.label}

                      </span>

                    </div>

                    <p className="text-sm text-gray-500">

                      {m.desc}

                    </p>

                  </div>

                  {method === m.id && (
                    <FiCheckCircle />
                  )}

                </div>

              </button>

            ))}

          </div>

        </div>

        <div>

          <div className="rounded-xl border p-6">

            <h2 className="text-xl font-bold">

              {booking.title}

            </h2>

            <p className="mt-3">

              {booking.venue}

            </p>

            <p>

              {booking.date}

            </p>

            <p>

              {booking.time}

            </p>

            <p className="mt-2">

              Tickets :
              {" "}
              {booking.tickets}

            </p>

            <h2 className="mt-5 text-3xl font-bold">

              ₹ {booking.amount}

            </h2>

          </div>

          <Button

            fullWidth

            className="mt-5"

            onClick={handlePay}

          >

            Pay ₹ {booking.amount}

          </Button>

        </div>

      </div>

    </div>

  );

}