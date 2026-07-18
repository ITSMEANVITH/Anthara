import type { ReactNode } from "react";

interface StatCardProps {
  label: string;
  value: string;
  icon: ReactNode;
  trend?: string;
}

export function StatCard({ label, value, icon, trend }: StatCardProps) {
  return (
    <div className="rounded-[16px] border border-border bg-card p-5 shadow-soft">
      <div className="flex items-center justify-between">
        <span className="grid h-11 w-11 place-items-center rounded-[12px] bg-secondary text-xl text-primary">
          {icon}
        </span>
        {trend && (
          <span className="rounded-[999px] bg-secondary px-2.5 py-1 text-xs font-semibold text-primary">
            {trend}
          </span>
        )}
      </div>
      <p className="mt-4 text-2xl font-extrabold">{value}</p>
      <p className="text-sm text-muted-foreground">{label}</p>
    </div>
  );
}
