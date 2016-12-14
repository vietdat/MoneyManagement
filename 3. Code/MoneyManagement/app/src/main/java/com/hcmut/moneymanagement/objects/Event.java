package com.hcmut.moneymanagement.objects;

import java.io.Serializable;

/**
 * Created by Admin on 06-Nov-16.
 */
public class Event extends Object implements Serializable {

    public String name;
    public String startDate;
    public String endDate;
    public int spent;
    public String description;

    public Event () {

    }

    public Event(String name, String startDate, String endDate, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.spent = 0;
    }

    public Event(String name, String startDate, String endDate, String description, int spent) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.spent = spent;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpent() {
        return spent;
    }

    public void setSpent(int spent) {
        this.spent = spent;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


}
