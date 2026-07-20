import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useRef, useState } from "react";
import { Button } from "@/components/Button";
import { ForgotPasswordService } from "@/services/api";

export const Route = createFileRoute("/verifyOtp")({
  component: VerifyOtp,
});

function VerifyOtp() {

  const navigate = useNavigate();

  const [otp, setOtp] = useState(["", "", "", "", "", ""]);

  const [loading, setLoading] = useState(false);

  const inputs = useRef<(HTMLInputElement | null)[]>([]);

  function handleChange(value: string, index: number) {

    if (!/^\d?$/.test(value)) return;

    const newOtp = [...otp];

    newOtp[index] = value;

    setOtp(newOtp);

    if (value && index < 5) {

      inputs.current[index + 1]?.focus();

    }

  }

  function handleKeyDown(
    e: React.KeyboardEvent<HTMLInputElement>,
    index: number
  ) {

    if (e.key === "Backspace" && !otp[index] && index > 0) {

      inputs.current[index - 1]?.focus();

    }

  }

  function handlePaste(e: React.ClipboardEvent<HTMLInputElement>) {

    e.preventDefault();

    const pasted = e.clipboardData.getData("text").trim();

    if (!/^\d{6}$/.test(pasted)) return;

    const digits = pasted.split("");

    setOtp(digits);

    digits.forEach((d, i) => {

      if (inputs.current[i]) {

        inputs.current[i]!.value = d;

      }

    });

    inputs.current[5]?.focus();

  }

  async function verify(e: React.FormEvent) {

    e.preventDefault();

    const finalOtp = otp.join("");

    if (finalOtp.length !== 6) {

      alert("Enter 6 digit OTP");

      return;

    }

    try {

      setLoading(true);

      await ForgotPasswordService.verifyOtp(

        new URLSearchParams({

          otp: finalOtp,

        })

      );

      alert("OTP Verified Successfully");

      navigate({

        to: "/resetPassword",

      });

    } catch (err: any) {

      console.error(err);

      alert(

        err.response?.data?.message ||

        "Invalid OTP"

      );

    } finally {

      setLoading(false);

    }

  }

  return (

    <div className="mx-auto flex max-w-md flex-col px-4 py-16">

      <div className="rounded-2xl border bg-card p-8 shadow-card">

        <h1 className="text-3xl font-bold text-center">

          Verify OTP

        </h1>

        <p className="mt-2 text-center text-muted-foreground">

          Enter the 6-digit OTP sent to your email.

        </p>

        <form onSubmit={verify} className="mt-8">

          <div className="flex justify-between gap-2">

            {otp.map((digit, index) => (

              <input
                key={index}
                ref={(el) => {
                  inputs.current[index] = el;
                }}
                type="text"
                maxLength={1}
                value={digit}
                onPaste={handlePaste}
                onChange={(e) =>
                  handleChange(e.target.value, index)
                }
                onKeyDown={(e) =>
                  handleKeyDown(e, index)
                }
                className="h-14 w-14 rounded-xl border text-center text-2xl font-bold outline-none focus:border-primary focus:ring-2 focus:ring-primary"
              />

            ))}

          </div>

          <Button
            type="submit"
            fullWidth
            size="lg"
            className="mt-8"
            disabled={loading}
          >
            {loading ? "Verifying..." : "Verify OTP"}
          </Button>

        </form>

      </div>

    </div>

  );

}
