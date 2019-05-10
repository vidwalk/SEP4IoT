package com.application.cmapp.firebase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseClient {


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser(); // may be null if admin is not logged in
    public String loginReply = "Anonymous user0";
    public DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();



    public String AdminLogin (final String userEmail, String userPass)  //changes LD
    {
        Log.i("FirebaseClientStart====", "reaches");

        mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            loginReply = userEmail;

                        }
                        else{
                            loginReply = "Anonymous user";
                        }
                    }
                });


        Log.i("loginReply====",loginReply+"");
        return loginReply;
    }





    @SuppressLint("LongLogTag")
    public String AdminIsLoggedInCheck()
    {
        Log.i("CheckASO===", "reaches");
        if(firebaseUser != null){
            return (firebaseUser.getEmail());
        }
        else{
            return "Anonymous user";

        }


    }

    public void AdminSignOut()
    {
        mAuth.signOut();
        if (firebaseUser == null) {
            Log.i("SIGNOUT=====", "success");
        }
    }


}