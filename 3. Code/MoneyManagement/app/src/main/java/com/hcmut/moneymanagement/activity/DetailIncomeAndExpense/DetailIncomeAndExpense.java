package com.hcmut.moneymanagement.activity.DetailIncomeAndExpense;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.hcmut.moneymanagement.R;

public class DetailIncomeAndExpense extends Activity {

    EditText typeOfTransaction, amount, wallet, date, category, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_income_and_expense);

        initData();
        setData("income", "+1000000", "20/10/2016", "cash",
                "Bill", "Tra tien hoa don dien");
    }

    private void initData() {
        typeOfTransaction = (EditText) findViewById(R.id.typeTransaction);
        amount = (EditText) findViewById(R.id.amount);
        date = (EditText) findViewById(R.id.date);
        wallet = (EditText) findViewById(R.id.wallet);
        category = (EditText) findViewById(R.id.category);
        description = (EditText) findViewById(R.id.desciption);
    }

    private void setData(String typeOfTransaction_, String amount_, String date_, String wallet_,
                    String category_, String description_) {
        typeOfTransaction.setText(typeOfTransaction_);
        amount.setText(amount_);
        date.setText(date_);
        wallet.setText(wallet_);
        category.setText(category_);
        description.setText(description_);
    }
}
