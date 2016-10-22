package com.hcmut.moneymanagement.objects;

import java.util.Date;

public class Transaction {

    public String type;
    public int money;
    public Date date;
    public String wallet;
    public String category;
    public String description;

    public Transaction(){

    }

    public Transaction(String type, int money, Date date, String wallet, String category, String description ){
        this.type = type;
        this.money = money;
        this.date = date;
        this.wallet = wallet;
        this.category = category;
        this.description = description;

    }


}
