using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer
{
    /// <summary>
    /// Responsible for handling requests coming to socket connection,
    /// converting the data to the desirable format and calling methods on provided DatabaseHelper
    /// </summary>
    interface IRequestHandler
    {
        void HandleRequest(Enum operationNum, string data);
    }
}
