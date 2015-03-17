package com.example.joan.lafosca.rest;

import com.example.joan.lafosca.model.UserBodyRegister;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Joan on 16/3/15.
 */
public interface RestRegister {
    @POST("/users")
    public Response register(@Body UserBodyRegister userBody);
}
