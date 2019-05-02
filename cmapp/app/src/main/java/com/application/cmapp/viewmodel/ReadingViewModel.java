package com.application.cmapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.cmapp.model.Reading;
import com.application.cmapp.repository.Repository;

import java.net.MalformedURLException;

public class ReadingViewModel extends ViewModel {
    private final Repository repository = Repository.getInstance();



    @NonNull
    public void getReading(String requestUrl) throws MalformedURLException {
        repository.getReading(requestUrl);
    }

    @NonNull
    public MutableLiveData<Reading> getReadingLiveData()
    {
        return repository.getLiveReadingData();
    }



}
