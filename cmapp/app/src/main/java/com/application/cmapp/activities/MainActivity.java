package com.application.cmapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.application.cmapp.R;
import com.application.cmapp.model.Reading;
import com.application.cmapp.viewmodel.LogInViewModel;
import com.application.cmapp.viewmodel.ReadingViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView temperature, deviceNo, sound, co2, humidity, datetime, date;
    //Reading reading;
    Button getReading, getReadings, openWindow, gotoLogin;
    Reading reading = new Reading(0, 0,0 ,0, "");
    ArrayList<Reading> readings = new ArrayList<Reading>();

    TextView email;
    Button adminButton;

    private ViewPager vPager;
    private ViewPagerAdapter adapter;

    private ReadingViewModel viewModel;
    private LogInViewModel logInViewModel;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Quick connection no Async task for quick testing
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        //StrictMode.setThreadPolicy(policy);

        instance = this;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        vPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapter);

        //Login elements
        adminButton = findViewById(R.id.ButtonForAdmins);

        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(vPager);

        /*
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

*/
//        temperature.setText("Temperature: ");

        getReading = findViewById(R.id.getReading);

        viewModel = ViewModelProviders.of(this).get(ReadingViewModel.class);
        logInViewModel = ViewModelProviders.of(this).get(LogInViewModel.class);


        //Get latest reading
        try {

            viewModel.getReading("http://192.168.185.213:8080/readings");
        } catch (IOException ex){
            ex.printStackTrace();
        }




        //Most Recent Reading refresh button
        getReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Get reading
                    //viewModel.sendOpenWindow("http://10.152.194.18:8080/window");
                  //  Log.d("cacat", "pisat");
                  //
                    viewModel.getReading("http://192.168.185.213:8080/readings");
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
/*
        //Multiple Readings
        getReadings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //Get reading
                    viewModel.getReadings("http://10.152.194.100:8080/readings/"+date.getText());
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
                    viewModel.sendOpenWindow("http://10.152.222.116:8080/window");
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

*/
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


        //LOGIN
        MutableLiveData<String> readingLoginLiveData = logInViewModel.getAdminIsLoggedInCheck();
        MutableLiveData<String> readingSignOutLiveData = logInViewModel.AdminSignOut();

        readingSignOutLiveData.observe(this, new Observer<String>()
        {
            public void onChanged(String s)
            {
               // email.setText(s);
            }
        });

        readingLoginLiveData.observe(this, new Observer<String>()
        {
            public void onChanged( String s)
            {
                //email.setText(s);

                /*
                if (email.getText().toString().equals("Anonymous user"))
                {
                    adminButton.setVisibility(View.GONE);
                }

                else {
                    adminButton.setVisibility(View.VISIBLE);
                }
*/


            }
        });

    }



    @Override
    //inflate toolbar
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Top bar item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Fragment fragment = null;
        switch(item.getItemId())
        {
            case R.id.action_settings:
               // Intent i = new Intent(getApplicationContext(), LoginActivity.class);
               // startActivity(i);
                //User chooses the "Settings" item, show the app settings UI
                return true;
            case R.id.action_login:
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void updateCurrentReading(Reading reading)
    {
        this.reading = reading;
    }

    public void updateCurrentReadings(ArrayList<Reading> readings)
    {
        this.readings = readings;
    }


    public Reading getLatestReading()
    {
        return reading;
    }


    public static MainActivity getInstance()
    {
        return instance;
    }

    public ReadingViewModel getViewModel()
    {
        return viewModel;
    }





}
