namespace FacebookTestApi.Authorization
{
    using System;
    using System.Net.Http;
    using System.Web.Http.Controllers;
    using System.Web.Http.Filters;

    /// <summary>
    /// RequireHttpsAttribute Class handles
    /// the connection type
    /// </summary>
    public class RequireHttpsAttribute : AuthorizationFilterAttribute
    {
        /// <summary>
        /// OnAuthorization handles
        /// the unauthorized connection typ
        /// sends a forbidden code
        /// to the client
        /// </summary>
        /// <remarks>
        /// </remarks>
        /// <param name="actionContext">
        /// contains the HttpActionContext
        /// </param>
        public override void OnAuthorization(HttpActionContext actionContext)
        {
            if (actionContext.Request.RequestUri.Scheme != Uri.UriSchemeHttps)
            {
                actionContext.Response = new HttpResponseMessage(System.Net.HttpStatusCode.Forbidden)
                {
                    ReasonPhrase = "HTTPS Required"
                };
            }
            else
            {
                base.OnAuthorization(actionContext);
            }
        }
    }
}