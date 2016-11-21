package com.hcmut.moneymanagement.activity.CustomListView.Model;


public class Transaction {
    private String category;
    private String date;
    private String amount;

    public Transaction(String category, String date, String amount) {
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
