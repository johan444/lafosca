package com.example.joan.lafosca.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Joan on 16/3/15.
 */
public class UserBodyRegister {

    @SerializedName("user")
    private UserRegister userRegister;

    /**
     *
     * @return
     * The user
     */
    public UserRegister getUserRegister() {
        return userRegister;
    }

    /**
     *
     * @param userRegister
     * The user
     */
    public void setUserRegister(UserRegister userRegister) {
        this.userRegister = userRegister;
    }
}