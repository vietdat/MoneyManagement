package com.hcmut.moneymanagement.activity.Transaction;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.ExpenseCategoryModel;
import com.hcmut.moneymanagement.models.FastInputModel;
import com.hcmut.moneymanagement.models.IncomeCategoryModel;
import com.hcmut.moneymanagement.models.SavingModel;
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.objects.Category;

import java.util.ArrayList;

public class AdapterController {

    private Context context;

    public WalletModel walletModel;
    public FastInputModel fastInputModel;
    public IncomeCategoryModel incomeCategoryModel;
    public ExpenseCategoryModel expenseCategoryModel;
    public SavingModel savingModel;
    public ArrayList<String> keyFastInput;

    private ArrayAdapter<String> transactionTypeAdapter;
    private ArrayAdapter<String> walletAdapter;
    private ArrayAdapter<String> fastInputAdapter;
    private ArrayAdapter<String> incomeAdapter;
    private ArrayAdapter<String> expenseAdapter;
    private ArrayAdapter<String> savingNameAdapter;

    public AdapterController(Context context) {
        this.context = context;

        // Transaction Type Adapter
        String[] transactionTypes = {context.getResources().getString(R.string.income),
                context.getResources().getString(R.string.expense), context.getResources().getString(R.string.saving),
                context.getResources().getString(R.string.transfer)};
        transactionTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, transactionTypes);
        transactionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Wallet Adapter
        walletModel = new WalletModel();
        fastInputModel = new FastInputModel();
        walletModel.initNameAdapter(context);
        keyFastInput = fastInputModel.keys;
        fastInputModel.initNameAdapter(context);
        walletAdapter = walletModel.getNameAdapter();
        fastInputAdapter = fastInputModel.getNameAdapter();

        // income Category Adapter
        incomeCategoryModel = new IncomeCategoryModel();
        incomeCategoryModel.initSpinnerAdapter(context);
        incomeAdapter = incomeCategoryModel.getNameAdapter();

        // Expense Category Adapter
        expenseCategoryModel = new ExpenseCategoryModel();
        expenseCategoryModel.initSpinnerAdapter(context);
        expenseAdapter = expenseCategoryModel.getNameAdapter();

        savingModel = new SavingModel();
        savingModel.initNameAdapter(context);
        savingNameAdapter = savingModel.getNameAdapter();


    }

    public ArrayAdapter getTransactionTypesAdapter() {
        return transactionTypeAdapter;
    }

    public ArrayAdapter getWalletAdapter(){
        return  walletAdapter;
    }

    public ArrayAdapter getFastInputAdapter(){
        return  fastInputAdapter;
    }

    public ArrayAdapter getIncomeCategoryAdapter(){
        return incomeAdapter;
    }

    public ArrayAdapter getExpenseCategoryAdapter(){
        return expenseAdapter;
    }

    public ArrayAdapter getSavingNameAdapter() {
        return savingNameAdapter;
    }

    public void addIncomeCategory(String input){
        Category category = new Category(input);
        incomeCategoryModel.add(category);
    }

    public void addExpenseCategory(String input){
        Category category = new Category(input);
        expenseCategoryModel.add(category);
    }


}
