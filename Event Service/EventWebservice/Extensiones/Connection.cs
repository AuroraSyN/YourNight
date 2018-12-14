using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Web;

namespace EventWebservice2.Extensiones
{
    public static class Connection
    {
        private const string EventDB = "EventDB2";

        public static string ConnString
            {
                get { return ConfigurationManager.ConnectionStrings[EventDB].ToString(); }
            }

            public static ConnectionState ConnState { get; set; }
        
    }
}