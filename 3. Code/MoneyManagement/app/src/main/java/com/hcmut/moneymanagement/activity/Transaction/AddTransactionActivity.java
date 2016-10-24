package com.hcmut.moneymanagement.activity.Transaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.IncomeCategoryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddTransactionActivity extends AppCompatActivity implements OnClickListener {

    private Toolbar mToolbar;
    private EditText dateView;
    private EditText amouthOfMoney;
    private EditText description;
    private Spinner typeTransaction;
    private Spinner wallet;
    private Spinner category;

    private AdapterController adapterController;
    private String previousTypeSelected;
    private AlertDialog categoryDialog;

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
        dialogInit();

        // Type of transaction changed
        typeTransaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // If the selection has changed
                String selected = typeTransaction.getSelectedItem().toString();
                if( !previousTypeSelected.equals(selected) ) {
                    if (selected.equals("Income")) {
                        category.setAdapter(adapterController.getIncomeCategoryAdapter());
                    } else if (selected.equals("Expense")) {
                        category.setAdapter(adapterController.getExpenseCategoryAdapter());
                    }
                    previousTypeSelected = selected;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        wallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        // Create new category
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = category.getSelectedItem().toString();
                if(selected.equals("Create new")){
                    categoryDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void  init(){
        previousTypeSelected = "";

        dateView = (EditText) findViewById(R.id.input_date);
        typeTransaction = (Spinner) findViewById(R.id.typeTransaction);
        category = (Spinner) findViewById(R.id.category);
        wallet = (Spinner) findViewById(R.id.wallet);
        amouthOfMoney = (EditText) findViewById(R.id.input_amount);
        description = (EditText) findViewById(R.id.desciption);

        adapterController = new AdapterController(this);

        typeTransaction.setAdapter(adapterController.getTransactionTypesAdapter());
        wallet.setAdapter(adapterController.getWalletAdapter());
        category.setAdapter(adapterController.getIncomeCategoryAdapter());
    }

    private void dialogInit(){
        final EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New category");
        builder.setView(input);

        // Add the buttons to Dialogs
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(previousTypeSelected.equals("Income")){
                    dialog.dismiss();
                    adapterController.addIncomeCategory(input.getText().toString());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // Create the AlertDialog
        categoryDialog = builder.create();
    }

    //Get all data user input.
    private Map getInputData() {
        Map<String, Object> data = new HashMap<>();
        /*
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
        */

        return data;
    }

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
