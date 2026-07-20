import { createFileRoute, Link, useNavigate } from "@tanstack/react-router";
import { useState } from "react";
import { FiMail, FiLock } from "react-icons/fi";
import { Button } from "@/components/Button";
import { InputField } from "@/components/InputField";
import { AuthService } from "@/services/api";
import qs from "qs";
import { useAuth } from "@/context/AuthContext";

export const Route = createFileRoute("/login")({
  head: () => ({ meta: [{ title: "Login — Anthara" }] }),
  component: Login,
});

function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const { redirect } = Route.useSearch();
  const [form, setForm] = useState({ email: "", password: "" });
  const [errors, setErrors] = useState<Record<string, string>>({});

 const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    const err: Record<string, string> = {};
    if (!/^\S+@\S+\.\S+$/.test(form.email)) err.email = "Enter a valid email";
    if (form.password.length < 6) err.password = "Password must be at least 6 characters";
    setErrors(err);
    if (Object.keys(err).length === 0) {
      try {

    const response = await AuthService.login(

        qs.stringify({

            email: form.email,
            password: form.password

        })

    );

    console.log(document.cookie);

    console.log(response.data);

    login(response.data.user);

    navigate({
  to: redirect || "/profile",
});
}
catch(error){

    alert("Invalid Email or Password");

}
    }
  };

  return (
    <div className="mx-auto flex max-w-md flex-col px-4 py-16 sm:px-6">
      <div className="rounded-[16px] border border-border bg-card p-8 shadow-card">
        <h1 className="text-2xl font-extrabold">Welcome back</h1>
        <p className="mt-1 text-sm text-muted-foreground">Log in to manage your bookings</p>

        <form onSubmit={submit} className="mt-6 space-y-4">
          <InputField
            label="Email"
            name="email"
            type="email"
            placeholder="you@example.com"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
            error={errors.email}
          />
          <InputField
            label="Password"
            name="password"
            type="password"
            placeholder="••••••••"
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })}
            error={errors.password}
          />
          <div className="flex justify-end">
            <Link to="/forgotPassword"
  className="text-sm font-medium text-primary hover:underline"
>
  Forgot Password?
</Link>
          </div>
          <Button type="submit" fullWidth size="lg" leftIcon={<FiLock />}>Log In</Button>
        </form>

        <p className="mt-6 text-center text-sm text-muted-foreground">
          Don't have an account?{" "}
          <Link to="/register" className="font-semibold text-primary hover:underline">Sign up</Link>
        </p>
      </div>
      <p className="mt-4 flex items-center justify-center gap-2 text-xs text-muted-foreground">
        <FiMail /> Secure login powered by Anthara
      </p>
    </div>
  );
}

