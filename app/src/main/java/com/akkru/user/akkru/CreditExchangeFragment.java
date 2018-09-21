package com.akkru.user.akkru;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akkru.user.akkru.api.model.reward.RewardResponse;
import com.akkru.user.akkru.api.model.reward.RewardsItem;
import com.akkru.user.akkru.api.service.ApiClient;
import com.akkru.user.akkru.api.service.UserClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreditExchangeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageView ivCreditThumbnail, next, prev;
    private TextView tvPointPlus, tvPointTotal, tvPointMinus;
    private int sumOfPoint;
    private ViewPager viewPager;
    private List<RewardsItem> rewardsItems;

    public CreditExchangeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_credit_exchange, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sumOfPoint = 0;

        viewPager = view.findViewById(R.id.view_pager_credit);
        viewPager.setAdapter(new CreditExchangeAdapter(getFragmentManager()));

        prev = view.findViewById(R.id.back_credit);
        next = view.findViewById(R.id.next_credit);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewPager.getCurrentItem();
                viewPager.setCurrentItem(a + 1);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewPager.getCurrentItem();
                viewPager.setCurrentItem(a - 1, true);
            }
        });

        tvPointTotal = view.findViewById(R.id.point_total);

        tvPointPlus = view.findViewById(R.id.point_plus);
        tvPointPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumOfPoint = sumOfPoint + 1;
                displaySumOfPoint(sumOfPoint);
            }


        });

        tvPointMinus = view.findViewById(R.id.point_minus);
        tvPointMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sumOfPoint > 0) {
                    sumOfPoint = sumOfPoint - 1;
                    displaySumOfPoint(sumOfPoint);
                }
            }
        });

        UserClient userClient = ApiClient.getClient().create(UserClient.class);

        Call<RewardResponse> call = userClient.getRewards();
        call.enqueue(new Callback<RewardResponse>() {
            @Override
            public void onResponse(Call<RewardResponse> call, Response<RewardResponse> response) {
                if (response.isSuccessful()) {
                    rewardsItems = response.body().getRewards();
                } else {

                }

            }

            @Override
            public void onFailure(Call<RewardResponse> call, Throwable t) {

            }
        });


    }


    private void displaySumOfPoint(int sumOfPoint) {
        tvPointTotal.setText(sumOfPoint + "");
    }
}
