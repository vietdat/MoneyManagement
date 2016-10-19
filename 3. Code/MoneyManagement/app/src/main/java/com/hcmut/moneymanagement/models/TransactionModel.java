package com.hcmut.moneymanagement.models;


import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.objects.Transaction;
import com.hcmut.moneymanagement.objects.Wallet;

import java.lang.reflect.Field;

public class TransactionModel extends Model{
    public final String TYPE_INCOME = "INCOME";
    public final String TYPE_EXPENSE = "EXPENSE";

    public final String CATE_SAVING = "SAVING";
    public final String CATE_WALLET = "WALLET";
    public final String CATE_INVESMENT = "INVESMENT";

    public TransactionModel(){
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("transactions"));
    }

    public void add(Transaction transaction){
        Field[] fields = Wallet.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length - 2; i++){
            try {
                // Get value object of wallet
                String fieldEncrypted = encryption.encrypt(fields[i].getName());
                Object value = fields[i].get(transaction);
                // Log.w("field", fields[i].getName());
                // Log.w("value", value.toString());
                String valueEncrypted = encryption.encrypt(value.toString());

                // Write encypted value to Firebase
                reference.child(key).child(fieldEncrypted).setValue(valueEncrypted);

            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }
}

