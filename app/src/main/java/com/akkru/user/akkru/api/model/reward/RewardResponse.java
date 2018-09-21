package com.akkru.user.akkru.api.model.reward;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RewardResponse{

	@SerializedName("result")
	private boolean result;

	@SerializedName("rewards")
	private List<RewardsItem> rewards;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setRewards(List<RewardsItem> rewards){
		this.rewards = rewards;
	}

	public List<RewardsItem> getRewards(){
		return rewards;
	}
}