using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer
{
    /// <summary>
    ///  This interface is responsible for connection to database and sending data to database
    /// </summary>

    public interface IDatabaseHelper
    {
        /// <summary>
        /// This method sends a json-parsed reading into the database
        /// </summary>
        /// <param name="jsonParsedData"></param>
        void StoreReading(BsonDocument reading);

    }
}
