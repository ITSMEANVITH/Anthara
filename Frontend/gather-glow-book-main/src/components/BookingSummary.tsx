import { type AnthEvent, formatCurrency, formatDate } from "@/services/mockData";

interface BookingSummaryProps {
  event: AnthEvent;
  quantity: number;
  fee?: number;
}

export function BookingSummary({ event, quantity, fee = 49 }: BookingSummaryProps) {
  const subtotal = event.price * quantity;
  const total = subtotal + fee;
  return (
    <div className="rounded-[16px] border border-border bg-card p-5 shadow-soft">
      <h3 className="text-lg font-bold">Booking Summary</h3>
      <div className="mt-4 flex gap-3">
        <img src={event.image} alt={event.title} className="h-16 w-16 rounded-[12px] object-cover" />
        <div className="min-w-0">
          <p className="line-clamp-1 font-semibold">{event.title}</p>
          <p className="text-sm text-muted-foreground">{formatDate(event.date)} · {event.time}</p>
          <p className="text-sm text-muted-foreground">{event.venue}, {event.city}</p>
        </div>
      </div>

      <div className="mt-4 space-y-2 border-t border-border pt-4 text-sm">
        <div className="flex justify-between">
          <span className="text-muted-foreground">
            {formatCurrency(event.price)} × {quantity}
          </span>
          <span className="font-medium">{formatCurrency(subtotal)}</span>
        </div>
        <div className="flex justify-between">
          <span className="text-muted-foreground">Convenience fee</span>
          <span className="font-medium">{formatCurrency(fee)}</span>
        </div>
      </div>

      <div className="mt-3 flex items-center justify-between border-t border-border pt-3">
        <span className="font-semibold">Total</span>
        <span className="text-xl font-extrabold text-gradient-brand">{formatCurrency(total)}</span>
      </div>
    </div>
  );
}

