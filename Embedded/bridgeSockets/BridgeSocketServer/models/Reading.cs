using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer.models
{

    class Reading
    {
        public double Temperature { get; set; }
        public double Humidity { get; set; }
        public double CO2 { get; set; }
        public DateTime Date { get; set; }
        public string Device { get; set; }
    }
}
