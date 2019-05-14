package com.application.cmapp.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.application.cmapp.firebase.FirebaseClient;
import com.application.cmapp.firebase.FirebaseCallback;
import com.application.cmapp.network.Adapter;
import com.application.cmapp.model.Reading;
import com.application.cmapp.network.WebAPIClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Repository {

    private static Repository instance = new Repository();
    private MutableLiveData<Reading> liveReadingData = new MutableLiveData<Reading>();
    private MutableLiveData<ArrayList<Reading>> liveMultipleReadings = new MutableLiveData<>();
    private final WebAPIClient client = new WebAPIClient();
    private final Adapter adapter = new Adapter();
    private WebAPIClient.ReadingAsyncTask task;
    private WebAPIClient.PutAsyncTask task2;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser(); // may be null if admin is not logged in

    //private MutableLiveData<String> liveDataLogin = new MutableLiveData<String>();
    public MutableLiveData<String> liveDataLogin = new MutableLiveData<String>();
    public boolean reply;
    private FirebaseClient fbClient = new FirebaseClient();
    private FirebaseClient.LogOutAsyncTask logOutAsyncTask;
   // private LogOutAsyncTask logOutTask ;


    private Repository()
    {

    }


    public static Repository getInstance()
    {
        return instance;
    }


    //API communication
    public void getReading(String requestUrl) throws MalformedURLException {
        task = client.new ReadingAsyncTask() {
            @Override
            public void onResponseReceived(String jsonResult) throws JSONException {
                //Successful response posts adapted value inside the live data.
                liveReadingData.postValue(adapter.makeReading(jsonResult));
            }

            @Override
            public void onFailed()
            {
                //Default values if response failed.
                liveReadingData.postValue(new Reading(999, 999, 999, 999, ""));
            }
        };
        //Execute the task.
        task.execute(requestUrl);
    }

    public void getReadings(String requestUrl) throws MalformedURLException {
        task = client.new ReadingAsyncTask() {
            @Override
            public void onResponseReceived(String jsonResult) throws JSONException {
                //Successful response posts adapted value inside the live data.
                liveMultipleReadings.postValue(adapter.makeMultipleReadings(jsonResult));
                Log.d("cacat", adapter.makeMultipleReadings(jsonResult).toString());
            }


            @Override
            public void onFailed()
            {
                //Default values if response failed.
                ArrayList<Reading> failedArray = new ArrayList<Reading>();
                failedArray.add(new Reading(0, 0, 0, 0, ""));

            liveMultipleReadings.postValue(failedArray);
            }

        };
        //Execute the task
        task.execute(requestUrl);
    }

    public void sendOpenWindow(String requestUrl) throws MalformedURLException
    {
        task2 = client.new PutAsyncTask() {
            @Override
            public void onDataSent(String jsonString)
            {
                Log.d("cacat", jsonString);
            }

            @Override
            public void onFailed()
            {
                Log.d("cacat", "Opening window failed. Can't communicate with API.");
            }
        };
        //Execute the task
        task2.execute(requestUrl);
    }

    public MutableLiveData<Reading> getLiveReadingData()
    {
        return liveReadingData;
    }

    public MutableLiveData<ArrayList<Reading>> getMultipleReadings()
    {
        return liveMultipleReadings;
    }

//Add firebase communication for login.

    public void AdminLogin(final String userEmail, final String userPass)  //changes LD
    {

        fbClient.AdminLogin(userEmail, userPass, new FirebaseCallback() {

            @Override
            public void onResponseReceived()
            {
                liveDataLogin.postValue(userEmail);
            }

            @Override
            public void onFailed()
            {
                liveDataLogin.postValue("invalid");
            }

        });
        //liveDataLogin.postValue(fbClient.AdminLogin(userEmail, userPass));

    }

    public String AdminIsLoggedInCheck()
    {

        return fbClient.AdminIsLoggedInCheck();

    }


    public void AdminSignOut()
    {

        logOutAsyncTask = fbClient. new LogOutAsyncTask() {
            @Override
            public void onResponseReceived()
            {
                liveDataLogin.postValue("loggedout");
            }

            @Override
            public void onFailed()
            {
                liveDataLogin.postValue("failed");
            }
        };

        logOutAsyncTask.execute();
        //return liveDataLogin;

    }


    public MutableLiveData<String> getLiveDataLogin()
    {
        return liveDataLogin;
    }



}
