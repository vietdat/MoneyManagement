package com.hcmut.moneymanagement.models;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.objects.Category;

import java.util.ArrayList;

import static com.google.android.gms.internal.zzs.TAG;

public class WalletCategoryModel extends CategoryModel {
    public ArrayList<String> names;

    public WalletCategoryModel(){
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("walletCategories"));

        names = new ArrayList<String>();

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
