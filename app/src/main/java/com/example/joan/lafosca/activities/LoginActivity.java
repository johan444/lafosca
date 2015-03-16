package com.example.joan.lafosca.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joan.lafosca.HttpManager;
import com.example.joan.lafosca.R;
import com.example.joan.lafosca.RequestPackage;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;


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
            String name = et_name.getText().toString();
            String password = et_password.getText().toString();

            if(name.isEmpty() || password.isEmpty()) {
                // If there is some field left empty
                Toast.makeText(this, "Some fields are empty", Toast.LENGTH_LONG).show();
            } else {
                // Generate the request with the user information
                requestData("http://lafosca-beach.herokuapp.com/api/v1/user", name, password);
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

    private void requestData(String uri, String name, String password) {

        // Creation of the request package which contains all the parameters to
        //   deal with the server
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(uri);
        p.setID("LOGIN");
        p.setSuccessCode("200");

        // Creation the Basic Authentication token from the user params
        BufferedReader reader = null;
        byte[] loginBytes = (name + ":" + password).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.NO_WRAP));
        p.setParam("token",loginBuilder.toString());

        // User params
        p.setParam("msg","username="+name+"&"+"password="+password);

        Task task = new Task();
        task.execute(p);
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

    protected void goToApp(String result) {

        Toast.makeText(LoginActivity.this, "welcome!!", Toast.LENGTH_LONG).show();

        try {
            // Get authorization code
            JSONObject jsn = new JSONObject(result);
            String token = jsn.getString("authentication_token");

            // Create the app activity and send the authorizaation data
            Intent intent = new Intent(this, AppActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class Task extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {

            // Set progress bar visible
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(RequestPackage... params) {

            // Establish connection and receive content from the server
            String content = HttpManager.getResponse(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            // Set progress bar invisible
            pb.setVisibility(View.INVISIBLE);

            // Check if there is any response
            if(result == null){
                Toast.makeText(LoginActivity.this, "can't connect to web service", Toast.LENGTH_LONG).show();
                return;
            }

            // Go to next activity
            goToApp(result);
        }
    }
}
