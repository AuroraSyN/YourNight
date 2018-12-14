
namespace ConsoleApp1.Model
{
    using System.Collections.Generic;

    /// <summary>
    /// Model for events ids
    /// </summary>
    internal class EventList
    {
        /// <summary>
        /// Gets or sets the event ids by location.
        /// </summary>
        /// <value>
        /// The Data contains event ids.
        /// </value>
        public List<EventObject> Data { get; set; }
    }
}
