import axios from "axios";

// Central Axios instance — point this at the Anthara backend base URL.
// When ready, replace the mock imports in pages with these service calls.
export const api = axios.create({
  baseURL: "http://localhost:8080/Anthara/api",
  withCredentials: true,
  headers: {
    "Content-Type": "application/x-www-form-urlencoded",
  },
});

// Attach auth token if present (backend uses JWT/session).
api.interceptors.request.use((config) => {
  if (typeof window !== "undefined") {
    const token = window.localStorage.getItem("anthara_token");
    if (token) config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// ---- Endpoint map (wire these to real backend routes) ----
export const AuthService = {
  login: (data: string) => api.post("/login", data),
 register: (data: string) =>
    api.post("/register", data),
  
  logout: () => api.post("/auth/logout"),
  me: () => api.get("/auth/me"),
};

export const EventService = {
  list: () => api.get("/events"),
  get:(id:number)=>api.get(`/events?id=${id}`)
};


export const ProfileService = {

    get: () => api.get("/profile"),

    update: (data: URLSearchParams) =>
        api.post("/profile/update", data)

};

export const WishlistService = {

    get: () =>
        api.get("/wishlist"),

    add: (eventId: number) =>
        api.post(
            "/wishlist",
            new URLSearchParams({
                eventId: eventId.toString(),
                action: "add",
            })
        ),

    remove: (eventId: number) =>
        api.post(
            "/wishlist",
            new URLSearchParams({
                eventId: eventId.toString(),
                action: "remove",
            })
        ),

};



export const ForgotPasswordService = {

  sendOtp: (data: URLSearchParams) =>
    api.post("/forgotPassword", data),

  verifyOtp: (data: URLSearchParams) =>
    api.post("/verifyOtp", data),

  resetPassword: (data: URLSearchParams) =>
    api.post("/resetPassword", data),

};


export const ContactService = {

  send: (data: URLSearchParams) =>

    axios.post(
      "http://localhost:8080/Anthara/ContactServlet",
      data,
      {
        headers: {
          "Content-Type":"application/x-www-form-urlencoded"
        }
      }
    ),

  getAll: () =>

    axios.get(
      "http://localhost:8080/Anthara/admin/contactMessages"
    )

};

export const NewsletterService = {

  subscribe: (data: URLSearchParams) =>

    axios.post(
      "http://localhost:8080/Anthara/newsletter",
      data,
      {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }
    )

};







export const BookingService = {

  create: (data: string) =>
      api.post("/bookings", data),

  mine: () =>
      api.get("/bookings/me"),

  get: (bookingId: number) =>
      api.get(`/bookings?id=${bookingId}`)

};
export const PaymentService = {

  createOrder: (bookingId: number) =>

    axios.post(
      "http://localhost:8080/Anthara/CreateOrderServlet",

      new URLSearchParams({
        bookingId: bookingId.toString()
      }),

      {
        headers: {
          "Content-Type":"application/x-www-form-urlencoded"
        }
      }
    ),

  paymentSuccess: (data: URLSearchParams) =>

    axios.post(
      "http://localhost:8080/Anthara/PaymentSuccessServlet",

      data,

      {
        headers: {
          "Content-Type":"application/x-www-form-urlencoded"
        }
      }
    )

    

};

export const AdminService = {

  analytics: () => api.get("/admin/analytics"),

  users: () => api.get("/admin/users"),

  bookings: () => api.get("/admin/bookings"),

  events: () => api.get("/admin/events"),

  contactMessages: () =>

    axios.get(
        "http://localhost:8080/Anthara/admin/contactMessages"
    ),

  getEvent: (eventId:number) =>
    api.get(`/admin/event?eventId=${eventId}`),

updateEvent: (data: FormData) =>
    api.post(
        "/admin/updateEvent",
        data,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    ),

addEvent:(data:FormData)=>

api.post(

"/admin/addEvent",

data,

{

headers:{

"Content-Type":"multipart/form-data"

}

}

),

  deleteEvent: (eventId:number) =>
    api.post(
      "/admin/deleteEvent",
      new URLSearchParams({
        eventId: eventId.toString()
      })
    ),

  me: () => api.get("/admin/me"),

};
export const AdminAuthService = {

  login: (data: URLSearchParams) =>
    api.post("/admin/login", data),

  logout: () =>
    api.post("/admin/logout"),

};
console.log("API LOADED");


