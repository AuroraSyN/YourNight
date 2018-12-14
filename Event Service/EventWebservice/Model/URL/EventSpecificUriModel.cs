

namespace ConsoleApp1.Model
{
    using System;
    using ConsoleApp1.Extensiones;

    class EventSpecificUriModel
    {
        private static string eventURL = "https://graph.facebook.com/v2.12/";
        private static string ids = "?ids=";
        private static string accessToken = "&access_token=" + AccessToken.GetToken();
        private static string fieldstmp = "&fields=id,name,about,emails,cover.fields(id,source),picture.type(large),category," +
            "category_list.fields(name),location{city,country,latitude,longitude,state,street,zip},events.fields(type,name,cover.fields(id,source),picture.type(large),description,start_time,end_time,category,attending_count,declined_count,maybe_count,noreply_count," +
            "ticket_uri,ticketing_privacy_uri,ticketing_terms_uri,place.fields(id,name,location{city,country,latitude,longitude,state,street,zip}),is_canceled,is_draft)";

        private static string fields = "&fields=id,name,cover.fields(id,source),picture.type(large),location,events.fields(id,name,cover.fields(id,source),picture.type(large),description,start_time,attending_count,declined_count,maybe_count,noreply_count).since(" + DateTimeOffset.Now.ToUnixTimeMilliseconds().ToString() + ")";

        public static string GetEventsUri(string id)
        {
            return eventURL + ids + id + accessToken + fieldstmp;
        }
    }
}