package com.application.cmapp.network;

import com.application.cmapp.model.Reading;

import org.json.JSONException;

public interface ClientCallback {

    void onResponseReceived(String jsonString) throws JSONException;
    void onFailed();
    void onDataSent(String jsonString);
}
