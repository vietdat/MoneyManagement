package com.hcmut.moneymanagement.activity.Savings;

/**
 * Created by Admin on 23-Oct-16.
 */
public class Saving {
    String title;
    String goal;
    String left;

    public Saving(){

    }

    public Saving(String title, String goal, String left) {
        this.title = title;
        this.goal = goal;
        this.left = left;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
