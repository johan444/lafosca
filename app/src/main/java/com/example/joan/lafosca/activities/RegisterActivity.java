package com.example.joan.lafosca.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.JsonWriter;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joan.lafosca.HttpManager;
import com.example.joan.lafosca.R;
import com.example.joan.lafosca.RequestPackage;

import java.io.StringWriter;

public class RegisterActivity extends ActionBarActivity {

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        pb = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void registerUser(View v) {

        if(isOnline()) {

            // Gets the data from the input textfields
            EditText et_name = (EditText)findViewById(R.id.name);
            EditText et_password = (EditText)findViewById(R.id.password);
            String name = et_name.getText().toString();
            String password = et_password.getText().toString();

            if(name.isEmpty() || password.isEmpty()) {

                // If there is some field left empty
                Toast.makeText(this, "Some fields are empty", Toast.LENGTH_LONG).show();
            } else {

                // Generates the request with the user information
                requestData("http://lafosca-beach.herokuapp.com/api/v1/users", name, password);
            }

        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    protected void goToLogin() {

        // When registration is complete this creates the new activity
        Toast.makeText(RegisterActivity.this, "You have registered successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void requestData(String uri, String name, String password) {

        // Creation of the request package which contains all the parameters to
        //   deal with the server
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri(uri);
        p.setID("REGISTER");
        p.setSuccessCode("201");

        // String to JSON message conversion
        StringWriter out = new StringWriter();
        JsonWriter msg = new JsonWriter(out);

        try {
            msg.beginObject();
            msg.name("user");
                msg.beginObject();
                msg.name("username").value(name);
                msg.name("password").value(password);
                msg.endObject();
            msg.endObject();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // User params
        p.setParam("msg",out.toString());

        // Creates and executes the Asynchronous task which communicates with the server
        Task task = new Task();
        task.execute(p);
    }

    protected boolean isOnline(){

        // Check if the mobile has access to the net
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        } else {
            return false;
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
                Toast.makeText(RegisterActivity.this, "can't connect to web service", Toast.LENGTH_LONG).show();
                return;
            }

            // Go to next activity
            goToLogin();
        }
    }
}
