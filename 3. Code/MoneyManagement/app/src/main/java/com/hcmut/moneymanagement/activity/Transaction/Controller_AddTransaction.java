package com.hcmut.moneymanagement.activity.Transaction;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

/**
 * Created by Admin on 16-Oct-16.
 * Class use add date to view
 */
public class Controller_AddTransaction {

    private Calendar calendar;
    private EditText dateView;
    private Context context;
    private MaterialBetterSpinner typeOfTransaction;
    private MaterialBetterSpinner category;
    private MaterialBetterSpinner wallet;

    public Controller_AddTransaction(Context context, MaterialBetterSpinner typeOfTransaction,
                                     MaterialBetterSpinner category, MaterialBetterSpinner wallet) {
        this.context = context;
        this.typeOfTransaction = typeOfTransaction;
        this.wallet = wallet;
        this.category = category;
    }

    //Add Type of transaction
    //menu income, expense
    public void showTypeTransaction() {
        String[] SPINNERLIST = {"Income", "Expense", "Saving", "Transfer"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        typeOfTransaction.setAdapter(arrayAdapter);
    }

    public void showCategorys(){
        String[] SPINNERLIST = {"1", "2", "3", "4"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        category.setAdapter(arrayAdapter);
    }

    public void showWallets(){
        String[] SPINNERLIST = {"1", "2", "3", "4"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        wallet.setAdapter(arrayAdapter);
    }
    //Add date of transaction
    public void showDateInTransaction() {
        //Get current date

    }


}
