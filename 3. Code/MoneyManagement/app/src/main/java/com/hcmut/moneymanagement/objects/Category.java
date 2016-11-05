package com.hcmut.moneymanagement.objects;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Category {
    public String name;
    public Category(){
        String name = "";
    }
    public Category(String name){
        this.name =name;
    }
}
