package com.akkru.user.akkru.api.model.SetExpense;

import com.google.gson.annotations.SerializedName;

public class Avatar{

	@SerializedName("url")
	private Object url;

	public void setUrl(Object url){
		this.url = url;
	}

	public Object getUrl(){
		return url;
	}
}