package com.akkru.user.akkru.api.model;

import com.google.gson.annotations.SerializedName;

public class EditUser {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("password_confirmation")
    private String password_conf;
    @SerializedName("avatar")
    private Avatar avatar;

    public EditUser(String email, String password, String password_conf) {
        this.email = email;
        this.password = password;
        this.password_conf = password_conf;
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
}
