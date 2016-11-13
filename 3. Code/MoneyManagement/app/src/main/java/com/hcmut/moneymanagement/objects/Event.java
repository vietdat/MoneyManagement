package com.hcmut.moneymanagement.objects;

import java.io.Serializable;

/**
 * Created by Admin on 06-Nov-16.
 */
public class Event extends Object implements Serializable {

    public String name;
    public String endDate;
    public String current_unit;
    public int spent;
    public String description;

    public Event () {

    }

    public Event(String name, String endDate, String current_unit,
                 String description) {
        this.name = name;
        this.endDate = endDate;
        this.current_unit = current_unit;
        this.description = description;
        this.spent = 0;
    }

    public Event(String name, String endDate, String current_unit,
                 String description, int spent) {
        this.name = name;
        this.endDate = endDate;
        this.current_unit = current_unit;
        this.description = description;
        this.spent = 0;
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

    public String getCurrent_unit() {
        return current_unit;
    }

    public void setCurrent_unit(String current_unit) {
        this.current_unit = current_unit;
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


}
