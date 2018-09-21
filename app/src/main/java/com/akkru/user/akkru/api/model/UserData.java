package com.akkru.user.akkru.api.model;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("password_confirmation")
    private String password_conf;
    @SerializedName("avatar")
    private Avatar avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_conf() {
        return password_conf;
    }

    public void setPassword_conf(String password_conf) {
        this.password_conf = password_conf;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public UserData(String name, String email, String password, String password_conf ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_conf = password_conf;

    }

}
