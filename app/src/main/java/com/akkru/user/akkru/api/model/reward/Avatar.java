package com.akkru.user.akkru.api.model.reward;

import com.google.gson.annotations.SerializedName;

public class Avatar{

	@SerializedName("url")
	private String url;

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}
}