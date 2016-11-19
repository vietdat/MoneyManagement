package com.hcmut.moneymanagement.models;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.objects.Category;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Semaphore;

import static com.google.android.gms.internal.zzs.TAG;

public abstract class CategoryModel extends Model{
    protected Context context;
    public ArrayList<String> names;
    public  ArrayList<String> keys;
    protected ArrayAdapter<String> nameAdapter;

    public CategoryModel(){
        keys = new ArrayList<String>();
        names = new ArrayList<String>();
    }

    public void initSpinnerAdapter(Context context){
        this.context = context;

        nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, names);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();
                keys.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object obj = categorySnapshot.child(encrypt("name")).getValue();
                    names.add(decrypt(obj.toString()));
                    keys.add(categorySnapshot.getKey());
                }
                names.add("Create new");
                nameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadCategory:onCancelled", databaseError.toException());
            }
        });
    }

    public void initListViewAdapter(Context context){
        this.context = context;

        nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, names);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();
                keys.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object obj = categorySnapshot.child(encrypt("name")).getValue();
                    names.add(decrypt(obj.toString()));
                    keys.add(categorySnapshot.getKey());
                }
                nameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadCategory:onCancelled", databaseError.toException());
            }
        });
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

    // Update a category
    public void update(String key, Map<String, Object> data){
        reference.child(key).updateChildren(data);
    }

    // Remove a category
    public void remove(String key){
        reference.child(key).removeValue();
    }

    public ArrayAdapter<String> getNameAdapter(){
        if(nameAdapter != null){
            return nameAdapter;
        }
        return null;
    }

    public String getNameByKey(final String key){
        String name = "";

        /*
        final Semaphore semaphore = new Semaphore(0);
        try {
            DatabaseReference itemReference = reference.child(key);
            itemReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Object objName = dataSnapshot.child(encrypt("name")).getValue();

                    name = objName.toString();
                    semaphore.release();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "loadCategory:onCancelled", databaseError.toException());
                }
            });

            semaphore.acquire();
        }catch (InterruptedException ex){
            Log.w(TAG, ex.toString());
        }
        */

        return name;
    }
}
