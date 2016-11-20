package com.hcmut.moneymanagement.activity.Budget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.BudgetModel;
import com.hcmut.moneymanagement.models.ExpenseCategoryModel;
import com.hcmut.moneymanagement.objects.Budget;

public class BudgetAdd extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText input_name, input_end_date, description, amount;
    private BudgetModel budgetModel;
    private Spinner category;
    private ExpenseCategoryModel expenseCategoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_add);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.add_budget_tittle);
        getSupportActionBar().setTitle(title);

        init();
    }

    private void init() {
        input_name = (EditText) findViewById(R.id.input_name);
        input_end_date = (EditText) findViewById(R.id.etEndtDate);
        description = (EditText) findViewById(R.id.description);
        amount = (EditText) findViewById(R.id.amount);
        category = (Spinner) findViewById(R.id.category);

        input_end_date.setShowSoftInputOnFocus(false);

        budgetModel = new BudgetModel();
        expenseCategoryModel = new ExpenseCategoryModel();
        expenseCategoryModel.initListViewAdapter(getApplicationContext());
        category.setAdapter(expenseCategoryModel.getNameAdapter());
    }

    private Budget getValue() {
        String name = input_name.getText().toString();
        String end_date = input_end_date.getText().toString();
        String desciption = description.getText().toString();
        String currentAmount = amount.getText().toString();
        String categoryName = category.getSelectedItem().toString();

        Budget budget = new Budget(name, end_date, currentAmount, desciption, categoryName);

        return budget;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.mnDone) {
            budgetModel.add(getValue());
            budgetModel.getReference().addChildEventListener(onSavingChildListener);
            return true;
        }

        if(id == android.R.id.home){
            BudgetAdd.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_add, menu);
        return true;
    }

    public void onStart(){
        super.onStart();
        input_end_date = (EditText) findViewById(R.id.etEndtDate);
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

    private ChildEventListener onSavingChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Toast.makeText(BudgetAdd.this,R.string.budget_add_success,Toast.LENGTH_SHORT).show();
            BudgetAdd.this.finish();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(BudgetAdd.this,R.string.database_err,Toast.LENGTH_SHORT).show();
        }
    };
}
