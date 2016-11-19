package com.hcmut.moneymanagement.activity.Wallets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.WalletCategoryModel;
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.objects.Category;
import com.hcmut.moneymanagement.objects.Wallet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WalletEdit extends AppCompatActivity {

    private Toolbar mToolbar;
    String key;
    Wallet wallet;
    WalletModel walletModel;
    EditText nameOfWallet, currentAmount, note;
    Spinner typeOfAccount;
    WalletCategoryModel walletCategoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detai);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.add_wallet_title);
        getSupportActionBar().setTitle(title);

        wallet = new Wallet();

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");
        wallet = (Wallet) getIntent().getSerializableExtra("wallet");
        walletModel = new WalletModel();
        walletModel.initNameAdapter(getApplicationContext());
        //add data to view
        initData();
    }

    private void initData() {
        nameOfWallet = (EditText) findViewById(R.id.input_name);
        currentAmount = (EditText) findViewById(R.id.startMoney);
        note = (EditText) findViewById(R.id.note);
        typeOfAccount = (Spinner) findViewById(R.id.typeOfAccount);

        typeOfAccount();

        nameOfWallet.setText(wallet.getName());
        currentAmount.setText(String.valueOf(wallet.getCurrentAmount()));
        note.setText(wallet.getDescription());
        int spinnerPosition = walletCategoryModel.getNames().getPosition(wallet.getType());
        typeOfAccount.setSelection(spinnerPosition);

        currentAmount.setFocusable(false);
    }

    private Wallet getValue() {
        String name = nameOfWallet.getText().toString();
        String type = typeOfAccount.getSelectedItem().toString();
        String description = note.getText().toString();
        int initAmount = wallet.getInitialAmount();

        Wallet wallet1 = new Wallet(name, type, description, initAmount);
        wallet1.setCurrentAmount(wallet.getCurrentAmount());

        return wallet1;
    }


    /**
     * add data to typeOftransaction
     * click event.
     */
    private void typeOfAccount () {
        // typeOfAccount
        walletCategoryModel = new WalletCategoryModel(WalletEdit.this);

        typeOfAccount.setAdapter(walletCategoryModel.getNames());
        typeOfAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = typeOfAccount.getSelectedItem().toString();
                if(selected.equals("Create new")){
                    // Create dialog
                    final EditText input = new EditText(WalletEdit.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalletEdit.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnDone) {
            Wallet update = getValue();
            Map<String, Object> updateData = new HashMap<String, Object>();
            updateData.put(walletModel.encrypt("name"), walletModel.encrypt(update.getName()));
            updateData.put(walletModel.encrypt("type"), walletModel.encrypt(update.getType()));
            updateData.put(walletModel.encrypt("description"), walletModel.encrypt(update.getDescription()));

            walletModel.update(key, updateData);

            Intent intent = new Intent(this, WalletDetai.class);
            intent.putExtra("key", key);
            intent.putExtra("wallet", (Serializable) update);
            startActivity(intent);
            WalletEdit.this.finish();

            return true;
        }

        if(id == android.R.id.home){
            WalletEdit.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
