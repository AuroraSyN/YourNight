using Newtonsoft.Json.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using FacebookTestApi.Facebook_Core;
using EventWebservice2.EventDummyLogic;

namespace FacebookTestApi.Controllers
{

    public class ValuesController : ApiController
    {
        //[Authorization.Authorize]
        //#if !DEBUG
        //[Authorization.RequireHttps]
        //#endif
        [HttpPost]
        public async Task<JObject> PostAsync(JObject jobject)
        {

            GetEvents getEvents = new GetEvents();
            getEvents.readEvents();

            return await Task.FromResult<JObject>(Core.DownloadEvents(jobject));
        }
    }
}
