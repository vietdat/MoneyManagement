package com.hcmut.moneymanagement.objects;

public class Wallet {
    public String name;
    public String description;
    public int money;

    public Wallet(String name){
        this.name = name;
        this.description = "";
        this.money = 0;
    }

    public Wallet(String name, String description, int money){
        this.name = name;
        this.description = description;
        this.money = money;
    }
}
