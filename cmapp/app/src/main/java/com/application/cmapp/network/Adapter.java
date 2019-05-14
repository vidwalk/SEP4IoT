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


        reading = new Reading(0, 0, 0, 0, "00");
        try{
            latestReading = new JSONObject(jsonString);
            //JSONArray readings = root.getJSONArray(0);

            //JSONObject reading0 = root.getJSONObject(0);

            reading.setTemperature(Double.parseDouble(latestReading.getString("temperature")));
            reading.setCo2(Double.parseDouble(latestReading.getString("co2")));
            reading.setHumidity(Double.parseDouble(latestReading.getString("humidity")));
            reading.setLight(Double.parseDouble(latestReading.getString("light")));
        //    reading.setDeviceNo(Integer.parseInt(latestReading.getString("device")));
            reading.setDateTime(latestReading.getString("datetime"));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return reading;
    }

    public ArrayList<Reading> makeMultipleReadings(String jsonString) throws JSONException {
        multipleLiveReadings = new ArrayList<Reading>();
        multipleLiveReadings.add(new Reading(0, 0, 0, 0, "00"));
        try {
            root = new JSONArray(jsonString);

            for (int i = 0; i < root.length();i++)
            {
                JSONObject object = root.getJSONObject(i);
                //Make reading vars
                double temp = Double.parseDouble(object.getString("temperature"));
                double co2 = Double.parseDouble(object.getString("co2"));
                double hum = Double.parseDouble(object.getString("humidity"));
                double light = Double.parseDouble(object.getString("light"));
                String datetime = object.getString("datetime");
                Reading reading = new Reading(temp, hum, light, co2, datetime);
                multipleLiveReadings.add(reading);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return multipleLiveReadings;
    }

}
