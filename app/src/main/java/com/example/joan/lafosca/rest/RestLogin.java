package com.example.joan.lafosca.rest;

import com.example.joan.lafosca.model.UserBodyLogin;
import com.example.joan.lafosca.model.UserBodyRegister;

import javax.security.auth.callback.Callback;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Joan on 16/3/15.
 */
public interface RestLogin {
    @GET("/user")
    public void login(@Header("Authorization") String authorization,
                          @Query("username") String name, @Query("password") String pwd, retrofit.Callback<UserBodyLogin> cb);
}
