package com.application.cmapp.network;

import com.application.cmapp.model.Reading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Adapter {
    private JSONArray root;
    private Reading reading;
    public Reading makeReading(String jsonString) throws JSONException {


        reading = new Reading(0, 0, 0, 0, 0, "0");
        try{
            root = new JSONArray(jsonString);
            //JSONArray readings = root.getJSONArray(0);

            JSONObject reading0 = root.getJSONObject(0);

            reading.setTemperature(Double.parseDouble(reading0.getString("temperature")));
            reading.setCo2(Double.parseDouble(reading0.getString("co2")));
            reading.setHumidity(Double.parseDouble(reading0.getString("humidity")));
            reading.setSound(Double.parseDouble(reading0.getString("sound")));
            reading.setDeviceNo(Integer.parseInt(reading0.getString("device")));
            reading.setDateTime(reading0.getString("datetime"));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return reading;
    }

}
