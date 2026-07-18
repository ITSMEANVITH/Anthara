import { createFileRoute, Link, useNavigate } from "@tanstack/react-router";
import { useState } from "react";
import { FiMail } from "react-icons/fi";
import { Button } from "@/components/Button";
import { InputField } from "@/components/InputField";
import { ForgotPasswordService } from "@/services/api";


export const Route = createFileRoute("/forgotPassword")({
  head: () => ({
    meta: [{ title: "Forgot Password — Anthara" }],
  }),
  component: ForgotPassword,
});

function ForgotPassword() {

  const navigate = useNavigate();

  const [email, setEmail] = useState("");

  const [loading, setLoading] = useState(false);

  async function sendOtp(e: React.FormEvent) {

    e.preventDefault();
    

    try {

      setLoading(true);

      await ForgotPasswordService.sendOtp(

        new URLSearchParams({

          email,

        })

      );

      alert("OTP has been sent to your email.");

      navigate({
  to: "/verifyOtp",
});

    } catch (e) {

      console.error(e);

      alert("Unable to send OTP.");

    } finally {

      setLoading(false);

    }

  }

  return (

    <div className="mx-auto flex max-w-md flex-col px-4 py-16 sm:px-6">

      <div className="rounded-[16px] border border-border bg-card p-8 shadow-card">

        <h1 className="text-2xl font-extrabold">

          Forgot Password

        </h1>

        <p className="mt-2 text-sm text-muted-foreground">

          Enter your registered email to receive an OTP.

        </p>

        <form onSubmit={sendOtp} className="mt-6 space-y-4">

          <InputField
            label="Email"
            name="email"
            type="email"
            placeholder="you@example.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <Button
            type="submit"
            fullWidth
            size="lg"
            leftIcon={<FiMail />}
            disabled={loading}
          >
            {loading ? "Sending..." : "Send OTP"}
          </Button>

        </form>

        <p className="mt-6 text-center text-sm">

          <Link
            to="/login"
            className="font-semibold text-primary hover:underline"
          >
            Back to Login
          </Link>

        </p>

      </div>

    </div>

  );

}