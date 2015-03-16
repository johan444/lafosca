package com.example.joan.lafosca.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Joan on 16/3/15.
 */
public class UserBody {

    @Expose
    private User user;

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }
}