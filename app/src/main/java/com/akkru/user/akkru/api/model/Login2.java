package com.akkru.user.akkru.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login2 {
    @SerializedName("auth")
    private Auth auth;

    public Login2(Auth auth) {
        this.auth = auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Auth getAuth() {
        return auth;
    }
}