package com.hcmut.moneymanagement.objects;

import java.io.Serializable;

/**
 * Created by Admin on 15-Nov-16.
 */
public class Budget extends Object implements Serializable {
    public String name;
    public String endDate;
    public String amount;
    public String description;
    public String category;
    public String currentAmount;

    public Budget () {

    }

    public Budget(String name, String endDate, String amount, String description, String category) {
        this.name = name;
        this.endDate = endDate;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.currentAmount = amount;
    }

    public Budget(String name, String endDate, String amount, String description, String category,
                  String currentAmount) {
        this.name = name;
        this.endDate = endDate;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.currentAmount = currentAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }
}
