namespace FacebookTestApi.Authorization
{
    using System.Web;

    /// <summary>
    /// AuthorizeAttribute Class handles
    /// the unauthorized requestes
    /// </summary>
    public class AuthorizeAttribute : System.Web.Http.AuthorizeAttribute
    {
        /// <summary>
        /// HandleUnauthorizedRequest handles
        /// the unauthorized requestes
        /// sends a forbidden code
        /// to the client
        /// </summary>
        /// <remarks>
        /// </remarks>
        /// <param name="actionContext">
        /// contains the HttpActionContext
        /// </param>
        protected override void HandleUnauthorizedRequest(System.Web.Http.Controllers.HttpActionContext actionContext)
        {
            if (!HttpContext.Current.User.Identity.IsAuthenticated)
            {
                base.HandleUnauthorizedRequest(actionContext);
            }
            else
            {
                actionContext.Response = new System.Net.Http.HttpResponseMessage(System.Net.HttpStatusCode.Forbidden);
            }
        }
    }
}