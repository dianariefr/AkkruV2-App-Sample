package com.akkru.user.akkru.api.model;

import com.google.gson.annotations.SerializedName;


public class UserInfo{

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("name")
        private String name;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("password_digest")
        private String passwordDigest;

        @SerializedName("avatar")
        private Avatar avatar;

        @SerializedName("email")
        private String email;

        @SerializedName("point")
        private Object point;

        public void setUpdatedAt(String updatedAt){
            this.updatedAt = updatedAt;
        }

        public String getUpdatedAt(){
            return updatedAt;
        }

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public void setCreatedAt(String createdAt){
            this.createdAt = createdAt;
        }

        public String getCreatedAt(){
            return createdAt;
        }

        public void setId(int id){
            this.id = id;
        }

        public int getId(){
            return id;
        }

        public void setPasswordDigest(String passwordDigest){
            this.passwordDigest = passwordDigest;
        }

        public String getPasswordDigest(){
            return passwordDigest;
        }

        public void setAvatar(Avatar avatar){
            this.avatar = avatar;
        }

        public Avatar getAvatar(){
            return avatar;
        }

        public void setEmail(String email){
            this.email = email;
        }

        public String getEmail(){
            return email;
        }

        public void setPoint(Object point){
            this.point = point;
        }

        public Object getPoint(){
            return point;
        }

    }

