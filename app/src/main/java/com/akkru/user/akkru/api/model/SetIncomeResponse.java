package com.akkru.user.akkru.api.model;

import com.google.gson.annotations.SerializedName;

public class SetIncomeResponse{

	@SerializedName("result")
	private boolean result;

	@SerializedName("income")
	private Income income;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setIncome(Income income){
		this.income = income;
	}

	public Income getIncome(){
		return income;
	}
}