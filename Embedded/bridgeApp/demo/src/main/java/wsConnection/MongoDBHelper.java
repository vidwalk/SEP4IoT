package wsConnection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MongoDBHelper {

    public void send(String data) {

        JSONObject json = new JSONObject(data);

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://groupZ1:groupZ1@iotzuperteam-no7vb.mongodb.net/test?retryWrites=true");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("ClimatizerDB");

        System.out.println("Connected to the database successfully");

        MongoCollection<Document> collection = database.getCollection("Climatizer");
        LocalDateTime now = LocalDateTime.now();
        Document document = new Document("temperature", json.get("temperature"))
                .append("humidity", json.get("humidity"))
                .append("CO2", json.get("CO2"))
                .append("light", json.get("light"))
                .append("date", now)
                .append("device", json.get("device"));
        collection.insertOne(document);
        mongoClient.close();
        System.out.println("Connection closed.");
    }
}
