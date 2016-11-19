package com.hcmut.moneymanagement.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Events.EventsAdapter;
import com.hcmut.moneymanagement.objects.Event;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.internal.zzs.TAG;

public class EventModel extends Model {
    private Context context;
    public ArrayList<Event> events;
    public ArrayList<Event> eventsRunning;
    public ArrayList<Event> eventsFinish;
    private EventsAdapter eventsFinishAdapter;
    public ArrayList<String> keyRunnings, keyFinishs, keys;
    private EventsAdapter eventsRunningAdapter;

    public EventModel(){
        eventsRunning = new ArrayList<Event>();
        eventsFinish = new ArrayList<Event>();
        keyRunnings = new ArrayList<String>();
        keys = new ArrayList<String>();
        keyFinishs = new ArrayList<String>();
        // Event refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("event"));
        reference.keepSynced(true);
    }

    public void initEventAdapter(Activity activity){
        eventsRunningAdapter = new EventsAdapter(activity, R.layout.event_item, eventsRunning);
        eventsFinishAdapter = new EventsAdapter(activity, R.layout.event_item, eventsFinish);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventsRunning.clear();
                eventsFinish.clear();
                keyRunnings.clear();
                keyFinishs.clear();

                // [START_EXCLUDE]
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = new Event();
                    //get name
                    Object obj = snapshot.child(encrypt("name")).getValue();
                    event.setName(decrypt(obj.toString()));

                    //currency unit
                    Object objcurrent_unit = snapshot.child(encrypt("current_unit")).getValue();
                    event.setCurrent_unit(decrypt(objcurrent_unit.toString()));

                    //End date
                    Object objEndDate = snapshot.child(encrypt("endDate")).getValue();
                    event.setEndDate(decrypt(objEndDate.toString()));

                    //Spent
                    Object objSpent = snapshot.child(encrypt("spent")).getValue();
                    event.setSpent(Integer.parseInt(decrypt(objSpent.toString())));

                    //Description
                    Object objDescription = snapshot.child(encrypt("description")).getValue();
                    event.setDescription(decrypt(objDescription.toString()));

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date endDate = df.parse(event.getEndDate());
                        Date now = new Date();
                        Long leftDate = (endDate.getTime() - now.getTime())
                                / (24 * 3600 * 1000) + 1;
                        int i = leftDate.intValue();
                        if(i < 0) {
                            eventsFinish.add(event);
                            keyFinishs.add(snapshot.getKey());
                        } else {
                            eventsRunning.add(event);
                            keyRunnings.add(snapshot.getKey());
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                eventsFinishAdapter.notifyDataSetChanged();
                eventsRunningAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadEvent:onCancelled", databaseError.toException());
            }
        });
    }

    //add event to database
    public void add(Event event){
        Field[] fields = Event.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length; i++){
            try {
                String fieldName = fields[i].getName();

                if( !fieldName.equals("serialVersionUID") && !fieldName.equals("$change")){
                    // Get value object of wallet
                    Object value = fields[i].get(event);
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

    // Update a event
    public void update(String key, Map<String, Object> data){
        reference.child(key).updateChildren(data);
    }

    // Update a amount
    public void increaseMoneyAmount(String key, final int amount){
        final DatabaseReference eventReference =  FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("event")).child(key);

        eventReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object objCurrentAmount = dataSnapshot.child(encrypt("spent")).getValue();
                if(objCurrentAmount != null) {
                    int currentAmount = Integer.parseInt(objCurrentAmount.toString());
                    currentAmount += amount;
                    Map<String, Object> update = new HashMap<String, Object>();
                    update.put(encrypt("spent"), currentAmount);

                    eventReference.updateChildren(update);
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

    public EventsAdapter getEventsRunningAdapter() {
        return eventsRunningAdapter;
    }
    public EventsAdapter getEventsFinishAdapter() {
        return eventsFinishAdapter;
    }
}
