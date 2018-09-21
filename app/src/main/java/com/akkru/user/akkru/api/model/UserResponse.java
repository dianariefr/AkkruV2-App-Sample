package com.akkru.user.akkru.api.model;

import com.google.gson.annotations.SerializedName;

public class UserResponse{

    @SerializedName("result")
    private boolean result;

    @SerializedName("user")
    private UserInfo user;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}