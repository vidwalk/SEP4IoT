package com.application.cmapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.application.cmapp.R;
import com.application.cmapp.activities.fragments.AppFragment;
import com.application.cmapp.activities.fragments.SettingsFragment;
import com.application.cmapp.model.Reading;
import com.application.cmapp.viewmodel.LogInViewModel;
import com.application.cmapp.viewmodel.ReadingViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView time0, time1, time2, time3, time4, time5, time6, time7, time8, time9, time10, time11,
            time12, time13, time14, time15, time16, time17, time18, time19, time20, time21, time22, time23,
            display0, display1, display2, display3, display4, display5, display6, display7, display8,
            display9, display10, display11, display12, display13, display14, display15, display16, display17,
            display18, display19, display20, display21, display22, display23;

    Button getReading;
        public Button date;
    int selectedYear, selectedDay, selectedMonth;
    Reading reading = new Reading(0, 0,0 ,0, "");
    ArrayList<Reading> readings = new ArrayList<Reading>();
    private View fadeBackground;
    private TextView[] displays;
    private TextView[] times;

    Button adminButton;

    private ViewPager vPager;
    private ViewPagerAdapter adapter;

    private ReadingViewModel viewModel;
    private LogInViewModel logInViewModel;
    private FrameLayout settingsFragmentPlaceholder;
    private static MainActivity instance;
    private int position = 0;
    private Calendar cal;
    private final String apiProtocol = "http://";
            private final String apiIp = "10.152.194.103";
            private final String apiPort = ":8080";
                    private final String apiReadingRequest = "/readings";
                    private final String apiReadingsRequest = "/readings/"; //Need to add year-month-day at the end
                    private final String apiWindowRequest = "/window";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        time0 = findViewById(R.id.time0); time1 = findViewById(R.id.time1); time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3); time4 = findViewById(R.id.time4); time5 = findViewById(R.id.time5);
        time6 = findViewById(R.id.time6); time7 = findViewById(R.id.time7); time8 = findViewById(R.id.time8);
        time9 = findViewById(R.id.time9); time10 = findViewById(R.id.time10); time11 = findViewById(R.id.time11);
        time12 = findViewById(R.id.time12); time13 = findViewById(R.id.time13); time14 = findViewById(R.id.time14);
        time15 = findViewById(R.id.time15); time16 = findViewById(R.id.time16); time17 = findViewById(R.id.time17);
        time18 = findViewById(R.id.time18); time19 = findViewById(R.id.time19); time20 = findViewById(R.id.time20);
        time21 = findViewById(R.id.time21); time22 = findViewById(R.id.time22); time23 = findViewById(R.id.time23);

        times = new TextView[24];
        times[0] = time0; times[1] = time1; times[2] = time2; times[3] = time3; times[4] = time4; times[5] = time5;
        times[6] = time6; times[7] = time7; times[8] = time8; times[9] = time9; times[10] = time10; times[11] = time11;
        times[12] = time12; times[13] = time13; times[14] = time14; times[15] = time15; times[16] = time16; times[17] = time17;
        times[18] = time18; times[19] = time19; times[20] = time20; times[21] = time21; times[22] = time22; times[23] = time23;

        display0 = findViewById(R.id.display0); display1 = findViewById(R.id.display1); display2 = findViewById(R.id.display2);
        display3 = findViewById(R.id.display3); display4 = findViewById(R.id.display4); display5 = findViewById(R.id.display5);
        display6 = findViewById(R.id.display6); display7 = findViewById(R.id.display7); display8 = findViewById(R.id.display8);
        display9 = findViewById(R.id.display9); display10 = findViewById(R.id.display10); display11 = findViewById(R.id.display11);
        display12 = findViewById(R.id.display12); display13 = findViewById(R.id.display13); display14 = findViewById(R.id.display14);
        display15 = findViewById(R.id.display15); display16 = findViewById(R.id.display16); display17 = findViewById(R.id.display17);
        display18 = findViewById(R.id.display18); display19 = findViewById(R.id.display19); display20 = findViewById(R.id.display20);
        display21 = findViewById(R.id.display21); display22 = findViewById(R.id.display22); display23 = findViewById(R.id.display23);

        displays = new TextView[24];
        displays[0] = display0; displays[1] = display1; displays[2] = display2; displays[3] = display3; displays[4] = display4; displays[5] = display5;
        displays[6] = display6; displays[7] = display7; displays[8] = display8; displays[9] = display9; displays[10] = display10; displays[11] = display11;
        displays[12] = display12; displays[13] = display13; displays[14] = display14; displays[15] = display15; displays[16] = display16; displays[17] = display17;
        displays[18] = display18; displays[19] = display19; displays[20] = display20; displays[21] = display21; displays[22] = display22; displays[23] = display23;


        instance = this;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);


        SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                updateReadingsUi(position);
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(spChanged);


        date = findViewById(R.id.date);

        viewModel = ViewModelProviders.of(this).get(ReadingViewModel.class);
        logInViewModel = ViewModelProviders.of(this).get(LogInViewModel.class);

        cal = Calendar.getInstance();
        date.setText(cal.get(Calendar.MONTH)+1+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR));

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDay = dayOfMonth;
                selectedYear = year;
                selectedMonth = month+1;
                date.setText(selectedMonth+"/"+selectedDay+"/"+selectedYear);

                try {
                   // Log.d("shit", apiProtocol+apiIp+apiPort+apiReadingsRequest+selectedYear+"-"+selectedMonth+"-"+selectedDay);
                    viewModel.getReadings(apiProtocol+apiIp+apiPort+apiReadingsRequest+selectedYear+"-"+selectedMonth+"-"+selectedDay);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        };

       // date.setClickable(true);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        fadeBackground = findViewById(R.id.fadeBackground);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        vPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapter);
        vPager.getAdapter();
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity.getInstance().position = position;
            updateReadingsUi(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        settingsFragmentPlaceholder = findViewById(R.id.settingsFragmentPlaceholder);

        //Login elements
        adminButton = findViewById(R.id.ButtonForAdmins);
        //adminButton.setVisibility(View.GONE);


        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(vPager);


        getReading = findViewById(R.id.getReading);




        //Get latest reading and readings
        try {

            viewModel.getReading(apiProtocol+apiIp+apiPort+apiReadingRequest);
            //Readings for today
            selectedDay = cal.get(Calendar.DAY_OF_MONTH);
            selectedMonth = cal.get(Calendar.MONTH)+1;
            selectedYear = cal.get(Calendar.YEAR);
            viewModel.getReadings(apiProtocol+apiIp+apiPort+apiReadingsRequest+selectedYear+"-"+selectedMonth+"-"+selectedDay);
            Log.d("shit", String.valueOf(selectedMonth));
        } catch (IOException ex){
            ex.printStackTrace();
        }




        //Open Window
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Open Window
                    viewModel.sendOpenWindow(apiProtocol+apiIp+apiPort+apiWindowRequest);
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        //Refresh button listener
        getReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //Get reading
                    viewModel.getReading(apiProtocol+apiIp+apiPort+apiReadingRequest);
                    viewModel.getReadings(apiProtocol+apiIp+apiPort+apiReadingsRequest+selectedYear+"-"+selectedMonth+"-"+selectedDay);

                }
                catch (IOException ex){
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


        //LOGIN
        //Checking on startup for already being signed in
       String checker =  logInViewModel.getAdminIsLoggedInCheck();
       if (checker.matches("loggedout") || checker.matches("failed") || checker.matches("invalid"))
           adminButton.setVisibility(View.GONE);
       else
           adminButton.setVisibility(View.VISIBLE);



        MutableLiveData<String> loginLiveData = logInViewModel.getLoginLiveData();
        //MutableLiveData<String> readingSignOutLiveData = logInViewModel.AdminSignOut();


        loginLiveData.observe(this, new Observer<String>()
        {
            public void onChanged(String s)
            {
                if (s.matches("loggedout")) {
                    adminButton.setVisibility(View.GONE);
                }
                else if (s.matches("invalid") || s.matches("failed"))
                {
                    adminButton.setVisibility(View.GONE);
                }
                else
                {
                    adminButton.setVisibility(View.VISIBLE);
                }
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
                //Add settings fragment but check if it already exists
                fragment = getSupportFragmentManager().findFragmentById(R.id.settingsFragmentPlaceholder);
                if (fragment instanceof SettingsFragment)
                    return true;
                else
            {
                fadeBackground();
                fragment = new SettingsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.settingsFragmentPlaceholder, fragment);
                ft.commit();
            }
                return true;
            case R.id.action_login:
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                return true;
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

        updateReadingsUi(position);
    }

    public void updateReadingsUi(int position)
    {
        int j;
        Log.d("nikita", readings.toString());
        //Log.d("nik", String.valueOf(position));
        switch (position) {

            case 0:
                j=0;

                    for (int i = 0; i < displays.length; i++) {
                        if (i == Integer.parseInt(readings.get(j).getDateTime())) {

                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            if (prefs.getBoolean("imperial", false) == false)
                                displays[i].setText(String.valueOf((int) readings.get(j).getTemperature() + " C" + "°"));
                            else
                                displays[i].setText(String.valueOf((int) (readings.get(j).getTemperature() * 9 / 5) + 32) + " F" + "°");

                            if (j < readings.size() - 1)
                                j++;

                            if (readings.get(0).getTemperature() == 666)
                                displays[0].setText("N/A");
                        } else
                            displays[i].setText("N/A");
                    }
                break;
            case 1:
                j = 0;
                for (int i = 0; i < displays.length; i++) {
                    if (i == Integer.parseInt(readings.get(j).getDateTime())) {
                        displays[i].setText(String.valueOf((int) readings.get(j).getHumidity() + " %"));
                        if (j < readings.size()-1)
                    j++;


                        if (readings.get(0).getTemperature() == 666)
                            displays[0].setText("N/A");
                    }
                    else
                        displays[i].setText("N/A");
                }
                break;
            case 2:
                j = 0;
                for (int i = 0; i < displays.length; i++) {
                    if (i == Integer.parseInt(readings.get(j).getDateTime())) {
                        displays[i].setText(String.valueOf((int) readings.get(j).getCo2() + " ppm"));
                        if (j < readings.size()-1)
                        j++;


                        if (readings.get(0).getTemperature() == 666)
                            displays[0].setText("N/A");
                    }
                    else
                        displays[i].setText("N/A");
                }
                break;
            case 3:
                j = 0;
                for (int i = 0; i < displays.length; i++) {
                    if (i == Integer.parseInt(readings.get(j).getDateTime())) {
                        displays[i].setText(String.valueOf((int) readings.get(j).getLight() + " lux"));
                        if (j < readings.size()-1)
                        j++;


                        if (readings.get(0).getTemperature() == 666)
                            displays[0].setText("N/A");
                    }
                    else
                        displays[i].setText("N/A");
                }
                break;
            default:
                break;
            }

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

    public void fadeBackground()
    {
        settingsFragmentPlaceholder.setClickable(true);
        fadeBackground.animate().alpha(0.5f); //grey out value

    }

    public void unFadeBackground()
    {
        settingsFragmentPlaceholder.setClickable(false);
        fadeBackground.animate().alpha(0.0f);
    }

    //Make sure app doesn't close due to pressing back.
    @Override
    public void onBackPressed()
    {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.settingsFragmentPlaceholder);
        if (fragment instanceof SettingsFragment)
        {
            date.setEnabled(true);
            unFadeBackground();
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        else
        {
            super.onBackPressed();
        }
    }

public int getPosition()
{
    return position;
}

}
