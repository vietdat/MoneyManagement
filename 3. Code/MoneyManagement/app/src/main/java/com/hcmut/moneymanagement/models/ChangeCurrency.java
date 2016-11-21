package com.hcmut.moneymanagement.models;

/**
 * Created by Admin on 20-Nov-16.
 */
public class ChangeCurrency {

    public float changeMoneyUSDToVND(float money) {
        return money*22550;
    }
    public float changeMoneyVNDToUSD(float money) {
        return money/22550;
    }
}
