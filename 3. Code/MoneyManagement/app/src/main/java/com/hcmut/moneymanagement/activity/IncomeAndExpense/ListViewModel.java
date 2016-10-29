package com.hcmut.moneymanagement.activity.IncomeAndExpense;

/**
 * Created by Admin on 15-Oct-16.
 */
public class ListViewModel {
    private String date_time;
    private String amount;

    public ListViewModel(String date_time, String amount) {
        this.date_time = date_time;
        this.amount = amount;
    }

    public String getDateTime() {
        return date_time;
    }

    public void setDateTime(String date_time) {
        this.date_time = date_time;
    }

    public String getamount() {
        return amount;
    }

    public void setamount(String amount) {
        this.amount = amount;
    }
}
