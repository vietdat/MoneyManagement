package com.hcmut.moneymanagement.activity.Transaction;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.hcmut.moneymanagement.models.ExpenseCategoryModel;
import com.hcmut.moneymanagement.models.IncomeCategoryModel;
import com.hcmut.moneymanagement.models.WalletModel;

public class AdapterController {

    private Context context;

    private WalletModel walletModel;
    private IncomeCategoryModel incomeCategoryModel;
    private ExpenseCategoryModel expenseCategoryModel;

    private ArrayAdapter<String> transactionTypeAdapter;
    private ArrayAdapter<String> walletAdapter;
    private ArrayAdapter<String> incomeAdapter;
    private ArrayAdapter<String> expenseAdapter;

    public AdapterController(Context context) {
        this.context = context;

        // Transaction Type Adapter
        String[] transactionTypes = {"Income", "Expense", "Saving", "Transfer"};
        transactionTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, transactionTypes);

        // Wallet Adapter
        walletModel = new WalletModel();
        walletAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, walletModel.getNames());

        // Income Category Adapter
        incomeCategoryModel = new IncomeCategoryModel();
        incomeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, incomeCategoryModel.getNames());

        // Expense Category Adapter
        expenseCategoryModel = new ExpenseCategoryModel();
        expenseAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, expenseCategoryModel.getNames());

    }

    public ArrayAdapter getTransactionTypesAdapter() {
        return transactionTypeAdapter;
    }

    public ArrayAdapter getWalletAdapter(){
        return  walletAdapter;
    }

    public ArrayAdapter getIncomeCategoryAdapter(){
        return incomeAdapter;
    }

    public ArrayAdapter getExpenseCategoryAdapter(){
        return expenseAdapter;
    }

}
