using ConsoleApp1.Extensiones;
using ConsoleApp1.Model.DownloadLogic;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using System.Threading;

namespace FacebookTestApi.Facebook_Core
{
    //TODO download events limitation is now 50
    //parse for catagorys example Dance & Night Club
    //Check if only "active" events (non-draft, non-cancelled) should be shown &&   // Check for non-draft, non-cancelled events
    //save events in db
    //https://www.newtonsoft.com/json/help/html/QueryingLINQtoJSON.htm
    //handle exception if data is empty
    public static class Core
    {
        public static JObject returnObject;
        /// <summary>
        /// Description for EventListCallback.</summary>
        /// <param name="eventIdList">event list</param>
        /// Delegate that defines the signature for the callback method
        public delegate void EventListCallback(List<string> eventIdList);

        /// <summary>
        /// Description for EventSpecificCallback.</summary>
        /// <param name="specificEvent">event list</param>
        /// Delegate that defines the signature for the callback method
        public delegate void EventSpecificCallback(string specificEvent);

        /// <summary>
        /// Download Events from Facebook Graph API
        /// </summary>
        /// <remarks>
        /// First downloads event ids by location
        /// With Event Id downloads specfic events details
        /// Create a JSON Object
        /// </remarks>


            /// <summary>
            /// Description for Main method.</summary>
            /// <param name="args">command-line arguments </param>
            /// Main entry for this project
            public static JObject DownloadEvents(JObject jobject)
            {
                // berlin 52.492131, 13.388334
                // ft 48.3 8.3
                double latPos = 48.3;
                double longPos = 8.3;
                int distance = 2500;

                // Get Events lists
                RequestEventList requestEventList = new RequestEventList(
                latPos, longPos, distance, new EventListCallback(ResultEventListCallback));

                // Create the thread object, passing in the
                // serverObject.InstanceMethod method using a
                // ThreadStart delegate.
                Thread thread = new Thread(new ThreadStart(requestEventList.DownloadEventList));

                // Start the thread.
                thread.Start();

                thread.Join();

                return returnObject;


              //  Extension.IsEventActive();

            }

            /// <summary>
            /// Description for Method ResultEventListCallback.</summary>
            /// <param name="eventIdList">event list </param>
            /// Main entry for this project
            // The callback method must match the signature of the
            // callback delegate.
            // for the webservice, set return type to jobject
            private static void ResultEventListCallback(List<string> eventIdList)
            {
                System.Diagnostics.Debug.WriteLine("Callback from RequestEventList");

                foreach (var item in eventIdList)
                {
                    System.Diagnostics.Debug.WriteLine("item " + item);
                }

                DownloadSpecificEvent(eventIdList);
            }

            /// <summary>
            /// Description for Method DownloadSpecificEvent.</summary>
            /// <param name="eventIdList">event list </param>
            /// Downloads specific events from the graph api
            private static void DownloadSpecificEvent(List<string> eventIdList)
            {
                foreach (var item in eventIdList)
                {
                    RequestSpecificEvent requestSpecificEvent = new RequestSpecificEvent(item, new EventSpecificCallback(ResultEventSpecificCallback));

                    Thread thread = new Thread(new ThreadStart(requestSpecificEvent.DownloadEvent));

                    // Start the thread.
                    thread.Start();
                }
            }


        /// <summary>
        /// Description for Method ResultEventSpecificCallback.</summary>
        /// <param name="eventIdList">event list </param>
        /// Result of the DownloadSpecificEvent Method
        private static void ResultEventSpecificCallback(string eventIdList)
            {  
                returnObject = Extension.ConvertToJsonObject(eventIdList);
             }
        }
}