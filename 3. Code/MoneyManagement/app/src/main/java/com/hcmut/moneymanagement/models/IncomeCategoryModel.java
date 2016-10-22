package com.hcmut.moneymanagement.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.objects.Category;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.google.android.gms.internal.zzs.TAG;

public class IncomeCategoryModel extends Model{
    private ArrayList<String> incomeCategories;

    public IncomeCategoryModel(){
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("incomeCategories"));
    }

    public void add(Category category){
        Field[] fields = Category.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length - 2; i++){
            try {
                // Get value object of wallet
                String fieldEncrypted = encryption.encrypt(fields[i].getName());
                Object value = fields[i].get(category);

                String valueEncrypted = encryption.encrypt(value.toString());

                // Write encypted value to Firebase
                reference.child(key).child(fieldEncrypted).setValue(valueEncrypted);

            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getNames(){
        incomeCategories = new ArrayList<String>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object objWallet = categorySnapshot.child(encrypt("text")).getValue();
                    incomeCategories.add(decrypt(objWallet.toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        return incomeCategories;
    }
}
