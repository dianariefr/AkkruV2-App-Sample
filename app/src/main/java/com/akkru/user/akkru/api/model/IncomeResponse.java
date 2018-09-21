package com.akkru.user.akkru.api.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IncomeResponse{

	@SerializedName("result")
	private boolean result;

	@SerializedName("income")
	private List<IncomeItem> income;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setIncome(List<IncomeItem> income){
		this.income = income;
	}

	public List<IncomeItem> getIncome(){
		return income;
	}
}