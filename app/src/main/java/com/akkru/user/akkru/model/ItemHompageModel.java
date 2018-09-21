package com.akkru.user.akkru.model;

public class ItemHompageModel {
    String date;
    String nominal;
    String itemName;
    int minplus;

    public ItemHompageModel(String date, String nominal, String itemName, int minplus) {
        this.date = date;
        this.nominal = nominal;
        this.itemName = itemName;
        this.minplus = minplus;
    }

    public int getMinplus() {
        return minplus;
    }

    public void setMinplus(int minplus) {
        this.minplus = minplus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
