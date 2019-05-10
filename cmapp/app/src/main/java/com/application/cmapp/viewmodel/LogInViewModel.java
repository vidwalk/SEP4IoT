package com.application.cmapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.cmapp.model.Reading;
import com.application.cmapp.repository.Repository;

public class LogInViewModel extends ViewModel {

    private final Repository repository = Repository.getInstance();


    @NonNull
    public MutableLiveData<String> getLoginLiveData(String userEmail, String userPass)
    {
        Log.i("VM=====", "reaches");
        return repository.getAdminLogin(userEmail,userPass);
    }

    public MutableLiveData<String> getAdminIsLoggedInCheck()
    {
        return repository.AdminIsLoggedInCheck();
    }

    public MutableLiveData<String>  AdminSignOut()
    {
        Log.i("VMquit=====", "reaches");
        return repository.AdminSignOut();
    }

}