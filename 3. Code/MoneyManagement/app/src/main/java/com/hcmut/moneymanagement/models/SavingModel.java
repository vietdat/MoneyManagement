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
import com.hcmut.moneymanagement.activity.Savings.SavingAdapter;
import com.hcmut.moneymanagement.objects.Saving;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.internal.zzs.TAG;

public class SavingModel extends Model {
    private Context context;
    private ArrayList<String> names;
    public ArrayList<Saving> savings;
    public ArrayList<Saving> savingsRunning;
    public ArrayList<Saving> savingsFinish;
    private ArrayAdapter<String> nameAdapter;
    private SavingAdapter savingsFinishAdapter;
    public ArrayList<String> keyRunnings, keyFinishs, keys;
    private SavingAdapter savingsRunningAdapter;

    public SavingModel(){
        names = new ArrayList<String>();
        savingsRunning = new ArrayList<Saving>();
        savingsFinish = new ArrayList<Saving>();
        keyRunnings = new ArrayList<String>();
        keys = new ArrayList<String>();
        keyFinishs = new ArrayList<String>();
        // Saving refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("saving"));
        reference.keepSynced(true);
    }

    public void initSavingAdapter(Activity activity){

        savingsRunningAdapter = new SavingAdapter(activity, R.layout.saving_item, savingsRunning);
        savingsFinishAdapter = new SavingAdapter(activity, R.layout.saving_item, savingsFinish);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                savingsRunning.clear();
                savingsFinish.clear();
                keyRunnings.clear();
                keyFinishs.clear();
                // [START_EXCLUDE]
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Saving saving = new Saving();
                    //get name
                    Object obj = snapshot.child(encrypt("name")).getValue();
                    saving.setName(decrypt(obj.toString()));

                    //get goal
                    Object objGoal = snapshot.child(encrypt("goal")).getValue();
                    saving.setGoal(decrypt(objGoal.toString()));

                    //currency amount
                    Object objcurrent_amount = snapshot.child(encrypt("current_amount")).getValue();
                    saving.setCurrent_amount(decrypt(objcurrent_amount.toString()));

                    //currency unit
                    Object objcurrent_unit = snapshot.child(encrypt("current_unit")).getValue();
                    saving.setCurrent_unit(decrypt(objcurrent_unit.toString()));

                    //Satrt date
                    Object objStartDate = snapshot.child(encrypt("startDate")).getValue();
                    saving.setStartDate(decrypt(objStartDate.toString()));

                    //End date
                    Object objEndDate = snapshot.child(encrypt("endDate")).getValue();
                    saving.setEndDate(decrypt(objEndDate.toString()));

                    //Description
                    Object objDescription = snapshot.child(encrypt("description")).getValue();
                    saving.setDescription(decrypt(objDescription.toString()));

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date endDate = df.parse(saving.getEndDate());
                        Date now = new Date();
                        Long leftDate = (endDate.getTime() - now.getTime())
                                / (24 * 3600 * 1000) + 1;
                        int i = leftDate.intValue();
                        if(i < 0) {
                            savingsFinish.add(saving);
                            keyFinishs.add(snapshot.getKey());
                        } else {
                            savingsRunning.add(saving);
                            keyRunnings.add(snapshot.getKey());
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                savingsRunningAdapter.notifyDataSetChanged();
                savingsFinishAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadWallet:onCancelled", databaseError.toException());
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

    public void increaseMoneyAmount(String key, final int amount){
        final DatabaseReference savingReference =  FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("saving")).child(key);

        savingReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object objCurrentAmount = dataSnapshot.child(encrypt("current_amount")).getValue();
                if(objCurrentAmount != null) {
                    int currentAmount = Integer.parseInt(decrypt(objCurrentAmount.toString()));
                    currentAmount += amount;
                    Map<String, Object> update = new HashMap<String, Object>();
                    update.put(encrypt("current_amount"), currentAmount);

                    savingReference.updateChildren(update);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void add(Saving saving){
        Field[] fields = Saving.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length; i++){
            try {
                String fieldName = fields[i].getName();

                if( !fieldName.equals("serialVersionUID") && !fieldName.equals("$change")){
                    // Get value object of wallet
                    Object value = fields[i].get(saving);
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

    // Update a saving
    public void update(String key, Map<String, Object> data){
        reference.child(key).updateChildren(data);
    }

    public void remove(String key){
        reference.child(key).removeValue();
    }

    public ArrayAdapter<String> getNameAdapter(){
        if(nameAdapter != null) {
            return nameAdapter;
        }
        return  null;
    }
    public SavingAdapter getSavingsRunningAdapter() {
        return savingsRunningAdapter;
    }
    public SavingAdapter getSavingsFinishAdapter() {
        return savingsFinishAdapter;
    }
 }
