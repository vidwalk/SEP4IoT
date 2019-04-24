using System;
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
            Console.WriteLine("Connected to the database successfully");
        }
    }
}
