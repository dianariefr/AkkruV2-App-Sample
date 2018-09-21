package com.akkru.user.akkru.api.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("jwt")
    private String token;
    @SerializedName("email")
    private String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
