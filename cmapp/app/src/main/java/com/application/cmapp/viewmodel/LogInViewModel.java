package com.application.cmapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.cmapp.model.Reading;
import com.application.cmapp.repository.Repository;

public class LogInViewModel extends ViewModel {

    private final Repository repository = Repository.getInstance();


    public void adminLogIn(String userEmail, String userPass)
    {
        repository.AdminLogin(userEmail, userPass);
    }

    public String getAdminIsLoggedInCheck()
    {
       return repository.AdminIsLoggedInCheck();
    }

    public void  AdminSignOut()
    {
        repository.AdminSignOut();
    }

    public MutableLiveData<String> getLoginLiveData()
    {
        return repository.getLiveDataLogin();
    }

}