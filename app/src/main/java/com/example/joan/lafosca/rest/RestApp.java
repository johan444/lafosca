package com.example.joan.lafosca.rest;

import com.example.joan.lafosca.model.ModelState;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by Joan on 17/3/15.
 */
public interface RestApp {
    @GET("/state")
    public void getState(@Header("Authorization") String authorization, Callback<ModelState> cb);


}
