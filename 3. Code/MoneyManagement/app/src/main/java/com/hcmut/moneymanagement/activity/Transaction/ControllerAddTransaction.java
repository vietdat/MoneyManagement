package com.hcmut.moneymanagement.activity.Transaction;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.objects.Wallet;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;

import static com.google.android.gms.internal.zzs.TAG;

public class ControllerAddTransaction {

    private Calendar calendar;
    private EditText dateView;
    private Context context;
    private MaterialBetterSpinner typeOfTransaction;
    private MaterialBetterSpinner category;
    private MaterialBetterSpinner wallet;

    public ControllerAddTransaction(Context context, MaterialBetterSpinner typeOfTransaction,
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

    public void showCategories(){
        String[] lstCategories = {"1", "2", "3", "4"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstCategories);
        category.setAdapter(arrayAdapter);
    }

    public void showWallets(){
        final ArrayList<String> lstWallets = new ArrayList<String>();

        final WalletModel walletModel = new WalletModel();
        DatabaseReference walletReference = walletModel.getReference();
        walletReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot walletSnapshot : dataSnapshot.getChildren()) {
                    Object objWallet = walletSnapshot.child(walletModel.encrypt("name")).getValue();
                    Log.w("Wallet-name", walletModel.decrypt(objWallet.toString()));
                    lstWallets.add(walletModel.decrypt(objWallet.toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        ArrayAdapter<String> walletAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstWallets);
        wallet.setAdapter(walletAdapter);

    }
    //Add date of transaction
    public void showDateInTransaction() {
        //Get current date

    }

    //get data after input in transaction



}
