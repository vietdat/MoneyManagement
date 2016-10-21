package com.hcmut.moneymanagement.models;


import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.objects.Wallet;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class WalletModel extends Model{
    private ArrayList<String> incomeCategories;

    public WalletModel(){
        // Wallets refecence
        String wallets = encryption.encrypt("wallets");
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(wallets);
    }

    public void add(Wallet wallet){
        Field[] fields = Wallet.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length; i++){
            try {
                String fieldName = fields[i].getName();
                if( !fieldName.equals("serialVersionUID") && !fieldName.equals("$change")){
                    // Get value object of wallet
                    Object value = fields[i].get(wallet);
                    if(value != null){
                        String valueEncrypted = encryption.encrypt(value.toString());

                        // Write encypted value to Firebase
                        reference.child(key).child(encrypt(fieldName)).setValue(valueEncrypted);
                    }
                }

            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }
}
