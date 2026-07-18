import { createFileRoute, Link, useNavigate } from "@tanstack/react-router";
import { useState } from "react";
import { FiUserPlus } from "react-icons/fi";
import { Button } from "@/components/Button";
import { InputField } from "@/components/InputField";
import { AuthService } from "@/services/api";
import qs from "qs";

export const Route = createFileRoute("/register")({
  head: () => ({
    meta: [{ title: "Create Account — Anthara" }],
  }),
  component: Register,
});

function Register() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    confirm: "",
  });

  const [errors, setErrors] = useState<Record<string, string>>({});

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();

    const err: Record<string, string> = {};

    if (form.name.trim().length < 2)
      err.name = "Enter your full name";

    if (!/^\S+@\S+\.\S+$/.test(form.email))
      err.email = "Enter a valid email";

    if (form.password.length < 6)
      err.password = "Password must be at least 6 characters";

    if (form.confirm !== form.password)
      err.confirm = "Passwords do not match";

    setErrors(err);

    if (Object.keys(err).length === 0) {
      try {
        await AuthService.register(
          qs.stringify({
            fullname: form.name,
            email: form.email,
            phone: "",
            password: form.password,
          })
        );

        alert("Registration Successful!");

        navigate({
          to: "/login",
        });
      } catch (error) {
        console.error(error);
        alert("Registration Failed");
      }
    }
  };

  return (
    <div className="mx-auto flex max-w-md flex-col px-4 py-16 sm:px-6">
      <div className="rounded-[16px] border border-border bg-card p-8 shadow-card">
        <h1 className="text-2xl font-extrabold">
          Create your account
        </h1>

        <p className="mt-1 text-sm text-muted-foreground">
          Join Anthara and start booking events
        </p>

        <form onSubmit={submit} className="mt-6 space-y-4">

          <InputField
            label="Full name"
            name="name"
            placeholder="Your name"
            value={form.name}
            onChange={(e) =>
              setForm({
                ...form,
                name: e.target.value,
              })
            }
            error={errors.name}
          />

          <InputField
            label="Email"
            name="email"
            type="email"
            placeholder="you@example.com"
            value={form.email}
            onChange={(e) =>
              setForm({
                ...form,
                email: e.target.value,
              })
            }
            error={errors.email}
          />

          <InputField
            label="Password"
            name="password"
            type="password"
            placeholder="••••••••"
            value={form.password}
            onChange={(e) =>
              setForm({
                ...form,
                password: e.target.value,
              })
            }
            error={errors.password}
          />

          <InputField
            label="Confirm password"
            name="confirm"
            type="password"
            placeholder="••••••••"
            value={form.confirm}
            onChange={(e) =>
              setForm({
                ...form,
                confirm: e.target.value,
              })
            }
            error={errors.confirm}
          />

          <Button
            type="submit"
            fullWidth
            size="lg"
            leftIcon={<FiUserPlus />}
          >
            Create Account
          </Button>

        </form>

        <p className="mt-6 text-center text-sm text-muted-foreground">
          Already have an account?{" "}
          <Link
            to="/login"
            className="font-semibold text-primary hover:underline"
          >
            Log in
          </Link>
        </p>
      </div>
    </div>
  );
}