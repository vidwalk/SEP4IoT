using System;
using MongoDB.Bson;
using MongoDB.Driver;
namespace bridgeSocket
{
    class Program
    {
        static void Main(string[] args)
        {
            var uri = "mongodb+srv://groupZ1:groupZ1@iotzuperteam-no7vb.mongodb.net/admin?retryWrites=true";

			MongoClient mongoClient = new MongoClient(uri);
			var database = mongoClient.GetDatabase("ClimatizerDB");

            // Retrieving a collection
            var collection = database.GetCollection<BsonDocument>("Climatizer"); 
            Console.WriteLine("Collection sampleCollection selected successfully");

            var document = new BsonDocument
            {
                {"temperature", 16},
                {"humidity", 51},
                {"CO2", 350.5},
                {"date", DateTime.Now},
                {"device", 16}
            };
            
            collection.InsertOne(document); 
            Console.WriteLine("Connected to the database successfully");
        }
    }
}
