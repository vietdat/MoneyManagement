package com.hcmut.moneymanagement.activity.Wallet;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.WalletCategoryModel;
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.objects.Category;
import com.hcmut.moneymanagement.objects.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletDetai extends AppCompatActivity {

    int position;
    Wallet wallet;
    WalletModel walletModel;
    EditText nameOfWallet, currentAmount, note;
    Spinner typeOfAccount, currency;
    WalletCategoryModel walletCategoryModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detai);

        Bundle extras = getIntent().getExtras();
        position = extras.getInt("position");

        //add data to view
        initData();






    }

    private void initData() {
        wallet = new Wallet();
        walletModel = new WalletModel();
        walletModel.initWalletAdapter(this);
        wallet = walletModel.getWallet(position);

        nameOfWallet = (EditText) findViewById(R.id.input_name);
        currentAmount = (EditText) findViewById(R.id.startMoney);
        note = (EditText) findViewById(R.id.note);
        typeOfAccount = (Spinner) findViewById(R.id.typeOfAccount);
        currency = (Spinner) findViewById(R.id.currency);
        typeOfCurrency();
        typeOfAccount();

        nameOfWallet.setFocusable(false);
        currentAmount.setFocusable(false);
        note.setFocusable(false);
        typeOfAccount.setEnabled(false);
        currency.setEnabled(false);

        nameOfWallet.setText(wallet.getName());
        currentAmount.setText(wallet.getCurrentAmount());
        note.setText(wallet.getDescription());
        if (!wallet.getType().equals(null)) {
            int spinnerPosition = walletCategoryModel.getNames().getPosition(wallet.getType());
            typeOfAccount.setSelection(spinnerPosition);
        }


    }

    /**
     * add data to typeOftransaction
     * click event.
     */
    private void typeOfAccount () {
        // typeOfAccount
        walletCategoryModel = new WalletCategoryModel(WalletDetai.this);

        typeOfAccount.setAdapter(walletCategoryModel.getNames());
        typeOfAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = typeOfAccount.getSelectedItem().toString();
                if(selected.equals("Create new")){
                    // Create dialog
                    final EditText input = new EditText(WalletDetai.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalletDetai.this);
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
}
