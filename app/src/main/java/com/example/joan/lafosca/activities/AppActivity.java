package com.example.joan.lafosca.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joan.lafosca.HttpManager;
import com.example.joan.lafosca.R;
import com.example.joan.lafosca.RequestPackage;
import com.example.joan.lafosca.model.ModelKid;
import com.example.joan.lafosca.model.ModelState;
import com.example.joan.lafosca.rest.RestController;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AppActivity extends ActionBarActivity {

    private String token;
    private String state;
    private ProgressBar pb;
    private static String URL = "http://lafosca-beach.herokuapp.com/api/v1";
    private List<ModelKid> kidsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        // Get JSON data coming from the login
        Bundle data = getIntent().getExtras();

        if ( data != null) {

            // Get the Authorization Token
            this.token = "Token token="+data.getString("token");

            // Start an asynchronous task to connect with the server
            // and get the information about the beach
            //getState();
            RestController.getInstance().getRestApp().getState(token,new Callback<ModelState>() {
                @Override
                public void success(ModelState modelState, Response response) {
                    LinearLayout ll = (LinearLayout) findViewById(R.id.beachOpenLayout);
                    Button btnClean = (Button) findViewById(R.id.btnClean);

                    // In case the beach is open we can display all the information
                    if (modelState.getState().equals("open")) {

                        ll.setVisibility(View.VISIBLE);
                        btnClean.setVisibility(View.INVISIBLE);

                        TextView txtFlag = (TextView) findViewById(R.id.txtFlag);
                        String flag = modelState.getFlag().toString();
                        txtFlag.setText(flag);

                        TextView txtHappiness = (TextView) findViewById(R.id.txtHappiness);
                        String happiness = modelState.getHappiness().toString();
                        txtHappiness.setText(happiness);

                        TextView txtDirtiness = (TextView) findViewById(R.id.txtDirtiness);
                        String dirtiness = modelState.getDirtiness().toString();
                        txtDirtiness.setText(dirtiness);

                        kidsList = modelState.getKids();

                    } else {

                        // Otherwise we set it to invisible
                        ll.setVisibility(View.INVISIBLE);
                        btnClean.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            // Bind buttons to a click listener
            Button btnState = (Button) findViewById(R.id.btnState);
            btnState.setOnClickListener(clickListener);

            Button btnClean = (Button) findViewById(R.id.btnClean);
            btnClean.setOnClickListener(clickListener);

            Button btnNivea = (Button) findViewById(R.id.btnNivea);
            btnNivea.setOnClickListener(clickListener);

            ImageButton btnGreenFlag = (ImageButton) findViewById(R.id.greenFlag);
            btnGreenFlag.setOnClickListener(clickListener);

            ImageButton btnYelFlag = (ImageButton) findViewById(R.id.yelFlag);
            btnYelFlag.setOnClickListener(clickListener);

            ImageButton btnRedFlag = (ImageButton) findViewById(R.id.redFlag);
            btnRedFlag.setOnClickListener(clickListener);

            Button btnKids = (Button) findViewById(R.id.btnKids);
            btnKids.setOnClickListener(clickListener);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        // Same clickListener for all the buttons

        @Override
        public void onClick(View v) {
            String flag = "";
            String msg;
            switch (v.getId()) {

                case R.id.btnKids :
                    if (kidsList != null) {

                        Intent intent = new Intent(AppActivity.this, KidsActivity.class);
                        Bundle b = new Bundle();

                        b.putParcelableArrayList("kids", (ArrayList) kidsList);
                        intent.putExtras(b);

                        startActivity(intent);
                    }

                    break;

                case R.id.greenFlag :

                        // Flag number
                        flag = "0";

                        // Creates a JSON and then gets the String back resulting
                        msg = parseFlagMsg(flag);

                        // Sends the message to the server
                        requestData(URL+"/flag", "FLAG", "PUT", msg);

                    break;

                case R.id.yelFlag :

                        // Flag number
                        flag = "1";

                        // Creates a JSON and then gets the String back resulting
                        msg = parseFlagMsg(flag);

                        // Sends the message to the server
                        requestData(URL+"/flag", "FLAG", "PUT", msg);

                    break;

                case R.id.redFlag :

                        // Flag number
                        flag = "2";

                        // Creates a JSON and then gets the String back resulting
                        msg = parseFlagMsg(flag);

                        // Sends the message to the server
                        requestData(URL+"/flag", "FLAG", "PUT", msg);

                    break;

                case R.id.btnClean :

                        // Cleans the dirtiness value of the beach
                        clean();

                    break;

                case R.id.btnState :

                        // Changes the state of the beach (open / closed)
                        changeState();
                    break;

                case R.id.btnNivea :

                        // Resets the happiness value to level 100
                        niveaBalls();
            }
        }
    };

    protected String parseFlagMsg(String idFlag) {

        // String to JSON and back to String conversion
        StringWriter out = new StringWriter();
        JsonWriter msg = new JsonWriter(out);

        try {
            msg.beginObject();
                msg.name("flag").value(idFlag);
            msg.endObject();
            return out.toString();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void getState() {
        if(isOnline()) {

            // Generates the request with the user information
            requestData(URL + "/state", "STATE", "GET", null);

        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    private void requestData(String uri, String id, String method, String msg) {

        // Creation of the request package which contains all the parameters to
        //   deal with the server for each case
        RequestPackage p = new RequestPackage();
        p.setMethod(method);
        p.setUri(uri);
        p.setID(id);

        switch (id) {
            case "FLAG" :
                    p.setSuccessCode("204");
                break;
            case "CLEAN" :
                    p.setSuccessCode("201");
                break;
            case "STATE" :
                    p.setSuccessCode("200");
                break;
        }

        p.setParam("msg",msg);

        // Set Authorization Token
        p.setParam("token",this.token);

        // Creates and executes the Asynchronous task which communicates with the server
        Task task = new Task();
        task.execute(p);
    }

    protected void showBeachState(String data) {

        try {

            // Get the message from the beach state
            JSONObject jsn = new JSONObject(data);
            this.state = jsn.getString("state");
            TextView txtState = (TextView) findViewById(R.id.txtState);
            txtState.setText(state);

            LinearLayout ll = (LinearLayout) findViewById(R.id.beachOpenLayout);
            Button btnClean = (Button) findViewById(R.id.btnClean);

            // In case the beach is open we can display all the information
            if (state.equals("open")) {

                ll.setVisibility(View.VISIBLE);
                btnClean.setVisibility(View.INVISIBLE);

                TextView txtFlag = (TextView) findViewById(R.id.txtFlag);
                String flag = jsn.getString("flag");
                txtFlag.setText(flag);

                TextView txtHappiness = (TextView) findViewById(R.id.txtHappiness);
                String happiness = jsn.getString("happiness");
                txtHappiness.setText(happiness);

                TextView txtDirtiness = (TextView) findViewById(R.id.txtDirtiness);
                String dirtiness = jsn.getString("dirtiness");
                txtDirtiness.setText(dirtiness);

            } else {

                // Otherwise we set it to invisible
                ll.setVisibility(View.INVISIBLE);
                btnClean.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void changeState() {
        if(isOnline()) {
            if(this.state != null ) {

                // Change the url in order to close or open the beach through a PUT request method
                String uri;
                if (this.state.equals("open")) {
                     uri = "/close";
                } else {
                     uri = "/open";
                }
                requestData(URL+uri, "CHANGE", "PUT", null);
                getState();
            }
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    protected void clean() {
        if(isOnline()) {
            if(this.state != null ) {

                // Clean the beach only if it's closed
                if (this.state.equals("closed")) {
                    requestData(URL+"/clean", "CLEAN", "POST", null);
                }
            }
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    protected void niveaBalls() {
        if(isOnline()) {
            if(this.state != null ) {

                // Clean the beach only if it's closed
                if (this.state.equals("open")) {
                    requestData(URL+"/nivea-rain", "NIVEA", "POST", null);
                }
            }
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
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

    private class Task extends AsyncTask<RequestPackage, String, RequestPackage> {

        @Override
        protected void onPreExecute() {

            // Set progress bar visible
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected RequestPackage doInBackground(RequestPackage... params) {

            // Establish connection and receive content from the server
            params[0].setParam("content", HttpManager.getResponse(params[0]));
            return params[0];
        }

        @Override
        protected void onPostExecute(RequestPackage result) {

            // Set progress bar invisible
            pb.setVisibility(View.INVISIBLE);

            String id = result.getID();

            // In case the data requested is from the beach state or not
            // If is not the data is refreshed by calling getState()
            if(id.equals("STATE")) {
                try{
                    JSONObject jsn = new JSONObject(result.getParams("content"));
                    showBeachState(jsn.toString());
                }catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                getState();
            }
        }
    }
}
