import { useEffect, useState } from "react";
import { Link } from "@tanstack/react-router";
import { FiMenu, FiX, FiUser, FiHeart } from "react-icons/fi";
import { Button } from "./Button";
import antharaLogo from "../assets/logos/anthara-logo.png";
import { useAuth } from "@/context/AuthContext";

const links = [
  { to: "/", label: "Home" },
  { to: "/events", label: "Events" },
  { to: "/about", label: "About" },
  { to: "/contact", label: "Contact" },
] as const;

export function Navbar() {
  const [open, setOpen] = useState(false);
 const { user, logout } = useAuth();

const isLoggedIn = !!user;

 const handleLogout = () => {
    logout();
    window.location.href="/";
};
 
  return (
    <header className="sticky top-0 z-50 border-b border-border/70 bg-background/85 backdrop-blur-md">
      <nav className="mx-auto flex max-w-7xl items-center justify-between gap-4 px-4 py-3.5 sm:px-6">
        <Link
          to="/"
          className="flex items-center"
          onClick={() => setOpen(false)}
        >
          <img
            src={antharaLogo}
            alt="Anthara Logo"
            className="h-14 w-auto object-contain transition-transform duration-300 hover:scale-105"
          />
        </Link>

        {/* Desktop Navigation */}
        <div className="hidden items-center gap-1 md:flex">
          {links.map((l) => (
            <Link
              key={l.to}
              to={l.to}
              className="rounded-[12px] px-3.5 py-2 text-sm font-medium text-muted-foreground transition-colors hover:bg-muted hover:text-foreground"
              activeProps={{ className: "text-primary bg-secondary" }}
              activeOptions={{ exact: l.to === "/" }}
            >
              {l.label}
            </Link>
          ))}
        </div>

        {/* Desktop Right Side */}
        <div className="hidden items-center gap-2 md:flex">
          <Link to="/wishlist">
            <Button variant="ghost" size="sm" leftIcon={<FiHeart />}>
              Wishlist
            </Button>
          </Link>

          <Link to="/profile">
            <Button variant="ghost" size="sm" leftIcon={<FiUser />}>
              Profile
            </Button>
          </Link>

          {!isLoggedIn ? (
            <>
              <Link to="/login">
                <Button variant="outline" size="sm">
                  Login
                </Button>
              </Link>

              <Link to="/register">
                <Button size="sm">Sign Up</Button>
              </Link>
            </>
          ) : (
            <Button variant="outline" size="sm" onClick={handleLogout}>
  Logout
</Button>
          )}
        </div>

        {/* Mobile Menu Button */}
        <button
          className="grid h-10 w-10 place-items-center rounded-[12px] text-foreground hover:bg-muted md:hidden"
          onClick={() => setOpen((v) => !v)}
          aria-label="Toggle menu"
        >
          {open ? <FiX size={22} /> : <FiMenu size={22} />}
        </button>
      </nav>

      {/* Mobile Menu */}
      {open && (
        <div className="border-t border-border bg-background px-4 py-3 md:hidden">
          <div className="flex flex-col gap-1">
            {links.map((l) => (
              <Link
                key={l.to}
                to={l.to}
                onClick={() => setOpen(false)}
                className="rounded-[12px] px-3 py-2.5 text-sm font-medium text-muted-foreground hover:bg-muted"
                activeProps={{ className: "text-primary bg-secondary" }}
                activeOptions={{ exact: l.to === "/" }}
              >
                {l.label}
              </Link>
            ))}

            <Link to="/wishlist" onClick={() => setOpen(false)}>
              <Button
                variant="ghost"
                size="sm"
                fullWidth
                leftIcon={<FiHeart />}
              >
                Wishlist
              </Button>
            </Link>

            <Link to="/profile" onClick={() => setOpen(false)}>
              <Button
                variant="ghost"
                size="sm"
                fullWidth
                leftIcon={<FiUser />}
              >
                Profile
              </Button>
            </Link>

            <div className="mt-2 flex gap-2">
              {!isLoggedIn ? (
                <>
                  <Link
                    to="/login"
                    className="flex-1"
                    onClick={() => setOpen(false)}
                  >
                    <Button variant="outline" size="sm" fullWidth>
                      Login
                    </Button>
                  </Link>

                  <Link
                    to="/register"
                    className="flex-1"
                    onClick={() => setOpen(false)}
                  >
                    <Button size="sm" fullWidth>
                      Sign Up
                    </Button>
                  </Link>
                </>
              ) : (
                <Button
                  variant="outline"
                  size="sm"
                  fullWidth
                  onClick={handleLogout}
                >
                  Logout
                </Button>
              )}
            </div>
          </div>
        </div>
      )}
    </header>
  );
}
