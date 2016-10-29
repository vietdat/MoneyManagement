package com.hcmut.moneymanagement.models;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.activity.Transaction.AddTransactionActivity;

import java.util.ArrayList;

import static com.google.android.gms.internal.zzs.TAG;

public class IncomeCategoryModel extends CategoryModel{
    private Context context;
    private ArrayList<String> names;
    private ArrayAdapter<String> nameAdapter;

    public IncomeCategoryModel(){
        // Wallets refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("incomeCategories"));
    }
    public IncomeCategoryModel(Context context){
        this.context = context;
        //Log.w("uid", uidEncrypted);

        // Wallets refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("incomeCategories"));

        names = new ArrayList<String>();
        nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, names);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Event Listenner
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameAdapter.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object objWallet = categorySnapshot.child(encrypt("name")).getValue();
                    nameAdapter.add(decrypt(objWallet.toString()));
                    //Log.w("Income category", decrypt(objWallet.toString()));
                }
                nameAdapter.add("Create new");
                nameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadIncomeCategory:onCancelled", databaseError.toException());
            }
        });
    }

    public ArrayAdapter<String> getNameAdapter(){
        return nameAdapter;
    }
}
