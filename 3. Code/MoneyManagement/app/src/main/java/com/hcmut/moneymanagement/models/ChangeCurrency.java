package com.hcmut.moneymanagement.models;

import java.text.DecimalFormat;

/**
 * Created by Admin on 20-Nov-16.
 */
public class ChangeCurrency {

    DecimalFormat df = new DecimalFormat("#,###.##");

    public String changeMoneyUSDToVND(String money) {
        return String.valueOf(df.format(Double.parseDouble(money)*22680));
    }
    public String changeMoneyVNDToUSD(String money) {
        return String.valueOf(df.format(Double.parseDouble(money)/22680));
    }
}
