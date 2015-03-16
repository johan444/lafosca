package com.example.joan.lafosca;

import android.util.Log;

import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joan on 03/03/2015.
 */
public class HttpManager {

    public static String getResponse(RequestPackage p) {

        // Creation of the reader class which will hold the response message from the server
        BufferedReader reader = null;

        // Creation of the connection class
        HttpURLConnection con = null;

        String uri = p.getUri();

        try {
            // In the login case the message needs to be appended to the url
            if (p.getID().equals("LOGIN")) {
                uri += "?" + p.getParams("msg");
            }

            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();

            // Set the header params
            con.setRequestMethod(p.getMethod());
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            // Token auth for the login request
            if (p.getID().equals("LOGIN")) {
                con.addRequestProperty("Authorization",p.getParams("token"));
            }

            // Token auth for the functions where the token authorization is needed
            //  once the user has made the login
            if (!p.getID().equals("REGISTER") && !p.getID().equals("LOGIN")) {
                con.addRequestProperty("Authorization","Token token="+p.getParams("token"));

            }

            // Post request for the register section
            if (p.getID().equals("REGISTER") || p.getID().equals("FLAG")) {
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(p.getParams("msg"));
                writer.flush();
            }

            // Code received
            String status = String.valueOf(con.getResponseCode());
            Log.d("response code ", status);

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            // If the code corresponds to the aspected successfull code then the message is returned
            if(status.equals(p.getSuccessCode())) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                return sb.toString();
            } else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }
}
