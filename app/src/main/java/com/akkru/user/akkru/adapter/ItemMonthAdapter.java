package com.akkru.user.akkru.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akkru.user.akkru.R;
import com.akkru.user.akkru.model.ItemMonthModel;

import java.util.List;

public class ItemMonthAdapter extends RecyclerView.Adapter<ItemMonthAdapter.MyViewHolder> {

    List<ItemMonthModel> itemMonthModelList;
    //subclass
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView month;

        public MyViewHolder(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.tv_month);
        }
    }

    //constructor
    public ItemMonthAdapter(List<ItemMonthModel> itemMonthModels) {
        this.itemMonthModelList = itemMonthModels;
    }

    @NonNull
    @Override
    public ItemMonthAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_month, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemMonthModel itemMonthModel = itemMonthModelList.get(position);
        holder.month.setText(itemMonthModel.getMonth());

    }

    @Override
    public int getItemCount() {
        return itemMonthModelList.size();
    }
}
