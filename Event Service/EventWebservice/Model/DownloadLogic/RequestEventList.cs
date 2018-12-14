
namespace ConsoleApp1.Model.DownloadLogic
{
    using System.Collections.Generic;
    using System.IO;
    using System.Net;
    using System.Web.Script.Serialization;
    using Newtonsoft.Json.Linq;
    using static FacebookTestApi.Facebook_Core.Core;

    /// <summary>
    /// Download Events from Facebook Graph API
    /// </summary>
    /// <remarks>
    /// Downloads event ids by location
    /// Allows to downloads multiple json objects
    /// </remarks>
    public class RequestEventList
    {
        // maybe some side effect
        // set as parameters
        private double latPos;
        private double longPos;
        private int distance;

        // Delegate used to execute the callback method when the
        // task is complete.
        private EventListCallback eventListCallback;

        private JObject firstRequestEventlist;

        /// <summary>
        /// Initializes a new instance of the <see cref="RequestEventList"/> class.
        /// Description for RequestEventList method.</summary>
        /// <param name="latPos">latitude position</param>
        /// <param name="longPos">longitude position</param>
        /// <param name="distance">distance max 2500 </param>
        /// <param name="eventListCallback">Callback</param>
        /// Main entry for this project
        public RequestEventList(double latPos, double longPos, int distance, EventListCallback eventListCallback)
        {
            this.latPos = latPos;
            this.longPos = longPos;
            this.distance = distance;
            this.eventListCallback = eventListCallback;
        }

        /// <summary>
        /// Description for DownloadEventList method.</summary>
        /// Downloads JSON Object from Facebook Graph API
        /// Checks if paging exits
        public void DownloadEventList()
        {
            System.Diagnostics.Debug.WriteLine("URL: " + EventListUriModel.GetEventUri(this.latPos.ToString().Replace(",", "."), this.longPos.ToString().Replace(",", "."), this.distance.ToString()));

            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(EventListUriModel.GetEventUri(this.latPos.ToString().Replace(",", "."), this.longPos.ToString().Replace(",", "."), this.distance.ToString()));
            request.AutomaticDecompression = DecompressionMethods.GZip;

            string requestString = null;

            using (HttpWebResponse response = (HttpWebResponse)request.GetResponse())
            using (Stream stream = response.GetResponseStream())
            using (StreamReader reader = new StreamReader(stream))
            {
                requestString = reader.ReadToEnd();
            }

            this.firstRequestEventlist = JObject.Parse(requestString);

            if (this.IfPaging(requestString))
            {
                // call here callback if all events pages downloaded and merged
                // Parse events lists
                List<string> eventIdListFirst = this.ParseEventList(this.firstRequestEventlist.ToString());
                this.eventListCallback(eventIdListFirst);
            }
            else
            {
                List<string> eventIdListFirst = this.ParseEventList(requestString);
                this.eventListCallback(eventIdListFirst);
            }
        }

        /// <summary>
        /// Description for ParseEventList method.</summary>
        /// <param name="request">contians the request object</param>
        /// <returns>Event ID List</returns>
        /// Downloads JSON Object from Facebook Graph API
        /// Checks if paging exits
        private List<string> ParseEventList(string request)
        {
            List<string> eventIdList = new List<string>();

            EventList évents = new JavaScriptSerializer().Deserialize<EventList>(request);

            foreach (var item in évents.Data)
            {
                eventIdList.Add((string)item.Id);
            }

            return eventIdList;
        }

        /// <summary>
        /// Description for IfPaging method.</summary>
        /// <param name="requestString">contians the request object</param>
        /// <returns>true if object has paging object</returns>
        /// Check if request object has paging object
        private bool IfPaging(string requestString)
        {
            JObject jsonObject = JObject.Parse(requestString);

            if (requestString.Contains("paging"))
            {
                string nextUrl = (string)jsonObject["paging"]["next"];

                // download next page DownloadEventListNext
                this.DownloadEventListNext(nextUrl);

                System.Diagnostics.Debug.WriteLine("Download nextpage: " + nextUrl);

                return true;
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("No nextpage");

                return false;
            }
        }

        /// <summary>
        /// Description for DownloadEventListNext method.</summary>
        /// <param name="nextUrl">contians the next event list url</param>
        /// Downloads Next Event List if paging
        private void DownloadEventListNext(string nextUrl)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(nextUrl);
            request.AutomaticDecompression = DecompressionMethods.GZip;

            string requestString = null;

            using (HttpWebResponse response = (HttpWebResponse)request.GetResponse())
            using (Stream stream = response.GetResponseStream())
            using (StreamReader reader = new StreamReader(stream))
            {
                requestString = reader.ReadToEnd();
            }

            JObject eventIdListNextObject = JObject.Parse(requestString);

            // union array values together to avoid duplicates
            this.firstRequestEventlist.Merge(eventIdListNextObject, new JsonMergeSettings { MergeArrayHandling = MergeArrayHandling.Union });

            // System.Diagnostics.Debug.WriteLine("Merge: " + firstRequestEventlist.ToString());

            // System.Diagnostics.Debug.WriteLine("NextObject: " + requestString);

            // check if paging
            this.IfPaging(requestString);
        }
    }
}
