package com.hcmut.moneymanagement.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Budget.BudgetAdapter;
import com.hcmut.moneymanagement.objects.Budget;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Admin on 15-Nov-16.
 */
public class BudgetModel extends Model {
    private Context context;
    private ArrayList<String> names;
    public ArrayList<Budget> budgets;
    public ArrayList<Budget> budgetsRunning;
    public ArrayList<Budget> budgetsFinish;
    private ArrayAdapter<String> nameAdapter;
    private BudgetAdapter budgetFinishAdapter;
    public ArrayList<String> keyRunnings, keyFinishs, keys;
    private BudgetAdapter budgetRunningAdapter;

    public BudgetModel(){
        names = new ArrayList<String>();
        budgetsRunning = new ArrayList<Budget>();
        budgetsFinish = new ArrayList<Budget>();
        keyRunnings = new ArrayList<String>();
        keys = new ArrayList<String>();
        keyFinishs = new ArrayList<String>();
        // Budget refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("budget"));
        reference.keepSynced(true);
    }

    //add budget to database
    public void add(Budget budget){
        Field[] fields = Budget.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length; i++){
            try {
                String fieldName = fields[i].getName();

                if( !fieldName.equals("serialVersionUID") && !fieldName.equals("$change")){
                    // Get value object of wallet
                    Object value = fields[i].get(budget);
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

    //init running and finish adapter
    public void initBudgetAdapter(Activity activity){
        budgetRunningAdapter = new BudgetAdapter(activity, R.layout.budget_item, budgetsRunning);
        budgetFinishAdapter = new BudgetAdapter(activity, R.layout.budget_item, budgetsFinish);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                budgetsRunning.clear();
                budgetsFinish.clear();
                keyRunnings.clear();
                keyFinishs.clear();

                // [START_EXCLUDE]
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Budget budget = new Budget();
                    //get name
                    Object obj = snapshot.child(encrypt("name")).getValue();
                    budget.setName(decrypt(obj.toString()));

                    //amount
                    Object objcurrent_unit = snapshot.child(encrypt("amount")).getValue();
                    budget.setAmount(decrypt(objcurrent_unit.toString()));

                    //End date
                    Object objEndDate = snapshot.child(encrypt("endDate")).getValue();
                    budget.setEndDate(decrypt(objEndDate.toString()));

                    //category
                    Object objSpent = snapshot.child(encrypt("category")).getValue();
                    budget.setCategory(decrypt(objSpent.toString()));

                    //current amount
                    Object objCurrentAmount = snapshot.child(encrypt("currentAmount")).getValue();
                    budget.setCurrentAmount(decrypt(objCurrentAmount.toString()));

                    //Description
                    Object objDescription = snapshot.child(encrypt("description")).getValue();
                    budget.setDescription(decrypt(objDescription.toString()));

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date endDate = df.parse(budget.getEndDate());
                        Date now = new Date();
                        Long leftDate = (endDate.getTime() - now.getTime())
                                / (24 * 3600 * 1000) + 1;
                        int i = leftDate.intValue();
                        if(i < 0) {
                            budgetsFinish.add(budget);
                            keyFinishs.add(snapshot.getKey());
                        } else {
                            budgetsRunning.add(budget);
                            keyRunnings.add(snapshot.getKey());
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                budgetRunningAdapter.notifyDataSetChanged();
                budgetFinishAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadEvent:onCancelled", databaseError.toException());
            }
        });
    }

    public void initNameAdapter(Context context){
        nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, names);
        //Event Listenner
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {

                    //End date
                    Object objEndDate = categorySnapshot.child(encrypt("endDate")).getValue();
                    String endDate1 = decrypt(objEndDate.toString());

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date endDate = df.parse(endDate1);
                        Date now = new Date();
                        Long leftDate = (endDate.getTime() - now.getTime())
                                / (24 * 3600 * 1000) + 1;
                        int i = leftDate.intValue();
                        if(i < 0) {
                        } else {
                            Object objWallet = categorySnapshot.child(encrypt("name")).getValue();
                            names.add(decrypt(objWallet.toString()));
                            keys.add(categorySnapshot.getKey());
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                nameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadWallet:onCancelled", databaseError.toException());
            }
        });
    }


    // Update a budget
    public void update(String key, Map<String, Object> data){
        reference.child(key).updateChildren(data);
    }

    public void decreaseMoneyAmount(String key, final int amount){
        final DatabaseReference budgetReference =  FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("budget")).child(key);

        budgetReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object objCurrentAmount = dataSnapshot.child(encrypt("currentAmount")).getValue();
                if(objCurrentAmount != null) {
                    int currentAmount = Integer.parseInt(objCurrentAmount.toString());
                    currentAmount -= amount;
                    Map<String, Object> update = new HashMap<String, Object>();
                    update.put(encrypt("currentAmount"), String.valueOf(currentAmount));

                    budgetReference.updateChildren(update);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void remove(String key){
        reference.child(key).removeValue();
    }

    public BudgetAdapter getBudgetsRunningAdapter() {
        return budgetRunningAdapter;
    }
    public BudgetAdapter getBudgetsFinishAdapter() {
        return budgetFinishAdapter;
    }
}
