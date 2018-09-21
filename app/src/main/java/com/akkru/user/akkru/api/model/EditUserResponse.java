package com.akkru.user.akkru.api.model;

import com.google.gson.annotations.SerializedName;

public class EditUserResponse{

	@SerializedName("result")
	private boolean result;

	@SerializedName("msg")
	private String msg;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}
}