package com.hcmut.moneymanagement.objects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Wallet extends Object implements Serializable {
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(int initialAmount) {
        this.initialAmount = initialAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

}
