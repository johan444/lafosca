package com.example.joan.lafosca.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joan.lafosca.R;
import com.example.joan.lafosca.RequestPackage;
import com.example.joan.lafosca.model.UserBodyLogin;
import com.example.joan.lafosca.rest.RestController;

import java.io.BufferedReader;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends ActionBarActivity {

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        pb = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void validateLogin(View v){

        if(isOnline()) {

            // Get the data from the input textfields
            EditText et_name = (EditText)findViewById(R.id.name);
            EditText et_password = (EditText)findViewById(R.id.password);
            String username = et_name.getText().toString();
            String password = et_password.getText().toString();

            if(username.isEmpty() || password.isEmpty()) {
                // If there is some field left empty
                Toast.makeText(this, "Some fields are empty", Toast.LENGTH_LONG).show();
            } else {

                // Creation the Basic Authentication token from the user params
                BufferedReader reader = null;
                byte[] loginBytes = (username + ":" + password).getBytes();
                StringBuilder loginBuilder = new StringBuilder()
                        .append("Basic ")
                        .append(Base64.encodeToString(loginBytes, Base64.NO_WRAP));

                String token = loginBuilder.toString();

                pb.setVisibility(View.VISIBLE);

                try {
                    RestController.getInstance().getRestLogin().login(token,username, password,
                            new retrofit.Callback<UserBodyLogin>() {

                                @Override
                                public void success(UserBodyLogin o, Response response) {
                                    pb.setVisibility(View.INVISIBLE);
                                    goToApp(o);
                                }

                                @Override
                                public void failure(RetrofitError retrofitError) {
                                    pb.setVisibility(View.INVISIBLE);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    public void goToRegister(View v) {

        // When clicking to the link of register this creates the new activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    protected boolean isOnline() {

        // Check if the mobile has access to the net
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        } else {
            return false;
        }
    }

    protected void goToApp(UserBodyLogin msg) {

        Toast.makeText(LoginActivity.this, "welcome!!", Toast.LENGTH_LONG).show();

        // Get authorization code
        String token = msg.getAuthenticationToken();

        // Create the app activity and send the authorizaation data
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

}
