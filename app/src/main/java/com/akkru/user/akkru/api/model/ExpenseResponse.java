package com.akkru.user.akkru.api.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ExpenseResponse{

	@SerializedName("result")
	private boolean result;

	@SerializedName("expenses")
	private List<ExpensesItem> expenses;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setExpenses(List<ExpensesItem> expenses){
		this.expenses = expenses;
	}

	public List<ExpensesItem> getExpenses(){
		return expenses;
	}
}