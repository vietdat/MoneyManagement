package com.hcmut.moneymanagement.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.internal.zzs.TAG;

public class IncomeCategoryModel extends CategoryModel{
    public IncomeCategoryModel(){
        // Wallets refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("incomeCategories"));
        //Event Listenner
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object objWallet = categorySnapshot.child(encrypt("name")).getValue();
                    names.add(decrypt(objWallet.toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
