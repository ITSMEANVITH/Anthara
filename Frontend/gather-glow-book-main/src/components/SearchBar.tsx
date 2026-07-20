import { FiSearch, FiSliders } from "react-icons/fi";

interface SearchBarProps {
  value: string;
  onChange: (v: string) => void;
  onFilterClick?: () => void;
  placeholder?: string;
}

export function SearchBar({ value, onChange, onFilterClick, placeholder }: SearchBarProps) {
  return (
    <div className="flex w-full items-center gap-2">
      <div className="relative flex-1">
        <FiSearch className="pointer-events-none absolute left-4 top-1/2 -translate-y-1/2 text-muted-foreground" />
        <input
          value={value}
          onChange={(e) => onChange(e.target.value)}
          placeholder={placeholder || "Search events, artists, venues..."}
          className="w-full rounded-[12px] border border-input bg-background py-3.5 pl-11 pr-4 text-sm outline-none transition-all duration-300 focus:border-primary focus:ring-2 focus:ring-primary/20"
        />
      </div>
      <button
        onClick={onFilterClick}
        className="grid h-[50px] w-[50px] shrink-0 place-items-center rounded-[12px] border border-input bg-background text-foreground transition-colors hover:border-primary hover:text-primary"
        aria-label="Filters"
      >
        <FiSliders />
      </button>
    </div>
  );
}

