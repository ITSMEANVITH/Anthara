import { createFileRoute } from "@tanstack/react-router";
import { useState } from "react";
import { FiMail, FiMapPin, FiSend } from "react-icons/fi";
import { Button } from "@/components/Button";
import { ContactService } from "@/services/api";
import { InputField } from "@/components/InputField";
import { Modal } from "@/components/Modal";


export const Route = createFileRoute("/contact")({
  head: () => ({
    meta: [
      { title: "Contact — Anthara" },
      {
        name: "description",
        content:
          "Get in touch with the Anthara team for support and partnership enquiries.",
      },
      { property: "og:title", content: "Contact Anthara" },
      {
        property: "og:description",
        content: "Reach the Anthara team for support and partnerships.",
      },
    ],
  }),
  component: Contact,
});

function Contact() {
  const [sent, setSent] = useState(false);
const [loading, setLoading] = useState(false);

  return (
    <div className="mx-auto max-w-5xl px-4 py-14 sm:px-6">
      <div className="text-center">
        <h1 className="text-4xl font-extrabold">Get in touch</h1>
        <p className="mt-3 text-muted-foreground">
          We'd love to hear from you. Reach out anytime.
        </p>
      </div>

      <div className="mt-10 grid gap-8 lg:grid-cols-[1fr_1.2fr]">
        {/* Contact Information */}
        <div className="space-y-4">
          {[
            {
              icon: <FiMail />,
              label: "Email",
              value: "antharaevents@gmail.com",
            },
            {
              icon: <FiMapPin />,
              label: "Office",
              value: "Electronic City, Bengaluru",
            },
          ].map((c) => (
            <div
              key={c.label}
              className="flex items-center gap-4 rounded-[16px] border border-border bg-card p-5 shadow-soft"
            >
              <span className="grid h-11 w-11 place-items-center rounded-[12px] bg-secondary text-primary">
                {c.icon}
              </span>

              <div>
                <p className="text-xs text-muted-foreground">{c.label}</p>
                <p className="font-semibold">{c.value}</p>
              </div>
            </div>
          ))}
        </div>

        {/* Contact Form */}
        <form
        onSubmit={async (e) => {

    e.preventDefault();

    const formElement = e.currentTarget;

    setLoading(true);

    try {

        const form = new FormData(formElement);

        const data = new URLSearchParams();

        data.append("name", form.get("name") as string);
        data.append("email", form.get("email") as string);
        data.append("subject", form.get("subject") as string);
        data.append("message", form.get("message") as string);

        await ContactService.send(data);

        setSent(true);

        formElement.reset();

    } catch (error) {

        console.log(error);
        alert("Failed to send message.");

    } finally {

        setLoading(false);

    }

}}
          className="rounded-[16px] border border-border bg-card p-6 shadow-soft"
        >
          <div className="grid gap-4 sm:grid-cols-2">
            <InputField
              label="Name"
              name="name"
              placeholder="Your name"
              required
            />

            <InputField
              label="Email"
              name="email"
              type="email"
              placeholder="you@example.com"
              required
            />
          </div>

          <InputField
            label="Subject"
            name="subject"
            placeholder="How can we help?"
            className="mt-4"
            required
          />

          <div className="mt-4">
            <label className="mb-1.5 block text-sm font-medium">
              Message
            </label>

            <textarea
    name="message"
              required
              rows={5}
              placeholder="Write your message..."
              className="w-full rounded-[12px] border border-input bg-background px-4 py-3 text-sm outline-none transition-all focus:border-primary focus:ring-2 focus:ring-primary/20"
            />
          </div>

          <Button
    type="submit"
    size="lg"
    fullWidth
    leftIcon={<FiSend />}
    className="mt-5"
    disabled={loading}
>
    {loading ? "Sending..." : "Send Message"}
</Button>
        </form>
      </div>

      <Modal
        open={sent}
        onClose={() => setSent(false)}
        title="Message sent!"
      >
        <p className="text-sm text-muted-foreground">
          Thanks for reaching out. Our team will get back to you within 24
          hours.
        </p>

        <Button
          className="mt-4"
          fullWidth
          onClick={() => setSent(false)}
        >
          Done
        </Button>
      </Modal>
    </div>
  );
}