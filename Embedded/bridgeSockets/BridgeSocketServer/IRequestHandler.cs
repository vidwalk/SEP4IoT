using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer
{
    /// <summary>
    /// Responsible for handling requests received with the socket connection and
    /// converting the data to the desirable json format. The data should be in its origin form received from the LoRaWAN, this class is responsible
    /// for extracting the needed parts
    /// </summary>
    interface IRequestHandler
    {
        /// <summary>
        /// 
        /// </summary>
        /// <param name="operationNum">This enum should be specific to the operation and is derived from the class implementing this interface</param>
        /// <param name="data">The data should not be the data received from the device as a whole (including device, timestamps etc.</param>
        void HandleRequest(Enum operationNum, string data);
    }
}
