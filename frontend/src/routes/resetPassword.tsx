import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useState } from "react";
import { Button } from "@/components/Button";
import { InputField } from "@/components/InputField";
import { ForgotPasswordService } from "@/services/api";

export const Route = createFileRoute("/resetPassword")({
  component: ResetPassword,
});

function ResetPassword() {

  const navigate = useNavigate();

  const [password, setPassword] = useState("");

  const [confirmPassword, setConfirmPassword] = useState("");

  const [loading, setLoading] = useState(false);

  async function reset(e: React.FormEvent) {

    e.preventDefault();

    if (password !== confirmPassword) {

      alert("Passwords do not match");

      return;

    }

    try {

      setLoading(true);

      await ForgotPasswordService.resetPassword(

        new URLSearchParams({

          password,

          confirmPassword,

        })

      );

      alert("Password Reset Successfully");

      navigate({

        to: "/login",

      });

    } catch (err) {

      console.error(err);

      alert("Unable to reset password");

    } finally {

      setLoading(false);

    }

  }

  return (

    <div className="mx-auto flex max-w-md flex-col px-4 py-16 sm:px-6">

      <div className="rounded-[16px] border border-border bg-card p-8 shadow-card">

        <h1 className="text-2xl font-extrabold">
          Reset Password
        </h1>

        <p className="mt-2 text-sm text-muted-foreground">
          Enter your new password.
        </p>

        <form onSubmit={reset} className="mt-6 space-y-4">

          <InputField
            label="New Password"
            name="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <InputField
            label="Confirm Password"
            name="confirmPassword"
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />

          <Button
            type="submit"
            fullWidth
            size="lg"
            disabled={loading}
          >
            {loading ? "Updating..." : "Reset Password"}
          </Button>

        </form>

      </div>

    </div>

  );

}
