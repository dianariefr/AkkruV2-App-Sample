package com.akkru.user.akkru.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akkru.user.akkru.R;
import com.akkru.user.akkru.api.model.IncomeItem;
import com.akkru.user.akkru.model.ItemHompageModel;

import java.util.ArrayList;
import java.util.List;

public class ItemHomePageAdapter extends RecyclerView.Adapter<ItemHomePageAdapter.MyViewHolder> {

    List<ItemHompageModel> hompageModelList;
    //subclass
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, itemName, nominal, minplus;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_date);
            itemName = itemView.findViewById(R.id.tv_itemname);
            nominal = itemView.findViewById(R.id.tv_nominal2);
            minplus = itemView.findViewById(R.id.tv_plus);
        }
    }

    //constructor
    public ItemHomePageAdapter(List<ItemHompageModel> itemHompageModels) {
        this.hompageModelList = itemHompageModels;
    }

    @NonNull
    @Override
    public ItemHomePageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHomePageAdapter.MyViewHolder holder, int position) {
        ItemHompageModel itemHome = hompageModelList.get(position);
        holder.date.setText(String.valueOf(position+1));
        holder.itemName.setText(itemHome.getItemName());
        holder.nominal.setText(String.valueOf("Rp. "+itemHome.getNominal()));
        if (itemHome.getMinplus()==0){
            holder.minplus.setText("-");
            holder.minplus.setTextColor(Color.BLACK);
            holder.nominal.setTextColor(Color.BLACK);
        }else{
            holder.minplus.setText("+");
            holder.minplus.setTextColor(Color.RED);
            holder.itemName.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return hompageModelList.size();
    }
}
