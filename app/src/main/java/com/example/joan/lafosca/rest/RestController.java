package com.example.joan.lafosca.rest;

import com.example.joan.lafosca.BuildConfig;
import com.example.joan.lafosca.common.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Joan on 16/3/15.
 */
public class RestController {

    private static  RestController sInstance;

    public static RestController getInstance() {
        if (sInstance == null) {
            sInstance = new RestController();
        }

        return sInstance;
    }

    private RestRegister mRestRegister;

    private RestController() {
        setupRestRegister();
    }

    private void setupRestRegister() {


        RestAdapter mAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");
                        request.addHeader("Accept", "application/json");
                    }
                })
                .setEndpoint(Constants.URL_BASE)
                .build();

        if (BuildConfig.DEBUG) {
            mAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
        }
        else {
            mAdapter.setLogLevel(RestAdapter.LogLevel.NONE);
        }

        mRestRegister = mAdapter.create(RestRegister.class);
    }

    public RestRegister getRestRegister() {
        return mRestRegister;
    }
}
