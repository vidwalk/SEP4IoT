package com.application.cmapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.application.cmapp.R;
import com.application.cmapp.viewmodel.LogInViewModel;
import com.application.cmapp.viewmodel.ReadingViewModel;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail;
    EditText userPass;
    private TextView loggedIn;
    Button userLogin;
    Button userSignOut;
    LogInViewModel viewModel;
    String isUserLoggedIn;

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
        loggedIn = findViewById(R.id.loggedIn);

        viewModel = ViewModelProviders.of(this).get(LogInViewModel.class);

        isUserLoggedIn = viewModel.getAdminIsLoggedInCheck();

        if (isUserLoggedIn.matches("loggedout") || isUserLoggedIn.matches("failed") || isUserLoggedIn.matches("invalid")) {
            loggedIn.setText("");
            userEmail.setVisibility(View.VISIBLE);
            userPass.setVisibility(View.VISIBLE);
            userLogin.setVisibility(View.VISIBLE);
            userSignOut.setVisibility(View.GONE);
        }
        else
        {
            loggedIn.setText("Logged in with e-mail: "+isUserLoggedIn);
            userEmail.setVisibility(View.GONE);
            userPass.setVisibility(View.GONE);
            userLogin.setVisibility(View.GONE);
            userSignOut.setVisibility(View.VISIBLE);
        }

        MutableLiveData<String> loginLiveData = viewModel.getLoginLiveData();



        loginLiveData.observe(this, new Observer<String>()
        {
            public void onChanged(String s)
            {
                if (s.matches("loggedout")) {
                    loggedIn.setText("");
                    userEmail.setVisibility(View.VISIBLE);
                    userPass.setVisibility(View.VISIBLE);
                    userLogin.setVisibility(View.VISIBLE);
                    userSignOut.setVisibility(View.GONE);
                    if (userEmail.getText().toString().matches("")) {
                        Toast toast = Toast.makeText(LoginActivity.this, "Logged out!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else if (s.matches("invalid"))
                {
                    loggedIn.setText("");
                    userEmail.setVisibility(View.VISIBLE);
                    userPass.setVisibility(View.VISIBLE);
                    userLogin.setVisibility(View.VISIBLE);
                    userSignOut.setVisibility(View.GONE);
                    if (userEmail.getText().toString().matches("")) {
                    }
                    else {
                        Toast toast = Toast.makeText(LoginActivity.this, "Invalid e-mail/password!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else if (s.matches("failed"))
                {
                    loggedIn.setText("");
                    userEmail.setVisibility(View.VISIBLE);
                    userPass.setVisibility(View.VISIBLE);
                    userLogin.setVisibility(View.VISIBLE);
                    userSignOut.setVisibility(View.GONE);
                    if (userEmail.getText().toString().matches("")) {
                    }
                    else {
                        Toast toast = Toast.makeText(LoginActivity.this, "Invalid e-mail/password!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else
                {
                    loggedIn.setText("Logged in with e-mail: "+s);
                    userEmail.setText("");
                    userPass.setText("");
                    userEmail.setVisibility(View.GONE);
                    userPass.setVisibility(View.GONE);
                    userLogin.setVisibility(View.GONE);
                    userSignOut.setVisibility(View.VISIBLE);

                    if (userEmail.getText().toString().matches(""))
                    {
                        Toast toast = Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        });


//check if user is still logged in
        /*

*/

        userLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (userEmail.getText().toString().matches(""))
                {
                   Toast toast = Toast.makeText(LoginActivity.this, "Please provide an e-mail!", Toast.LENGTH_SHORT);
                   toast.show();
                }
                else if (userPass.getText().toString().matches(""))
                {
                    Toast toast = Toast.makeText(LoginActivity.this, "Please provide a password!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    viewModel.adminLogIn(userEmail.getText().toString(), userPass.getText().toString());
                    Toast toast = Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        userSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                viewModel.AdminSignOut();
                Toast toast = Toast.makeText(LoginActivity.this, "Logging out...", Toast.LENGTH_LONG);
                toast.show();
            }

        });




    }
}
