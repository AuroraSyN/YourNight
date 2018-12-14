
namespace ConsoleApp1.Model.DownloadLogic
{
    using System.IO;
    using System.Net;
    using static FacebookTestApi.Facebook_Core.Core;

    internal class RequestSpecificEvent
    {
        private string id;

        private EventSpecificCallback eventSpecificCallback;

        public RequestSpecificEvent(string id, EventSpecificCallback eventSpecificCallback)
        {
            this.id = id;
            this.eventSpecificCallback = eventSpecificCallback;
        }

        public void DownloadEvent()
        {
            System.Diagnostics.Debug.WriteLine("Url Event: " + EventSpecificUriModel.GetEventsUri(id));

            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(EventSpecificUriModel.GetEventsUri(id));
            request.AutomaticDecompression = DecompressionMethods.GZip;

            string requestString = null;

            using (HttpWebResponse response = (HttpWebResponse)request.GetResponse())
            using (Stream stream = response.GetResponseStream())
            using (StreamReader reader = new StreamReader(stream))
            {
                requestString = reader.ReadToEnd();
            }

            System.Diagnostics.Debug.WriteLine("Event: " + requestString);

            this.eventSpecificCallback(requestString);
        }
    }
}
