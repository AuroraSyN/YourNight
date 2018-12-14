
namespace ConsoleApp1.Extensiones
{
    /// <summary>
    /// Acces Token for Facebook Graph API
    /// </summary>
    internal class AccessToken
    {
        private const string ACCESSTOKEN = "EAAcRPwZCqoOoBANJnkksY9fofhzPNv1zGtH917LUrtiE1L1d4k1YEPoRz0qiEVVrId0mP88qO0ZAp8vTUieUnksgOZAN0ZA2vzsqAETAGrK80yBxvJzyBcsJeNFV3r0jelffKaZCI3mpL4vZCID7aiMO5bB6PwOQYZD";

        /// <summary>
        /// Description for GetToken method.</summary>
        /// <returns>ACCESS Token</returns>
        /// Returns the access token
        public static string GetToken()
        {
            return ACCESSTOKEN;
        }
    }
}
