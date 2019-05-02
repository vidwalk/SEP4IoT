package com.application.cmapp.network;

import com.application.cmapp.model.Reading;

import org.json.JSONException;

public interface ClientCallback {

    public void onResponseReceived(String jsonString) throws JSONException;
    public void onFailed();
}
