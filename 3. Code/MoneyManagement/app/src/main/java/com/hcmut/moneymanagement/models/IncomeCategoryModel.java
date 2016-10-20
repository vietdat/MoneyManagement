package com.hcmut.moneymanagement.models;

import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.objects.Category;

import java.lang.reflect.Field;

public class IncomeCategoryModel extends Model{
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
}
