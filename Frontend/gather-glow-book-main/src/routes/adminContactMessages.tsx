import { createFileRoute } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { ContactService } from "@/services/api";
import { DataTable, type Column } from "@/components/DataTable";

export const Route = createFileRoute("/adminContactMessages")({
  component: ContactMessages,
});

function ContactMessages() {

  const [messages, setMessages] = useState<any[]>([]);

  useEffect(() => {
    loadMessages();
  }, []);

  async function loadMessages() {

    try {

      const res = await ContactService.getAll();

      console.log("Response:", res);
      console.log("Data:", res.data);

      setMessages(res.data);

    } catch (err: any) {

      console.log(err);
      console.log(err.response);

    }

  }

  const columns: Column<any>[] = [

    {
      header: "Name",
      accessor: (m) => m.name
    },

    {
      header: "Email",
      accessor: (m) => m.email
    },

    {
      header: "Subject",
      accessor: (m) => m.subject
    },

    {
      header: "Message",
      accessor: (m) => (
        <div className="max-w-xs truncate">
          {m.message}
        </div>
      )
    },

    {
      header: "Date",
      accessor: (m) => m.createdAt
    }

  ];

  return (

    <div className="mx-auto max-w-7xl px-4 py-10">

      <h1 className="text-3xl font-extrabold mb-6">
        Contact Messages
      </h1>

      <DataTable
        columns={columns}
        data={messages}
      />

    </div>

  );

}