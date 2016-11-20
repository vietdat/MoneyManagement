package com.hcmut.moneymanagement.activity.Budget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.BudgetModel;
import com.hcmut.moneymanagement.models.ExpenseCategoryModel;
import com.hcmut.moneymanagement.objects.Budget;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BudgetEdit extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText input_name, input_end_date, description, amount, category;
    private BudgetModel budgetModel;
    private String key;
    private Budget budget;
    private ExpenseCategoryModel expenseCategoryModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_edit);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.edit_budget_tittle);
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
        input_name = (EditText) findViewById(R.id.input_name);
        input_end_date = (EditText) findViewById(R.id.etEndtDate);
        description = (EditText) findViewById(R.id.description);
        amount = (EditText) findViewById(R.id.amount);
        category = (EditText) findViewById(R.id.category);

        input_end_date.setShowSoftInputOnFocus(false);

        input_name.setText(budget.getName());
        input_end_date.setText(budget.getEndDate());
        description.setText(budget.getDescription());
        amount.setText(budget.getAmount());
        category.setText(budget.getCategory());

        budgetModel = new BudgetModel();

        amount.setFocusable(false);
        category.setFocusable(false);
    }

    private Budget getValue(){
        String name = input_name.getText().toString();
        String end_date = input_end_date.getText().toString();
        String desciption = description.getText().toString();
        String initAmount = amount.getText().toString();
        String categoryName = category.getText().toString();
        String currentAmount = budget.getCurrentAmount();

        Budget budget1 = new Budget(name, end_date, initAmount, desciption, categoryName, currentAmount);

        return budget1;
    }

    public void onStart(){
        super.onStart();

        input_end_date.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    com.hcmut.moneymanagement.activity.Transaction.DateDialog dialog=new com.hcmut.moneymanagement.activity.Transaction.DateDialog(view);
                    android.app.FragmentTransaction ft =getFragmentManager().beginTransaction();
                    InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    dialog.show(ft, "DatePicker");
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnDone) {
            Budget update = getValue();
            Map<String, Object> updateData = new HashMap<String, Object>();
            updateData.put(budgetModel.encrypt("name"), budgetModel.encrypt(update.getName()));
            updateData.put(budgetModel.encrypt("endDate"), budgetModel.encrypt(update.getEndDate()));
            updateData.put(budgetModel.encrypt("description"), budgetModel.encrypt(update.getDescription()));

            budgetModel.update(key, updateData);

            Intent intent = new Intent(this, BudgetDetail.class);
            intent.putExtra("key", key);
            intent.putExtra("budget", (Serializable) update);
            startActivity(intent);
            BudgetEdit.this.finish();

            return true;
        }

        if(id == android.R.id.home){
            BudgetEdit.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
