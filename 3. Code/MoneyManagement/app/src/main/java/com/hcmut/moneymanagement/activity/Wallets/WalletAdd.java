package com.hcmut.moneymanagement.activity.Wallets;

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

public class WalletAdd extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private WalletCategoryModel walletCategoryModel;
    private EditText input_name, startMoney, note;
    private Spinner typeOfAccount;
    private WalletModel walletModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_add);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.add_wallet_title);
        getSupportActionBar().setTitle(title);

        init();
        typeOfTransaction ();
    }

    private void init() {
        input_name = (EditText) findViewById(R.id.input_name);
        startMoney = (EditText) findViewById(R.id.startMoney);
        note = (EditText) findViewById(R.id.note);
        typeOfAccount = (Spinner) findViewById(R.id.typeOfAccount);

        walletModel = new WalletModel();
    }

    private Wallet getValue() {
        String name = input_name.getText().toString();
        String type = typeOfAccount.getSelectedItem().toString();
        String description = note.getText().toString();
        String initAmount = startMoney.getText().toString();

        if(name.equals("")){
            Toast.makeText(WalletAdd.this,"Please input name",Toast.LENGTH_SHORT).show();
            return null;
        }

        if(initAmount.equals("")){
            Toast.makeText(WalletAdd.this,"Please input name",Toast.LENGTH_SHORT).show();
            return null;
        }

        if(type.equals("")){
            Toast.makeText(WalletAdd.this,"Please connect internet to get data!!",Toast.LENGTH_SHORT).show();
            return null;
        }


        Wallet wallet = new Wallet(name, type, description, Integer.parseInt(initAmount));

        return wallet;
    }

    /**
     * add data to typeOftransaction
     * click event.
     */
    private void typeOfTransaction () {
        // typeOfAccount
        walletCategoryModel = new WalletCategoryModel(WalletAdd.this);

        typeOfAccount.setAdapter(walletCategoryModel.getNames());
        typeOfAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = typeOfAccount.getSelectedItem().toString();
                    if(selected.equals("Create new")){
                        // Create dialog
                        final EditText input = new EditText(WalletAdd.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(WalletAdd.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.mnDone) {
            if(getValue() == null){
                return false;
            }
            walletModel.add(getValue());
            walletModel.getReference().addChildEventListener(onWalletChildListener);
            return true;
        }

        if(id == android.R.id.home){
            WalletAdd.this.finish();
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
    }

    @Override
    public void onClick(View view) {

    }

    // on child added
    private ChildEventListener onWalletChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Toast.makeText(WalletAdd.this,"Add new wallet success",Toast.LENGTH_SHORT).show();
            WalletAdd.this.finish();
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
            Toast.makeText(WalletAdd.this,"Error Establishing a Database Connection",Toast.LENGTH_LONG).show();
        }
    };
}
