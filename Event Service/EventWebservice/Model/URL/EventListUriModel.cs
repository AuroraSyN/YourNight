
namespace ConsoleApp1.Model
{
    using System.Collections.Generic;
    using ConsoleApp1.Extensiones;

    /// <summary>
    /// Download Events URL for Facebook Event API
    /// </summary>
    /// <remarks>
    internal class EventListUriModel
    {
        // event api v2.12 
        // place: This is the consolidated Place object from the Venue (which is actually the Page object which was returned from the Place search),
        // and the Event's place data. The latter will supersede the Place page data.
        private static string eventsUrl = "https://graph.facebook.com/v2.12/search?type=place&q=*&center=";

        // distance: The distance in meters (it makes sense to use smaller distances, like max. 2500). Default is 100
        private static string distance = "&distance=";

        // limit is 50 events per api call
        private static string limit = "&limit=100";

        // request shows only the field id, no more
        private static string fields = "&fields=id";

        // App Access Token to be used for the requests to the Graph API
        private static string accessToken = "&access_token=" + AccessToken.GetToken();

        // https://developers.facebook.com/docs/places/web/search#categories
        private static string query = "&q=club";
        private static string categories = "&categories=";

        // sort: The results can be sorted by time, distance (legacy option, will be removed in future release), venueDistance, eventDistance, 
        // venue or popularity. If omitted, the events will be returned in the order they were received from the Graph API.
        private static string sort = "&sort=venue";

        public static List<EventListUriModel> Data { get; set; }

        // return the URL String for Event ID list by location
        public static string GetEventUri(string latPos, string longPos, string distanceValue)
        {
            return eventsUrl + latPos + "," + longPos + distance + distanceValue + limit + fields + accessToken;
        }
    }
}
