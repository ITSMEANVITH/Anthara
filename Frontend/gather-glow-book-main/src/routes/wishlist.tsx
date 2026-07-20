import { createFileRoute, Link } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { FiHeart, FiTrash2 } from "react-icons/fi";
import { WishlistService } from "@/services/api";
import { Button } from "@/components/Button";

export const Route = createFileRoute("/wishlist")({
  component: Wishlist,
});

function Wishlist() {

  const [events, setEvents] = useState<any[]>([]);

  useEffect(() => {

    loadWishlist();

  }, []);

  async function loadWishlist() {

    try {

      const res = await WishlistService.get();

      setEvents(res.data);

    } catch (e) {

      console.error(e);

      alert("Unable to load wishlist.");

    }

  }

  async function remove(eventId:number){

    try{

      await WishlistService.remove(eventId);

      loadWishlist();

    }catch(e){

      console.error(e);

    }

  }

  return (

    <div className="mx-auto max-w-6xl px-4 py-10">

      <h1 className="text-3xl font-bold flex items-center gap-2">

        <FiHeart className="text-red-500"/>

        My Wishlist

      </h1>

      {
        events.length===0 ?

        <div className="mt-10 text-center">

          <p>No events in wishlist.</p>

          <Link to="/events">

            <Button className="mt-5">

              Browse Events

            </Button>

          </Link>

        </div>

        :

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 mt-8">

          {

            events.map((e)=>(

              <div
                key={e.eventId}
                className="rounded-xl border p-5 shadow"
              >

                <h2 className="font-bold text-xl">

                  {e.title}

                </h2>

                <p className="mt-2">

                  {e.venue}

                </p>

                <p>

                  {e.date}

                </p>

                <h3 className="mt-4 text-2xl font-bold">

                  ₹ {e.price}

                </h3>

                <div className="flex gap-3 mt-5">

                  <Link
                    to="/booking/$eventId"
                    params={{
                      eventId:e.eventId
                    }}
                  >

                    <Button>

                      Book Now

                    </Button>

                  </Link>

                  <Button
                    variant="outline"
                    leftIcon={<FiTrash2/>}
                    onClick={()=>
                      remove(e.eventId)
                    }
                  >

                    Remove

                  </Button>

                </div>

              </div>

            ))

          }

        </div>

      }

    </div>

  );

}
