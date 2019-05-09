import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class PipelineData extends TimerTask {
	private MongoDatabase databaseMG;
	private Timestamp lastReading ;
	private boolean added = false;

	@Override
	public void run() {
		try {
			added = false;
			// Creating the URI for the mongo db
			// TODO Remove password
			MongoClientURI uri = new MongoClientURI(
					"mongodb+srv://groupZ1:groupZ1@iotzuperteam-no7vb.mongodb.net/admin?retryWrites=true");

			// Connecting to the mongo database
			MongoClient mongoClient = new MongoClient(uri);
			databaseMG = mongoClient.getDatabase("ClimatizerDB");

			// Connecting to the MSSQL database
			// This is to check if the jdbc SQL Driver is present
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlserver://10.200.131.2:1433;user=groupZ1;password=groupZ1;database=climatizerDB");

			// We need to retrieve the date of the latest reading
			Statement sta1 = conn.createStatement();
			String Sql1 = "(SELECT MAX(Date) as 'lastReading' FROM Reading)";
			ResultSet set = sta1.executeQuery(Sql1);
			while (set.next())
				lastReading = set.getTimestamp("lastReading");
			if(lastReading == null)
				lastReading =new Timestamp(0);
			// Retrieving a collection
			MongoCollection<Document> collection = databaseMG.getCollection("Climatizer");

			// Getting the iterable object
			FindIterable<Document> iterDoc = collection.find();
			// Getting the iterator
			Iterator it = iterDoc.iterator();

			while (it.hasNext()) {
				Document document = (Document) it.next();

				double temperature = (double) document.get("temperature");
				double humidity = (double) document.get("humidity");
				double CO2 = (double) document.get("CO2");
				double light = (double) document.get("light");
				
				// Remove one hour from the time
				Timestamp date = new Timestamp(((Date) document.get("date")).getTime() - 60 * 60 * 1000);
				int device = (int) document.get("device");
				//TODO Remove this print. It is used only for testing
				System.out.println("INSERT INTO Reading VALUES (" + CO2 + "," + temperature + "," + humidity + ",'" + date + "', " + device + ", " + light + ");");
				

				// We compare the latest reading with the readings that are currently retrieved
				// to check if there are any new ones

				if (date.compareTo(lastReading) > 0) {
					// A check to see if the db has actually transfered anything to the sql
					added = true;

					Statement sta2 = conn.createStatement();
					String Sql2 = "INSERT INTO Reading VALUES (" + CO2 + "," + temperature + "," + humidity + ",'" + date + "', " + device + ", " + light + ");";
					sta2.execute(Sql2);

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if statement to display if new documents have been added
		if (added)
			System.out.println("New documents added to sql server");
		else
			System.out.println("No new documents added to the sql server");
		// waits 30 seconds
		// completeTask();
	}

	private void completeTask() {
		try {
			// assuming it takes 30 secs to complete the task
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		TimerTask timerTask = new PipelineData();
		// running timer task as daemon thread
		Timer timer = new Timer(true);
		// Scheduled at 30 seconds
		timer.scheduleAtFixedRate(timerTask, 0, 30 * 1000);
		System.out.println("TimerTask started");
		// cancel after 2 minutes = 120 seconds
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// cancels the timer
		timer.cancel();
		System.out.println("TimerTask cancelled");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
