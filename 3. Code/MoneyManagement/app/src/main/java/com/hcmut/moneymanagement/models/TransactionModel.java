package com.hcmut.moneymanagement.models;


import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.objects.Transaction;
import com.hcmut.moneymanagement.objects.Wallet;

import java.lang.reflect.Field;

public class TransactionModel extends Model{

    public TransactionModel(){
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("transactions"));
    }

    public void add(Transaction transaction){
        Field[] fields = Transaction.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length; i++){
            try {
                String fieldName = fields[i].getName();
                if( !fieldName.equals("serialVersionUID") && !fieldName.equals("$change")){
                    // Get value object of wallet
                    Object value = fields[i].get(transaction);
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

