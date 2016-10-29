package com.hcmut.moneymanagement.models;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.objects.Category;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

public abstract class CategoryModel extends Model{
    public CategoryModel(){
    }

    public void add(Category category){
        Field[] fields = Category.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length; i++){
            try {
                String fieldName = fields[i].getName();
                if( !fieldName.equals("serialVersionUID") && !fieldName.equals("$change")){
                    // Get value object of wallet
                    Object value = fields[i].get(category);
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
