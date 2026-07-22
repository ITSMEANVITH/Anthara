import type { ReactNode } from "react";

export interface Column<T> {
  header: string;
  accessor: (row: T) => ReactNode;
  align?: "left" | "right" | "center";
}

interface DataTableProps<T> {
  columns: Column<T>[];
  data: T[];
  emptyLabel?: string;
}

export function DataTable<T>({ columns, data, emptyLabel }: DataTableProps<T>) {
  return (
    <div className="overflow-x-auto rounded-[16px] border border-border bg-card shadow-soft">
      <table className="w-full min-w-[560px] text-sm">
        <thead>
          <tr className="border-b border-border bg-muted/50 text-left">
            {columns.map((c, i) => (
              <th
                key={i}
                className={`px-4 py-3 font-semibold text-muted-foreground ${
                  c.align === "right" ? "text-right" : c.align === "center" ? "text-center" : ""
                }`}
              >
                {c.header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.length === 0 ? (
            <tr>
              <td colSpan={columns.length} className="px-4 py-8 text-center text-muted-foreground">
                {emptyLabel || "No records found"}
              </td>
            </tr>
          ) : (
            data.map((row, ri) => (
              <tr key={ri} className="border-b border-border/60 transition-colors last:border-0 hover:bg-muted/40">
                {columns.map((c, ci) => (
                  <td
                    key={ci}
                    className={`px-4 py-3 ${
                      c.align === "right" ? "text-right" : c.align === "center" ? "text-center" : ""
                    }`}
                  >
                    {c.accessor(row)}
                  </td>
                ))}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

