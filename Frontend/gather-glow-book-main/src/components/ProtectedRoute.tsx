import { ReactNode } from "react";
import { Navigate, useRouterState } from "@tanstack/react-router";
import { useAuth } from "@/context/AuthContext";

type Props = {
  children: ReactNode;
};

export function ProtectedRoute({ children }: Props) {
  const { user } = useAuth();
  const location = useRouterState({
    select: (state) => state.location,
  });

  if (!user) {
    return (
      <Navigate
        to="/login"
        search={{
          redirect: location.pathname,
        }}
      />
    );
  }

  return <>{children}</>;
}
