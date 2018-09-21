package com.akkru.user.akkru.api.model.SetExpense;

import com.google.gson.annotations.SerializedName;

public class SetExpenseRespons{

	@SerializedName("result")
	private boolean result;

	@SerializedName("expense")
	private Expense expense;

	@SerializedName("point")
	private int point;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setExpense(Expense expense){
		this.expense = expense;
	}

	public Expense getExpense(){
		return expense;
	}

	public void setPoint(int point){
		this.point = point;
	}

	public int getPoint(){
		return point;
	}
}