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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.WalletCategoryModel;
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.objects.Category;
import com.hcmut.moneymanagement.objects.Wallet;

import java.util.ArrayList;
import java.util.List;

public class AddNewWalletActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private WalletCategoryModel walletCategoryModel;
    private Button btnSaving;
    private EditText input_name, startMoney, note;
    private Spinner typeOfAccount, currency;
    private WalletModel walletModel;

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

        init();
        typeOfTransaction ();
        typeOfCurrency();
        btnSaving.setOnClickListener(this);

    }

    private void init() {
        btnSaving = (Button) findViewById(R.id.btnSaving);
        input_name = (EditText) findViewById(R.id.input_name);
        startMoney = (EditText) findViewById(R.id.startMoney);
        note = (EditText) findViewById(R.id.note);
        typeOfAccount = (Spinner) findViewById(R.id.typeOfAccount);
        currency = (Spinner) findViewById(R.id.currency);

        walletModel = new WalletModel();
    }

    private Wallet getValue() {
        String name = input_name.getText().toString();
        String type = typeOfAccount.getSelectedItem().toString();
        String currencyUnit = currency.getSelectedItem().toString();
        String description = note.getText().toString();
        String initAmount = startMoney.getText().toString();

        Wallet wallet = new Wallet(name, type, currencyUnit, description, Integer.parseInt(initAmount));
        return wallet;
    }

    /**
     * add data to typeOftransaction
     * click event.
     */
    private void typeOfTransaction () {
        // typeOfAccount
        walletCategoryModel = new WalletCategoryModel(AddNewWalletActivity.this);

        typeOfAccount.setAdapter(walletCategoryModel.getNames());
        typeOfAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = typeOfAccount.getSelectedItem().toString();
                    if(selected.equals("Create new")){
                        // Create dialog
                        final EditText input = new EditText(AddNewWalletActivity.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewWalletActivity.this);
                        builder.setTitle("New wallet");
                        builder.setView(input);

                        // Add the buttons to Dialogs
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //add to database
                                dialog.dismiss();
                                Category category = new Category(input.getText().toString());
                                walletCategoryModel.add(category);
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

    @Override
    public void onClick(View view) {
        if(view == btnSaving) {
            walletModel.add(getValue());
            walletModel.getReference().addChildEventListener(onWalletChildListener);
        }
    }

    // on child added
    private ChildEventListener onWalletChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Toast.makeText(AddNewWalletActivity.this,"Add new wallet success",Toast.LENGTH_SHORT).show();
            AddNewWalletActivity.this.finish();
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
            Toast.makeText(AddNewWalletActivity.this,"Error Establishing a Database Connection",Toast.LENGTH_LONG).show();
        }
    };
}
