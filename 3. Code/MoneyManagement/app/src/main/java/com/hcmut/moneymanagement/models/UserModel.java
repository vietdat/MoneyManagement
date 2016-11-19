package com.hcmut.moneymanagement.models;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.objects.Category;
import com.hcmut.moneymanagement.objects.Wallet;


public class UserModel extends Model {
    public UserModel(){
        // Database reference of current user
        reference = FirebaseDatabase.getInstance().getReference().child(uidEncrypted);
        reference.keepSynced(true);
    }

    public void initUserData(){
        IncomeCategoryModel incomeCategoryModel = new IncomeCategoryModel();
        incomeCategoryModel.add(new Category("Salary"));
        incomeCategoryModel.add(new Category("Gift"));
        incomeCategoryModel.add(new Category("Deposit interest"));
        incomeCategoryModel.add(new Category("Selling things"));

        ExpenseCategoryModel expenseCategoryModel = new ExpenseCategoryModel();
        expenseCategoryModel.add(new Category("Bill & Utilities"));
        expenseCategoryModel.add(new Category("Traffic"));
        expenseCategoryModel.add(new Category("Shopping"));
        expenseCategoryModel.add(new Category("Entertainment"));;
        expenseCategoryModel.add(new Category("Travel"));
        expenseCategoryModel.add(new Category("Health"));
        expenseCategoryModel.add(new Category("Education"));
        expenseCategoryModel.add(new Category("Insurance"));
        Log.w("Uid", uidEncrypted);
        WalletCategoryModel walletCategoryModel = new WalletCategoryModel();
        walletCategoryModel.add(new Category("Cash"));
        walletCategoryModel.add(new Category("Bank account"));
        walletCategoryModel.add(new Category("Other"));

        Wallet cash = new Wallet("Cash", "Cash", "My cash");
        Wallet bank  = new Wallet("Vietcombank", "Bank account", "My Vietcombank account");
        WalletModel walletModel = new WalletModel();
        walletModel.add(cash);
        walletModel.add(bank);
    }

    //write username to db
    public void write(String field, String value){
        reference.child(encrypt(field)).setValue(encrypt(value));
    }
}
