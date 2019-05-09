package com.application.cmapp.network;

import android.util.Log;

import com.application.cmapp.model.Reading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adapter {
    private JSONArray root;
    private JSONObject latestReading;
    private Reading reading;
    private ArrayList<Reading> multipleLiveReadings;
    public Reading makeReading(String jsonString) throws JSONException {


        reading = new Reading(0, 0, 0, 0, 0, "0");
        try{
            latestReading = new JSONObject(jsonString);
            //JSONArray readings = root.getJSONArray(0);

            //JSONObject reading0 = root.getJSONObject(0);

            reading.setTemperature(Double.parseDouble(latestReading.getString("temperature")));
            reading.setCo2(Double.parseDouble(latestReading.getString("co2")));
            reading.setHumidity(Double.parseDouble(latestReading.getString("humidity")));
            reading.setSound(Double.parseDouble(latestReading.getString("sound")));
            reading.setDeviceNo(Integer.parseInt(latestReading.getString("device")));
            reading.setDateTime(latestReading.getString("datetime"));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return reading;
    }

    public ArrayList<Reading> makeMultipleReadings(String jsonString) throws JSONException {
        multipleLiveReadings = new ArrayList<Reading>();

        try {
            root = new JSONArray(jsonString);
            //Log.d("cacat", jsonString.toString());

            for (int i = 0; i < root.length();i++)
            {
                JSONObject object = root.getJSONObject(i);
                //Make reading vars
                double temp = Double.parseDouble(object.getString("temperature"));
                double co2 = Double.parseDouble(object.getString("co2"));
                double hum = Double.parseDouble(object.getString("humidity"));
                double sound = Double.parseDouble(object.getString("sound"));
                int devno = Integer.parseInt(object.getString("device"));
                String datetime = object.getString("datetime");
                Reading reading = new Reading(temp, devno, hum, sound, co2, datetime);
                multipleLiveReadings.add(reading);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return multipleLiveReadings;
    }

}
