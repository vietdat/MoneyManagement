package com.hcmut.moneymanagement.objects;

import java.io.Serializable;

/**
 * Created by Admin on 23-Oct-16.
 */
public class Saving extends Object implements Serializable {
    public String name;
    public String goal;
    public String startDate;
    public String endDate;
    public String current_amount;
    public String startAmount;
    public String description;

    public Saving(){
    }

    public Saving(String name, String goal, String current_amount,
                  String startDate, String endDate, String description) {
        this.name = name;
        this.goal = goal;
        this.current_amount = current_amount;
        this.startAmount = current_amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(String current_amount) {
        this.current_amount = current_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return name;
    }

    public void setStartAmount(String startAmount) {
        this.startAmount = startAmount;
    }

    public String getStartAmount() {
        return startAmount;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}