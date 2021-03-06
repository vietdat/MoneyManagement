package com.hcmut.moneymanagement.activity.Budget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.BudgetModel;
import com.hcmut.moneymanagement.models.ChangeCurrency;
import com.hcmut.moneymanagement.objects.Budget;

import java.io.Serializable;

public class BudgetDetail extends AppCompatActivity {

    private Toolbar mToolbar;
    Budget budget;
    String key;
    BudgetModel budgetModel;
    EditText nameOfBudget, initAmount, currentAmount, endDate, description, category, startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.detail_budget_tittle);
        getSupportActionBar().setTitle(title);

        budget = new Budget();

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");
        budget = (Budget) getIntent().getSerializableExtra("budget");
        budgetModel = new BudgetModel();
        //add data to view
        initData();
    }

    private void initData() {
        nameOfBudget = (EditText) findViewById(R.id.input_name);
        endDate = (EditText) findViewById(R.id.endDate);
        startDate = (EditText) findViewById(R.id.startDate);
        initAmount = (EditText) findViewById(R.id.initAmount);
        currentAmount = (EditText) findViewById(R.id.currentAmount);
        category = (EditText) findViewById(R.id.category);
        description = (EditText) findViewById(R.id.description);

        nameOfBudget.setText(budget.getName());

        category.setText(budget.getCategory());
        endDate.setText(budget.getEndDate());
        startDate.setText(budget.getStartDate());
        description.setText(budget.getDescription());

        SharedPreferences pre= getSharedPreferences("currency", 0);
        String lang = pre.getString("currency", "0");
        String currency;
        ChangeCurrency cc = new ChangeCurrency();

        if (lang.equals("1")) {
            currency = "đ"; // your language
            initAmount.setText(cc.changeMoneyUSDToVND(budget.getAmount()) + currency);
            currentAmount.setText(cc.changeMoneyUSDToVND(budget.getCurrentAmount()) + currency);
        } else {
            currency = "$"; // your language
            initAmount.setText(currency + budget.getAmount());
            currentAmount.setText(currency + budget.getCurrentAmount());
        }

        nameOfBudget.setFocusable(false);
        initAmount.setFocusable(false);
        currentAmount.setFocusable(false);
        category.setFocusable(false);
        endDate.setFocusable(false);
        startDate.setFocusable(false);
        description.setFocusable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_his, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnUpdate) {
            Intent intent = new Intent(this, BudgetEdit.class);
            intent.putExtra("key", key);
            intent.putExtra("budget", (Serializable) budget);
            startActivity(intent);
            BudgetDetail.this.finish();
            return true;
        }

        if (id == R.id.mnHis) {
            Intent intent = new Intent(this, BudgetHis.class);
            intent.putExtra("key", key);
            intent.putExtra("budget", (Serializable) budget);
            startActivity(intent);
            BudgetDetail.this.finish();
            return true;
        }

        if(id == R.id.mnDelete){
            budgetModel.remove(key);
            BudgetDetail.this.finish();
            return true;
        }

        if(id == android.R.id.home){
            BudgetDetail.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
