package com.hcmut.moneymanagement.activity.Wallet;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hcmut.moneymanagement.R;

import java.util.ArrayList;
import java.util.List;

public class AddNewWalletActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.add_wallet_title);
        getSupportActionBar().setTitle(title);

        typeOfTransaction ();
        typeOfCurrency();


    }

    /**
     * add data to typeOftransaction
     * click event.
     */
    private void typeOfTransaction () {
        // typeOfAccount
        final Spinner typeOfAccount = (Spinner) findViewById(R.id.typeOfAccount);

        List<String> types = new ArrayList<String>();
        types.add("Cash");
        types.add("Bank account");
        types.add("Create new...");

        final ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfAccount.setAdapter(typeAdapter);
        typeOfAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                String selected = typeOfAccount.getSelectedItem().toString();
                    if(selected.equals("Create new...")){
                        // Create dialog
                        final EditText input = new EditText(AddNewWalletActivity.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewWalletActivity.this);
                        builder.setTitle("New wallet");
                        builder.setView(input);

                        // Add the buttons to Dialogs
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //add to database
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        // Create the AlertDialog
                        Dialog dialog = builder.create();

                        dialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    }
}
