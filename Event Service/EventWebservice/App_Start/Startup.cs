using System;
using Microsoft.Owin;
using Microsoft.Owin.Security.OAuth;
using Owin;
using FacebookTestApi.Authorization;

[assembly: OwinStartup(typeof(FacebookTestApi.App_Start.Startup))]
namespace FacebookTestApi.App_Start
{
    public class Startup
    {
        /// <summary>
        /// Configuration method
        /// configure the service
        /// </summary>
        /// <remarks>
        /// </remarks>
        /// <param name="app">
        /// contains the IAppBuilder
        /// </param>
        public void Configuration(IAppBuilder app)
        {
            app.UseCors(Microsoft.Owin.Cors.CorsOptions.AllowAll);

            var provider = new UserAuthorization();

            // Disable HTTP for Authorization
            // Set Token path
            // Set Token expire time
            // Set the provider
            OAuthAuthorizationServerOptions options = new OAuthAuthorizationServerOptions
            {
                AllowInsecureHttp = true,
                TokenEndpointPath = new PathString("/token"),
                AccessTokenExpireTimeSpan = TimeSpan.FromDays(1),
                Provider = provider
            };

            // Generate a token
            app.UseOAuthAuthorizationServer(options);
            app.UseOAuthBearerAuthentication(new OAuthBearerAuthenticationOptions());
        }
    }
}
