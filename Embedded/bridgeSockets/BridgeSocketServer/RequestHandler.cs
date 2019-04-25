using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer
{
    /// <summary>
    /// This class takes the database instance in a constructor that will then be used to send data into database,
    /// whereas this class is responsible for parsing data into desirable format
    /// </summary>
    class RequestHandler : IRequestHandler
    {
        public enum OperationNumber {SEND_READINGS_TO_DB};
        private readonly IDatabaseHelper _helper;

        /// <summary>
        /// The helper param is a database specific (PostgreSQL, MongoDB etc.) object that will provide means for sending data to database
        /// </summary>
        /// <param name="helper"></param>
        public RequestHandler(IDatabaseHelper helper)
        {
            _helper = helper;
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
        /// <summary>
        /// This method extracts reading from the data and passes it to the provided DatabaseHelper object
        /// </summary>
        /// <param name="data"></param>
        private void SendReadingsToDatabase(string data)
        {
            // create a reading out of data
            var reading = new BsonDocument 
            {
                //Temperature and humidity should be a double
                {"temperature", 12.0},
                {"humidity", 71.0},
                {"CO2", 360.5},
                {"date", DateTime.Now},
                {"device", 16}
            };

            _helper.StoreReading(reading);

        }
    }
}
