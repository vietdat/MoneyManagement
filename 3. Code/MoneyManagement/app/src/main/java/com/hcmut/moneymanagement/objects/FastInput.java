package com.hcmut.moneymanagement.objects;

import java.io.Serializable;

/**
 * Created by Admin on 24-Dec-16.
 */
public class FastInput extends Object implements Serializable {
    public String key;
    public String type;
    public String money;
    public String wallet;
    public String category;
    public String description;

    public FastInput(){

    }

    public FastInput(String key, String type, String money, String wallet, String category, String description ){
        this.key = key;
        this.type = type;
        this.money = money;
        this.wallet = wallet;
        this.category = category;
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
