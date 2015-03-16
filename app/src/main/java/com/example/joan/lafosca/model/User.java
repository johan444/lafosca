package com.example.joan.lafosca.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Joan on 16/3/15.
 */
public class User {

    @Expose
    private String username;
    @Expose
    private String password;

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
