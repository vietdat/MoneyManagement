package com.hcmut.moneymanagement.objects;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Wallet {
    public String name;
    public String type;
    public String currencyUnit;
    public String description;
    public String date;
    public int initialAmount;
    public int currentAmount;


    public Wallet(){
    }

    public Wallet(String name, String type, String currencyUnit, String description){
        this.name = name;
        this.type = type;
        this.currencyUnit = currencyUnit;
        this.description = description;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.date = df.format(c.getTime());

        this.initialAmount = 0;
        this.currentAmount = 0;
    }

    public Wallet(String name, String type, String currencyUnit, String description, int initialAmount){
        this.name = name;
        this.type = type;
        this.currencyUnit = currencyUnit;
        this.description = description;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.date = df.format(c.getTime());

        this.initialAmount = initialAmount;
        this.currentAmount = initialAmount;
    }
}
