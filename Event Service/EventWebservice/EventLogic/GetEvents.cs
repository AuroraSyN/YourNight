using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Web;

namespace EventWebservice2.EventDummyLogic
{
    public class GetEvents
    {

        public void readEvents()
        {

            System.Diagnostics.Debug.WriteLine("Read Events");

            try
            {

                using (SqlConnection sqlConnection = new SqlConnection(Extensiones.Connection.ConnString))
                {
                    string query = "Select * from EventTable";
                    SqlCommand cmd = new SqlCommand(query, sqlConnection);
                    sqlConnection.Open();
                    using (SqlDataReader oReader = cmd.ExecuteReader())
                    {
                        while (oReader.Read())
                        {
                            System.Diagnostics.Debug.WriteLine("EventInfo: " + (string)oReader["EventInfo"]);
                        }

                        sqlConnection.Close();
                    }
                }

            }
            catch (Exception e)
            {
                System.Diagnostics.Debug.WriteLine("Exception: " + e.ToString());
            }

        }
    }

}