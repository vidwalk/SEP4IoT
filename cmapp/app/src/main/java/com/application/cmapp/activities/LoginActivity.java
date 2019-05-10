package com.application.cmapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.application.cmapp.R;
import com.application.cmapp.viewmodel.LogInViewModel;
import com.application.cmapp.viewmodel.ReadingViewModel;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail;
    EditText userPass;
    Button userLogin;
    Button userSignOut;
    LogInViewModel viewModel;

    MainActivity activityReferenceVarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userEmail = findViewById(R.id.username);
        userPass = findViewById(R.id.password);
        userLogin = findViewById(R.id.login);
        userSignOut = findViewById(R.id.signOutBtn);

        viewModel = ViewModelProviders.of(this).get(LogInViewModel.class);

        userLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viewModel.getLoginLiveData(userEmail.getText().toString(), userPass.getText().toString());
                Log.i("ActivityLogIn=====", "reaches");

                finish();
            }
        });

        userSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                viewModel.AdminSignOut();
                finish();
            }

        });

    }
}
