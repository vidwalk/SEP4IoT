package connection.spring.wsConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UplinkMessageFormatter {

    public static String incomingMessage;
    public static double temperature;
    public static double humidity;
    public static int co2;
    public static int light;
    public static String EUI = "11dc3bc663ea64c5";

    public static String receiveMessage(CharSequence data) {
        incomingMessage = data.toString();
        return formatData();
    }

    public static String formatData() {
        JSONObject inJson = new JSONObject(incomingMessage);

        //Getting the payload
        try {
            incomingMessage = inJson.getString("data");
        } catch (JSONException e) {
            System.out.println("No data field was found.");
            return null;
        }
        String currentData;
        //Dividing the payload to separate data and storing in the correct variables
        for(int i = 0; i <= 12; i=i+4) {
            currentData = incomingMessage.substring(i, i+4);
            switch (i) {
                case 0 :
                    temperature = Integer.parseInt(currentData, 16);
                    temperature = temperature/10;
                    break;
                case 4:
                    humidity = Integer.parseInt(currentData, 16);
                    humidity = humidity/10;
                    break;
                case 8:
                    co2 = Integer.parseInt(currentData, 16);
                    break;
                case 12:
                    light = Integer.parseInt(currentData, 16);
                    break;
            }
        }

        //Creating a Json that will be sent to MongoDB
        String outJsonString = new JSONObject()
                .put("temperature", temperature)
                .put("humidity", humidity)
                .put("CO2", co2)
                .put("light", light)
                .put("date", "")
                .put("device", EUI).toString();

        System.out.println(outJsonString);

        return outJsonString;

    }
}
