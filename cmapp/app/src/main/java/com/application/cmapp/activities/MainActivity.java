package com.application.cmapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.application.cmapp.R;
import com.application.cmapp.model.Reading;
import com.application.cmapp.viewmodel.ReadingViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView temperature, deviceNo, sound, co2, humidity, datetime, date;
    //Reading reading;
    Button getReading, getReadings, openWindow;
    Reading reading;
    ArrayList<Reading> readings = new ArrayList<Reading>();

    private ReadingViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Quick connection no Async task for quick testing
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperature = findViewById(R.id.temperature);
        deviceNo = findViewById(R.id.devicenumber);
        sound = findViewById(R.id.sound);
        humidity = findViewById(R.id.humidity);
        co2 = findViewById(R.id.co2);
        datetime = findViewById(R.id.datetime);
        date = findViewById(R.id.date);

        getReading = findViewById(R.id.getReading);
        getReadings = findViewById(R.id.getReadings);
        openWindow = findViewById(R.id.openWindow);

        temperature.setText("Temperature: ");


        viewModel = ViewModelProviders.of(this).get(ReadingViewModel.class);

        //Most Recent Reading.
        getReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Get reading
                    viewModel.getReading("http://10.152.194.106:8080/readings");
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        //Multiple Readings
        getReadings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //Get reading
                    viewModel.getReadings("http://10.152.194.106:8080/readings/"+date.getText());
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        //Open Window
        openWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    //Send data
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("String", "bitch");
                    //viewModel.sendOpenWindow("http://10.152.194.106:8080/window/"+jsonObject.toString());
                    viewModel.sendOpenWindow("http://10.152.222.115:8080/window");
                }
                catch (IOException | JSONException ex){
                    ex.printStackTrace();
                }
            }
        });


        //Make this activity observe the most recent reading mutableData
        MutableLiveData<Reading> readingLiveData = viewModel.getReadingLiveData();

        readingLiveData.observe(this, new Observer<Reading>()
        {
            public void onChanged(@Nullable Reading reading)
            {
                //Update current reading
                updateCurrentReading(reading);
            }
        });

        //Make this activity observe the most recent list of readings
        MutableLiveData<ArrayList<Reading>> readingMultipleLiveData = viewModel.getMultipleReadingsLiveData();

        readingMultipleLiveData.observe(this, new Observer<ArrayList<Reading>>() {

            public void onChanged(@Nullable ArrayList<Reading> readings)
            {
                //Update current readings
                updateCurrentReadings(readings);
               // Log.d("cacat", readings.toString());
            }

        });


    }

    public void updateCurrentReading(Reading reading)
    {
      //  this.reading = reading;
        temperature.setText(String.valueOf(reading.getTemperature()));
        humidity.setText(String.valueOf(reading.getHumidity()));
        datetime.setText(reading.getDateTime());
        co2.setText(String.valueOf(reading.getCo2()));
        sound.setText(String.valueOf(reading.getSound()));
        deviceNo.setText(String.valueOf(reading.getDeviceNo()));

    }

    public void updateCurrentReadings(ArrayList<Reading> readings)
    {
        //this.readings = readings;
        Log.d("cacat", readings.toString());
        temperature.setText(String.valueOf(readings.get(1).getTemperature()));
        //this.readings = readings;
       // temperature.setText(String.valueOf(readings.get(0).getTemperature()));
        //Log.d("cacat", readings.toString());
        //if (readings.get(1).getTemperature() == 21.3)
        //temperature.setText(String.valueOf(readings.get(1).getTemperature()));
    }

}
