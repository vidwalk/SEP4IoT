using MongoDB.Bson;
using MongoDB.Driver;
using System;

namespace BridgeSocketServer
{
    /// <summary>
    /// This class is responsible MongoDB specific connection and sending provided readings to it
    /// </summary>
    class MongoDBHelper : IDatabaseHelper
    {
        private readonly string _dbUri;
        private readonly IMongoDatabase _db;

        public MongoDBHelper(string uri)
        {
            _dbUri = uri;
            MongoClient mongoClient = new MongoClient(_dbUri);
            _db = mongoClient.GetDatabase("ClimatizerDB");
            Console.WriteLine("Connected to the database successfully");
        }

        public void StoreReading(BsonDocument reading)
        {
            var collection = _db.GetCollection<BsonDocument>("Climatizer");
            collection.InsertOne(reading);
            Console.WriteLine("Send data " + collection.ToString());

        }
    }
}
