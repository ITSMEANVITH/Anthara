import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { FiSave, FiArrowLeft, FiUser, FiPhone } from "react-icons/fi";
import { ProfileService } from "@/services/api";
import { Button } from "@/components/Button";
import { Loader } from "@/components/Loader";

export const Route = createFileRoute("/edit-profile")({
  component: EditProfile,
});

function EditProfile() {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  const [fullName, setFullName] = useState("");
  const [phone, setPhone] = useState("");

  useEffect(() => {
    loadProfile();
  }, []);

  async function loadProfile() {
    try {
      const res = await ProfileService.get();

      setFullName(res.data.user.fullName);
      setPhone(res.data.user.phone ?? "");
    } catch (e) {
      console.error(e);
      alert("Unable to load profile.");
    } finally {
      setLoading(false);
    }
  }

  async function saveProfile() {
    if (fullName.trim() === "") {
      alert("Please enter your full name.");
      return;
    }

    setSaving(true);

    try {
      const data = new URLSearchParams();

      data.append("fullName", fullName);
      data.append("phone", phone);

      const res = await ProfileService.update(data);

      if (res.data.success) {
        alert("Profile updated successfully.");

        navigate({
          to: "/profile",
        });
      } else {
        alert("Unable to update profile.");
      }
    } catch (e) {
      console.error(e);
      alert("Something went wrong.");
    } finally {
      setSaving(false);
    }
  }

  if (loading) {
    return <Loader label="Loading profile..." />;
  }

  return (
    <div className="mx-auto max-w-2xl px-4 py-10">

      <Button
        variant="outline"
        leftIcon={<FiArrowLeft />}
        onClick={() =>
          navigate({
            to: "/profile",
          })
        }
      >
        Back
      </Button>

      <div className="mt-6 rounded-[16px] border border-border bg-card p-8 shadow-soft">

        <h1 className="text-3xl font-extrabold">
          Edit Profile
        </h1>

        <p className="mt-2 text-muted-foreground">
          Update your profile information.
        </p>

        <div className="mt-8 space-y-6">

          <div>

            <label className="mb-2 flex items-center gap-2 font-semibold">
              <FiUser />
              Full Name
            </label>

            <input
              type="text"
              className="w-full rounded-lg border p-3 outline-none focus:border-blue-500"
              value={fullName}
              onChange={(e) => setFullName(e.target.value)}
            />

          </div>

          <div>

            <label className="mb-2 flex items-center gap-2 font-semibold">
              <FiPhone />
              Phone Number
            </label>

            <input
              type="text"
              className="w-full rounded-lg border p-3 outline-none focus:border-blue-500"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
            />

          </div>

          <Button
            fullWidth
            leftIcon={<FiSave />}
            onClick={saveProfile}
            disabled={saving}
          >
            {saving ? "Saving..." : "Save Changes"}
          </Button>

        </div>

      </div>

    </div>
  );
}
