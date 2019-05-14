package com.application.cmapp.firebase;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.application.cmapp.repository.Repository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

public class FirebaseClient {


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser(); // may be null if admin is not logged in
    public String loginReply = "Not logged in.";
    public DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    public FirebaseClient()
    {

    }

    public void AdminLogin (final String userEmail, String userPass, final FirebaseCallback firebaseCallback)  //changes LD
    {

        mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //loginReply = userEmail;
                            firebaseCallback.onResponseReceived();

                        }
                        else{
                            //loginReply = "Failed Login";
                            firebaseCallback.onFailed();
                        }
                    }
                });

    }





    @SuppressLint("LongLogTag")
    public String AdminIsLoggedInCheck()
    {

        if(firebaseUser != null){
            return (firebaseUser.getEmail());
        }
        else{
            return "loggedout";
        }


    }

    public void AdminSignOut()
    {
        mAuth.signOut();
    }

    public class LogOutAsyncTask extends AsyncTask<String, String, String> implements FirebaseCallback {

        @Override
        protected String doInBackground(String... strings)
        {
            AdminSignOut();
            return "loggedout";
        }

        @Override
        protected void onPostExecute(String jsonString)
        {
            onResponseReceived();
        }

        @Override
        public void onResponseReceived()
        {
        }

        @Override
        public void onFailed()
        {

        }
    }

}