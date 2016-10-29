package com.hcmut.moneymanagement.objects;

/**
 * Created by Admin on 23-Oct-16.
 */
public class Saving {
    String name;
    String goal;
    String current_amount;

    String left;

    public Saving(){

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

    public Saving(String name, String goal, String current_amount, String left) {
        this.name = name;
        this.goal = goal;
        this.current_amount = current_amount;
        this.left = left;

    }

    public String getTitle() {
        return name;
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

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }


}