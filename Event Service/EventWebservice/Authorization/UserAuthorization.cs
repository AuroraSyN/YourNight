namespace FacebookTestApi.Authorization
{
    using System.Security.Claims;
    using System.Threading.Tasks;
    using Microsoft.Owin.Security.OAuth;

    /// <summary>
    /// UserAuthorization Class handles
    /// client acces to the webservice
    /// </summary>
    public class UserAuthorization : OAuthAuthorizationServerProvider
    {
        /// <summary>
        /// OnAuthorization can be
        /// used to verify a base64
        /// encoding
        /// </summary>
        /// <remarks>

        /// </remarks>
        /// <returns>representing the asynchronous operation</returns>
        /// <param name="ctx">
        /// contains the OAuthValidateClientAuthenticationContext
        /// </param>
        public override async Task ValidateClientAuthentication(OAuthValidateClientAuthenticationContext ctx)
        {
            bool valid = ctx.Validated();
            if (!valid)
            {
                await Task.Delay(1000);
            }
        }

        /// <summary>
        /// GrantResourceOwnerCredentials
        /// gives the client the acces token
        /// if username and password are right
        /// </summary>
        /// <remarks>
        /// </remarks>
        /// <returns>representing the asynchronous operation</returns>
        /// <param name="ctx">
        /// contains the OAuthGrantResourceOwnerCredentialsContext
        /// </param>
        public override async Task GrantResourceOwnerCredentials(OAuthGrantResourceOwnerCredentialsContext ctx)
        {
            var identity = new ClaimsIdentity(ctx.Options.AuthenticationType);

            if (ctx.UserName == "user" && ctx.Password == "yournightpass")
            {
                identity.AddClaim(new Claim(ClaimTypes.Role, "DefaultMachine"));
                identity.AddClaim(new Claim("username", "DefaultMachine"));
                identity.AddClaim(new Claim(ClaimTypes.Name, "Machine"));
                ctx.Validated(identity);
            }
            else
            {
                ctx.SetError("400 Bad Request", "Username or password is incorrect");
                await Task.Delay(1000);
                return;
            }
        }
    }
}