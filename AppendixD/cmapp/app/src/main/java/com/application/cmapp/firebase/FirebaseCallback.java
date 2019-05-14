package com.application.cmapp.firebase;

import org.json.JSONException;

public interface FirebaseCallback {

    void onResponseReceived();
    void onFailed();
}
