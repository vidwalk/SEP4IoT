package com.application.cmapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.cmapp.model.Reading;
import com.application.cmapp.repository.Repository;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ReadingViewModel extends ViewModel {
    private final Repository repository = Repository.getInstance();



    @NonNull
    public void getReading(String requestUrl) throws MalformedURLException {
        repository.getReading(requestUrl);
    }

    @NonNull
    public void getReadings(String requestUrl) throws MalformedURLException {
        repository.getReadings(requestUrl);
    }

    @NonNull
    public void sendOpenWindow(String requestUrl) throws MalformedURLException
    {
        repository.sendOpenWindow(requestUrl);
    }

    @NonNull
    public MutableLiveData<Reading> getReadingLiveData()
    {
        return repository.getLiveReadingData();
    }

    @NonNull
    public MutableLiveData<ArrayList<Reading>> getMultipleReadingsLiveData()
    {
        return repository.getMultipleReadings();
    }


}
