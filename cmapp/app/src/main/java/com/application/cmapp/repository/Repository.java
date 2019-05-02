package com.application.cmapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.application.cmapp.network.Adapter;
import com.application.cmapp.model.Reading;
import com.application.cmapp.network.WebAPIClient;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class Repository {

    private static Repository instance = new Repository();
    private MutableLiveData<Reading> liveReadingData = new MutableLiveData<Reading>();
    private final WebAPIClient client = new WebAPIClient();
    private final Adapter adapter = new Adapter();
    private WebAPIClient.ReadingAsyncTask task;

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
                liveReadingData.postValue(new Reading(999, 999, 999, 999, 999, ""));
            }
        };
        //Execute the task.
        task.execute(requestUrl);
    }

    public MutableLiveData<Reading> getLiveReadingData()
    {
        return liveReadingData;
    }

//Add firebase communication for login.

}
