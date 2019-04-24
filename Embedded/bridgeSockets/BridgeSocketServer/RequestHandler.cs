using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer
{
    class RequestHandler : IRequestHandler
    {
        public enum OperationNumber {SEND_READINGS_TO_DB};
        private IDatabaseHelper _helper;

        public RequestHandler()
        {
            _helper = new DatabaseHelper();
        }

        public void HandleRequest(Enum operationNum, string data)
        {
            switch (operationNum)
            {
                case OperationNumber.SEND_READINGS_TO_DB:
                    SendReadingsToDatabase(data);
                    break;

            }
        }

        private void SendReadingsToDatabase(string data)
        {
            throw new NotImplementedException();
        }
    }
}
