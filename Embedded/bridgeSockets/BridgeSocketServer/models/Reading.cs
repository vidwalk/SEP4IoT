using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer.models
{
    /// <summary>
    /// This class represents a reading from a device that's gonna be passed into the database
    /// </summary>
    class Reading
    {
        public double Temperature { get; set; }
        public double Humidity { get; set; }
        public double CO2 { get; set; }
        public DateTime Date { get; set; }
        public string Device { get; set; }
    }
}
