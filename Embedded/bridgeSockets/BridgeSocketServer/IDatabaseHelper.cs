using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer
{
    /// <summary>
    ///  This interface is responsible for connection to database and sending data to database
    /// </summary>

    interface IDatabaseHelper
    {

        void SendReading(string jsonData);

    }
}
