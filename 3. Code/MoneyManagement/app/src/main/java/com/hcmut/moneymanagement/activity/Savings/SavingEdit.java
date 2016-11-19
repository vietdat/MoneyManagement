package com.hcmut.moneymanagement.activity.Savings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Wallets.WalletDetai;
import com.hcmut.moneymanagement.models.SavingModel;
import com.hcmut.moneymanagement.objects.Saving;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavingEdit extends AppCompatActivity {

    private Toolbar mToolbar;
    Saving saving;
    String key;
    SavingModel savingModel;
    EditText nameOfSaving, goal, currentAmount, startDate, endDate, description;
    Spinner currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_edit);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.edit_saving_tittle);
        getSupportActionBar().setTitle(title);

        saving = new Saving();

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");
        saving = (Saving) getIntent().getSerializableExtra("saving");
        savingModel = new SavingModel();

        //add data to view
        initData();
    }

    private void initData() {
        nameOfSaving = (EditText) findViewById(R.id.input_name);
        goal = (EditText) findViewById(R.id.goal);
        currentAmount = (EditText) findViewById(R.id.currentAmount);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        description = (EditText) findViewById(R.id.description);
        currency = (Spinner) findViewById(R.id.currency);

        typeOfCurrency();

        nameOfSaving.setText(saving.getName());
        goal.setText(saving.getGoal());
        currentAmount.setText(saving.getCurrent_amount());
        startDate.setText(saving.getStartDate());
        endDate.setText(saving.getEndDate());
        description.setText(saving.getDescription());

        currentAmount.setFocusable(false);
        startDate.setFocusable(false);
        currency.setEnabled(false);
        endDate.setShowSoftInputOnFocus(false);
    }

    private void typeOfCurrency() {
        // currencySpinner
        Spinner currencySpinner = (Spinner) findViewById(R.id.currency);

        List<String> currency = new ArrayList<String>();
        currency.add("VND");

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currency);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);
    }

    private Saving getValue(){
        String name = nameOfSaving.getText().toString();
        String goal1 = goal.getText().toString();
        String start_date = startDate.getText().toString();
        String currentAmount1 = currentAmount.getText().toString();
        String end_date = endDate.getText().toString();
        String desciption1 = description.getText().toString();
        String currentUnit = currency.getSelectedItem().toString();

        Saving saving = new Saving(name, goal1, currentAmount1, currentUnit, start_date,
                end_date, desciption1);
        return saving;
    }

    public void onStart(){
        super.onStart();

        endDate = (EditText) findViewById(R.id.endDate);
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
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
            Saving update = getValue();
            Map<String, Object> updateData = new HashMap<String, Object>();
            updateData.put(savingModel.encrypt("name"), savingModel.encrypt(update.getName()));
            updateData.put(savingModel.encrypt("goal"), savingModel.encrypt(update.getGoal()));
            updateData.put(savingModel.encrypt("endDate"), savingModel.encrypt(update.getEndDate()));
            updateData.put(savingModel.encrypt("description"), savingModel.encrypt(update.getDescription()));

            savingModel.update(key, updateData);

            Intent intent = new Intent(this, WalletDetai.class);
            intent.putExtra("key", key);
            intent.putExtra("saving", (Serializable) update);
            startActivity(intent);
            SavingEdit.this.finish();

            return true;
        }

        if(id == android.R.id.home){
            SavingEdit.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
