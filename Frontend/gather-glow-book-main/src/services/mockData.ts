// Mock data + shapes designed to mirror the Anthara backend API responses.
// Swap these arrays for Axios calls in src/services/api.ts later.

export interface AnthEvent {
  id: string;
  title: string;
  category: string;
  bookingOpen: boolean;
  image: string;
  venue: string;
  city: string;
  date: string;
  time: string;
  price: number;
  seatsAvailable: number;
  totalSeats: number;
  featured: boolean;
  organizer: string;
  description: string;
}

export interface Booking {
  id: string;
  eventId: string;
  eventTitle: string;
  date: string;
  venue: string;
  quantity: number;
  amount: number;
  status: "confirmed" | "upcoming" | "completed";
}

export const CATEGORIES = [
  "Music",
  "Concert",
  "Comedy",
  "Sports",
  "Workshop",
  "Drama",
] as const;

const img = (seed: string) =>
  `https://images.unsplash.com/${seed}?auto=format&fit=crop&w=1200&q=80`;

export const EVENTS: AnthEvent[] = [
  {
    id: "e1",
    title: "Neon Nights: Electronic Live",
    category: "Concert",
    bookingOpen: true,
    image: img("photo-1470229722913-7c0e2dbbafd3"),
    venue: "Skyline Arena",
    city: "Mumbai",
    date: "2026-08-14",
    time: "8:00 PM",
    price: 1499,
    seatsAvailable: 214,
    totalSeats: 500,
    featured: true,
    organizer: "Pulse Live Entertainment",
    description:
      "An immersive electronic music experience with world-class DJs, dynamic lighting and a state-of-the-art sound system. Expect an unforgettable night of neon-soaked energy.",
  },
  {
    id: "e2",
    title: "Stand-Up Riot",
    category: "Comedy",
    bookingOpen: true,
    image: img("photo-1516450360452-9312f5e86fc7"),
    venue: "The Laugh House",
    city: "Bengaluru",
    date: "2026-07-30",
    time: "7:30 PM",
    price: 799,
    seatsAvailable: 48,
    totalSeats: 200,
    featured: true,
    organizer: "Comic Collective",
    description:
      "The city's sharpest comedians take the stage for a night of non-stop laughter. Raw, unfiltered and hilarious.",
  },
  {
    id: "e3",
    title: "Symphony Under the Stars",
    category: "Music",
    bookingOpen: true,
    image: img("photo-1465847899084-d164df4dedc6"),
    venue: "Botanical Amphitheatre",
    city: "Pune",
    date: "2026-09-05",
    time: "6:00 PM",
    price: 1199,
    seatsAvailable: 320,
    totalSeats: 400,
    featured: true,
    organizer: "Harmony Orchestra",
    description:
      "A magical open-air evening with a full orchestra performing timeless classics beneath a starlit sky.",
  },
  {
    id: "e4",
    title: "Champions League Screening",
    category: "Sports",
    bookingOpen: true,
    image: img("photo-1522778119026-d647f0596c20"),
    venue: "Grand Sports Bar",
    city: "Delhi",
    date: "2026-08-02",
    time: "11:00 PM",
    price: 499,
    seatsAvailable: 12,
    totalSeats: 150,
    featured: false,
    organizer: "Fan Zone",
    description:
      "Watch the biggest match of the season on a giant screen with fellow fans, food and an electric atmosphere.",
  },
  {
    id: "e5",
    title: "Creative Photography Workshop",
    category: "Workshop",
    bookingOpen: true,
    image: img("photo-1452587925148-ce544e77e70d"),
    venue: "Studio Loft 22",
    city: "Hyderabad",
    date: "2026-07-25",
    time: "10:00 AM",
    price: 2499,
    seatsAvailable: 8,
    totalSeats: 25,
    featured: false,
    organizer: "Frame Academy",
    description:
      "A hands-on workshop covering composition, lighting and editing led by award-winning photographers.",
  },
  {
    id: "e6",
    title: "Hamlet: A Modern Retelling",
    category: "Drama",
    bookingOpen: true,
    image: img("photo-1503095396549-807759245b35"),
    venue: "Royal Theatre",
    city: "Kolkata",
    date: "2026-09-18",
    time: "7:00 PM",
    price: 999,
    seatsAvailable: 140,
    totalSeats: 300,
    featured: true,
    organizer: "Stagecraft Company",
    description:
      "A bold, contemporary staging of Shakespeare's masterpiece, reimagined for today's audience.",
  },
  {
    id: "e7",
    title: "Indie Fest 2026",
    category: "Music",
    bookingOpen: true,
    image: img("photo-1493225457124-a3eb161ffa5f"),
    venue: "Riverside Grounds",
    city: "Goa",
    date: "2026-10-10",
    time: "4:00 PM",
    price: 1799,
    seatsAvailable: 600,
    totalSeats: 1000,
    featured: false,
    organizer: "Wildflower Music",
    description:
      "A full day of independent artists across multiple stages, food trucks and art installations.",
  },
  {
    id: "e8",
    title: "Improv Comedy Battle",
    category: "Comedy",
    bookingOpen: false,
    image: img("photo-1585699324551-f6c309eedeca"),
    venue: "Basement Club",
    city: "Chennai",
    date: "2026-08-22",
    time: "8:30 PM",
    price: 599,
    seatsAvailable: 70,
    totalSeats: 120,
    featured: false,
    organizer: "Comic Collective",
    description:
      "Two teams. Zero scripts. Endless laughs. The audience decides the winner in this fast-paced improv showdown.",
  },
];

export const MY_BOOKINGS: Booking[] = [
  {
    id: "ANT-2026-0417",
    eventId: "e1",
    eventTitle: "Neon Nights: Electronic Live",
    date: "2026-08-14",
    venue: "Skyline Arena, Mumbai",
    quantity: 2,
    amount: 2998,
    status: "upcoming",
  },
  {
    id: "ANT-2026-0388",
    eventId: "e3",
    eventTitle: "Symphony Under the Stars",
    date: "2026-09-05",
    venue: "Botanical Amphitheatre, Pune",
    quantity: 1,
    amount: 1199,
    status: "upcoming",
  },
  {
    id: "ANT-2026-0201",
    eventId: "e2",
    eventTitle: "Stand-Up Riot",
    date: "2026-05-12",
    venue: "The Laugh House, Bengaluru",
    quantity: 3,
    amount: 2397,
    status: "completed",
  },
];

export const ADMIN_STATS = {
  totalUsers: 4820,
  totalEvents: 128,
  totalBookings: 9436,
  totalRevenue: 8940500,
};

export const MONTHLY_REVENUE = [
  { month: "Jan", revenue: 420000 },
  { month: "Feb", revenue: 510000 },
  { month: "Mar", revenue: 680000 },
  { month: "Apr", revenue: 590000 },
  { month: "May", revenue: 760000 },
  { month: "Jun", revenue: 910000 },
  { month: "Jul", revenue: 1040000 },
  { month: "Aug", revenue: 1180000 },
];

export const ADMIN_USERS = [
  { id: "u1", name: "Aarav Mehta", email: "aarav@example.com", bookings: 6, joined: "2026-01-12" },
  { id: "u2", name: "Diya Sharma", email: "diya@example.com", bookings: 3, joined: "2026-02-04" },
  { id: "u3", name: "Kabir Rao", email: "kabir@example.com", bookings: 11, joined: "2025-11-22" },
  { id: "u4", name: "Ishita Nair", email: "ishita@example.com", bookings: 2, joined: "2026-03-18" },
  { id: "u5", name: "Vivaan Gupta", email: "vivaan@example.com", bookings: 8, joined: "2025-12-30" },
];

export function formatCurrency(n: number) {
  return new Intl.NumberFormat("en-IN", {
    style: "currency",
    currency: "INR",
    maximumFractionDigits: 0,
  }).format(n);
}

export function formatDate(iso: string) {
  return new Date(iso).toLocaleDateString("en-IN", {
    weekday: "short",
    day: "numeric",
    month: "short",
    year: "numeric",
  });
}

export function getEvent(id: string) {
  return EVENTS.find((e) => e.id === id);
}
