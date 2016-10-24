package com.hcmut.moneymanagement.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.internal.zzs.TAG;

public class ExpenseCategoryModel extends CategoryModel {
    public ExpenseCategoryModel(){
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("expenseCategories"));

        //Event Listenner
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object objWallet = categorySnapshot.child(encrypt("name")).getValue();
                    names.add(decrypt(objWallet.toString()));
                }
                names.add("Other");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
