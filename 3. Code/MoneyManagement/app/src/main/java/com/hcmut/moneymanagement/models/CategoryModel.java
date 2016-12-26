package com.hcmut.moneymanagement.models;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Model.ListViewModel;
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

    protected CategoryAdapter customAdapter;
    protected ArrayList<ListViewModel> lvArr;
    protected ArrayList<String> icons;

    protected ArrayAdapter<String> nameAdapter;

    public CategoryModel(){
        keys = new ArrayList<String>();
        names = new ArrayList<String>();
        lvArr = new ArrayList<>();
        icons = new ArrayList<>();
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

    public void initCustomAdapter(Activity context){
        this.context = context;

        customAdapter = new CategoryAdapter(context,  R.layout.tool_item, lvArr);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Icon index
                int icon_index = 0;

                lvArr.clear();
                keys.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object obj = categorySnapshot.child(encrypt("name")).getValue();
                    ListViewModel lv = new ListViewModel();
                    lv.setTitle(decrypt(obj.toString()));
                    lv.setIcon(icons.get(icon_index));
                    if( icon_index < icons.size() - 1 ){
                        icon_index += 1;
                    }
                    lvArr.add(lv);
                    keys.add(categorySnapshot.getKey());
                }
                customAdapter.notifyDataSetChanged();
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

    public ArrayAdapter<ListViewModel> getCustomAdapter(){
        return customAdapter;
    }
}
