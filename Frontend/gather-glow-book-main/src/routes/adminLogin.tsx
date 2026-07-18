import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useState } from "react";
import { FiLock } from "react-icons/fi";
import { InputField } from "@/components/InputField";
import { Button } from "@/components/Button";
import { AdminAuthService } from "@/services/api";

export const Route = createFileRoute("/adminLogin")({
  component: AdminLogin,
});

function AdminLogin() {

  const navigate = useNavigate();

  const [username, setUsername] = useState("");

  const [password, setPassword] = useState("");

  const [loading, setLoading] = useState(false);

  async function login(e: React.FormEvent) {

    e.preventDefault();

    try {

      setLoading(true);

      await AdminAuthService.login(

        new URLSearchParams({

          username,

          password,

        })

      );

      alert("Admin Login Successful");

      navigate({

        to: "/admin",

      });

    }

    catch (err) {

      console.error(err);

      alert("Invalid Username or Password");

    }

    finally {

      setLoading(false);

    }

  }

  return (

    <div className="mx-auto flex max-w-md flex-col px-4 py-20">

      <div className="rounded-[16px] border border-border bg-card p-8 shadow-card">

        <h1 className="text-center text-3xl font-extrabold">

          ANTHARA ADMIN

        </h1>

        <form onSubmit={login} className="mt-8 space-y-5">

          <InputField
            label="Username"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />

          <InputField
            label="Password"
            name="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <Button
            type="submit"
            fullWidth
            size="lg"
            leftIcon={<FiLock />}
            disabled={loading}
          >

            {loading ? "Logging In..." : "Login"}

          </Button>

        </form>

      </div>

    </div>

  );

}