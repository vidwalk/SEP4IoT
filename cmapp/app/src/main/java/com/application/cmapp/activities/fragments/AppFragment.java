package com.application.cmapp.activities.fragments;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.application.cmapp.R;
import com.application.cmapp.activities.MainActivity;
import com.application.cmapp.model.Reading;
import com.application.cmapp.viewmodel.ReadingViewModel;

public class AppFragment extends Fragment {

    Reading currentReading;
    TextView dataDisplay;
    int position;
    private String dataPart = "temp";
    public static AppFragment instance;

    public AppFragment(int position){
        //No-args constructor
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance){

        return inflater.inflate(R.layout.activity_app_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance)
    {




        instance = this;
        currentReading = new Reading(0, 0,0 ,0, "");

        dataDisplay = view.findViewById(R.id.fragText);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());


        SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                refreshTemp();
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(spChanged);






        ReadingViewModel viewModel = MainActivity.getInstance().getViewModel();
        //Make this activity observe the most recent reading mutableData
        MutableLiveData<Reading> readingLiveData = viewModel.getReadingLiveData();

        readingLiveData.observe(this, new Observer<Reading>()
        {
            public void onChanged(@Nullable Reading reading)
            {
                //Update current reading
                currentReading = reading;

                switch (position)
                {

                    case 0:
                        dataPart = "temp";

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        if (prefs.getBoolean("imperial", false) == false)
                        dataDisplay.setText(String.valueOf(currentReading.getTemperature())+" C"+"°");
                        else
                            dataDisplay.setText(String.valueOf((currentReading.getTemperature()*9/5)+32)+" F"+"°");
                        break;

                    case 1:
                        dataPart = "hum";
                        dataDisplay.setText(String.valueOf(currentReading.getHumidity()) +" %");
                        break;
                    case 2:
                        dataPart = "co2";
                        dataDisplay.setText(String.valueOf((int)currentReading.getCo2()) + " ppm");
                        break;
                    case 3:
                        dataPart = "light";
                        dataDisplay.setText(String.valueOf((int)currentReading.getLight()) + " lux");
                        break;
                    default:
                        dataPart = "temp";
                        dataDisplay.setText("Couldn't Read Data.");
                        break;
                }
            }
        });

    }






    public void refreshTemp()
    {
        if (position == 0 && !dataDisplay.getText().toString().matches("Retrieving data...")) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            if (prefs.getBoolean("imperial", false) == false)
                dataDisplay.setText((currentReading.getTemperature()) + " C" + "°");
            else
                dataDisplay.setText(((currentReading.getTemperature() * 9 / 5) + 32) + " F" + "°");
        }
    }

    public static AppFragment getInstance()
    {
        return instance;
    }

    public String getDataPart()
    {
        return dataPart;
    }

    public int getPosition()
    {
        return position;
    }
}
