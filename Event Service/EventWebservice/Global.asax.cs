using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Http.Routing;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;

namespace EventWebservice2
{
    public class MvcApplication : System.Web.HttpApplication
    {
        private void Application_Start(object sender, EventArgs e)
        {
            GlobalConfiguration.Configuration.Routes.Add("default", new HttpRoute("{controller}"));
        }
    }
}
