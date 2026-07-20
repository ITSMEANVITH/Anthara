import type { ReactNode } from "react";
import { FiX } from "react-icons/fi";

interface ModalProps {
  open: boolean;
  onClose: () => void;
  title?: string;
  children: ReactNode;
}

export function Modal({ open, onClose, title, children }: ModalProps) {
  if (!open) return null;
  return (
    <div
      className="fixed inset-0 z-[100] flex items-center justify-center bg-foreground/50 p-4 backdrop-blur-sm"
      onClick={onClose}
    >
      <div
        className="w-full max-w-md animate-[scale-in_0.2s_ease-out] rounded-[16px] border border-border bg-card p-6 shadow-card"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex items-start justify-between">
          {title && <h3 className="text-lg font-bold">{title}</h3>}
          <button
            onClick={onClose}
            className="grid h-8 w-8 place-items-center rounded-[12px] text-muted-foreground hover:bg-muted"
            aria-label="Close"
          >
            <FiX />
          </button>
        </div>
        <div className="mt-4">{children}</div>
      </div>
    </div>
  );
}

