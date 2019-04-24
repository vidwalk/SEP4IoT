using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Text;

namespace BridgeSocketServer
{
    // This class is responsible for connection to database and sending data
    class MongoDBHelper : IDatabaseHelper
    {
        private readonly string _dbUri;

        public MongoDBHelper(string uri)
        {
            _dbUri = uri;
            MongoClient mongoClient = new MongoClient(_dbUri);
            var database = mongoClient.GetDatabase("ClimatizerDB");
            Console.WriteLine("Connected to the database successfully");
        }

    }
}
