package com.hcmut.moneymanagement.activity.Transaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.hcmut.moneymanagement.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashMap;
import java.util.Map;

public class AddTransactionActivity extends AppCompatActivity implements OnClickListener {

    private Toolbar mToolbar;
    private EditText dateView;
    private EditText amouthOfMoney;
    private EditText description;
    private MaterialBetterSpinner typeTransaction;
    private MaterialBetterSpinner wallet;
    private MaterialBetterSpinner category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String title = getString(R.string.add_transaction_title);
        getSupportActionBar().setTitle(title);


        init();
    }

    private void  init(){
        //init
        dateView = (EditText) findViewById(R.id.input_date);
        typeTransaction = (MaterialBetterSpinner) findViewById(R.id.typeTransaction);
        category = (MaterialBetterSpinner) findViewById(R.id.category);
        wallet = (MaterialBetterSpinner) findViewById(R.id.wallet);
        amouthOfMoney = (EditText) findViewById(R.id.input_amount);
        description = (EditText) findViewById(R.id.desciption);

        ControllerAddTransaction add_default = new ControllerAddTransaction(this,typeTransaction,category,wallet);

        add_default.showTypeTransaction();
        add_default.showCategorys();
        add_default.showWallets();

    }

    //Get all data user input.
    private Map getInputData() {
        Map<String, Object> data = new HashMap<>();

        //get type of transaction
        String typeOfTransactionValue = typeTransaction.getText().toString().trim();
        String amountOfMoneyValue = amouthOfMoney.getText().toString().trim();
        String dateViewValue = dateView.getText().toString().trim();
        String walletValue = wallet.getText().toString().trim();
        String categoryValue = category.getText().toString().trim();
        String descriptionValue = description.getText().toString().trim();

        data.put("typeOfTransaction", typeOfTransactionValue);
        data.put("amouthOfMoney", amountOfMoneyValue);
        data.put("dateView", dateViewValue);
        data.put("wallet", walletValue);
        data.put("category",categoryValue);
        data.put("description", descriptionValue);

        return data;
    }

    //handler data

    //

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_transaction, menu);
        return true;
    }

    public void onStart(){
        super.onStart();

        EditText txtDate=(EditText)findViewById(R.id.input_date);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    DateDialog dialog=new DateDialog(view);
                    android.app.FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
    }

    @Override
    public void onClick(View view) {
    }

}
