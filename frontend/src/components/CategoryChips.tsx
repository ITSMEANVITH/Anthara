import { cn } from "@/lib/utils";

interface CategoryChipsProps {
  categories: readonly string[];
  active: string;
  onSelect: (c: string) => void;
  includeAll?: boolean;
}

export function CategoryChips({ categories, active, onSelect, includeAll = true }: CategoryChipsProps) {
  const list = includeAll ? [ ...categories] : [...categories];
  return (
    <div className="flex flex-wrap gap-2">
      {list.map((c) => (
        <button
          key={c}
          onClick={() => onSelect(c)}
          className={cn(
            "rounded-[999px] border px-4 py-2 text-sm font-medium transition-all duration-300",
            active === c
              ? "gradient-brand border-transparent text-primary-foreground shadow-soft"
              : "border-border bg-background text-muted-foreground hover:border-primary hover:text-primary",
          )}
        >
          {c}
        </button>
      ))}
    </div>
  );
}

