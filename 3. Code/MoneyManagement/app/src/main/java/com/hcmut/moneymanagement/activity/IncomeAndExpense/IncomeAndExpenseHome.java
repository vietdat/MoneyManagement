package com.hcmut.moneymanagement.activity.IncomeAndExpense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hcmut.moneymanagement.R;

public class IncomeAndExpenseHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_and_expense_home);

        String value = getIntent().getExtras().getString("type");

        Toast.makeText(this, value, Toast.LENGTH_LONG).show();
    }
}
