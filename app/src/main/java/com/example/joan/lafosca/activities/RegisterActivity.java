package com.example.joan.lafosca.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joan.lafosca.R;
import com.example.joan.lafosca.model.UserBodyRegister;
import com.example.joan.lafosca.model.UserRegister;
import com.example.joan.lafosca.rest.RestController;

import retrofit.RetrofitError;
import retrofit.client.Response;

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
            String username = et_name.getText().toString();
            String password = et_password.getText().toString();

            if(username.isEmpty() || password.isEmpty()) {

                // If there is some field left empty
                Toast.makeText(this, "Some fields are empty", Toast.LENGTH_LONG).show();
            } else {

                // Generates the request with the user information
                // requestData("http://lafosca-beach.herokuapp.com/api/v1/users", name, password);

                UserBodyRegister userBody = new UserBodyRegister();
                UserRegister user = new UserRegister();
                user.setUsername(username);
                user.setPassword(password);
                userBody.setUserRegister(user);

                Task task = new Task();
                task.execute(userBody);
                /*Gson gson = new Gson();
                String jsonOutput = gson.toJson(userBody).toString();*/


                //Log.d("response ",response.toString());

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

    private class Task extends AsyncTask<UserBodyRegister, String, Response> {

        @Override
        protected void onPreExecute() {

            // Set progress bar visible
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Response doInBackground(UserBodyRegister... userBodyRegister) {

            // Establish connection and receive content from the server
            UserBodyRegister ub = userBodyRegister[0];
            Response response = null;
            try {
                 response = RestController.getInstance().getRestRegister().register(userBodyRegister[0]);
            } catch (RetrofitError error) {
                Log.e("error", error.toString());
            } catch (Exception e) {
                Log.e("error", e.toString());
            }

           return response;
        }

        @Override
        protected void onPostExecute(Response response) {

            // Set progress bar invisible
            pb.setVisibility(View.INVISIBLE);
            // Check if there is any response
            if(response == null || response.getStatus() != 201){
                Toast.makeText(RegisterActivity.this, "can't connect to web service", Toast.LENGTH_LONG).show();
                return;
            } else {
                Log.d("response ", response.toString());
                goToLogin();
            }
        }
    }
}
