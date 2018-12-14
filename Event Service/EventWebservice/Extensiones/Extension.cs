namespace ConsoleApp1.Extensiones
{
    using Newtonsoft.Json.Linq;

    /// <summary>
    /// Extension
    /// </summary>
    /// <remarks>
    /// Extension Class for the facebook event core
    /// </remarks>
    internal static class Extension
    {
        //this is only for test purpose
        static string testData = @"{
  'events': [{
    'id': '836655879846811',
    'name': 'test',
    'type': 'public',
    'coverPicture': 'https://scontent.xx.fbcdn.net/v/t31.0-8/s720x720/24883312_1521878931228093_3223523563973203944_o.jpg?oh=9bc3e5c5d45e39c542b057b92df95243&oe=5AC0353F',
    'profilePicture': 'https://scontent.xx.fbcdn.net/v/t1.0-0/c0.0.200.200/p200x200/24862268_1521878931228093_3223523563973203944_n.jpg?oh=23ec7dc943402ec7e0137f2d17f27719&oe=5AC246F8',
    'description': 'Friday, April 13th',
    'distance': 89,
    'startTime': '2018-04-13T20:00:00-0400',
    'endTime': null,
    'timeFromNow': 9982924,
    'isCancelled': false,
    'category': 'MUSIC_EVENT',
    'ticketing': {
      'ticket_uri': 'http://ticketf.ly/2j7AegO'
    },
    'place': {
      'id': '460616340718401',
      'name': 'Babys All Right',
      'location': {
        'city': 'Brooklyn',
        'country': 'United States',
        'latitude': 40.71012,
        'longitude': -73.96348,
        'state': 'NY',
        'street': '146 Broadway',
        'zip': '11211'
      }
    },
    'stats': {
      'attending': 20,
      'declined': 0,
      'maybe': 77,
      'noreply': 6
    },
    'distances': {
      'venue': 89,
      'event': 89
    },
    'venue': {
      'id': '460616340718401',
      'name': 'Babys All Right',
      'about': 'babysallright@gmail.com',
      'emails': ['babysallright@gmail.com'],
      'coverPicture': 'https://scontent.xx.fbcdn.net/v/t31.0-8/s720x720/20507438_1418517768261582_7945740169309872258_o.jpg?oh=24280a4732605e140c227db955c8d5e0&oe=5AC6B878',
      'profilePicture': 'https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/1480734_642185745894792_5820988503650852577_n.png?oh=c6e72b8a5645644e7dd3eb3d2161329f&oe=5AC0CD2D',
      'category': 'Bar',
      'location': {
        'city': 'Brooklyn',
        'country': 'United States',
        'latitude': 40.71012,
        'longitude': -73.96348,
        'state': 'NY',
        'street': '146 Broadway',
        'zip': '11211'
      }
    }
  }],
  'metadata': {
    'venues': 100,
    'venuesWithEvents': 2,
    'events': 25
  }
}";

        /// <summary>
        /// Description for Method ConvertToJsonObject.</summary>
        /// <param name="eventResult">event list </param>
        /// Downloads specific events from the graph api
        /// <returns>JSON Object of specific event result</returns>
        public static JObject ConvertToJsonObject(string eventResult)
        {
            return JObject.Parse(eventResult);
        }

        /// <summary>
        /// Description for Method ComparePopularity.</summary>
        /// <param name="eventOne">first event</param>
        /// <param name="eventTwo">second event</param>
        /// <returns>Result 1 event One is more popular
        /// Result 2 event two is more popular
        /// Result 0 events are equal popular</returns>
        public static int ComparePopularity(EventPopularity eventOne, EventPopularity eventTwo)
        {
            if ((eventOne.Attending + (eventOne.Maybe / 2)) < (eventTwo.Attending + (eventTwo.Maybe / 2)))
            {
                return 1;
            }

            if ((eventOne.Attending + (eventOne.Maybe / 2)) > (eventTwo.Attending + (eventTwo.Maybe / 2)))
            {
                return -1;
            }

            return 0;
        }

        /// <summary>
        /// Description for Method IsEventActive.</summary>
        /// <param name="eventResult">first event</param>
        /// <returns>Result true is event
        /// Result false event was canceled</returns>
        public static bool IsEventActive(string eventResult)
        {
            var jObject = JObject.Parse(eventResult);
            string result = (string)jObject["events"][0]["isCancelled"];

            System.Diagnostics.Debug.WriteLine("Is Canceld " + result);

            if (result.Equals("active"))
            {
                return true;
            }

            return false;
        }
    }
}
