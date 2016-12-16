package com.hcmut.moneymanagement.activity.Events;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.EventModel;
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.objects.Event;

public class EventsAdd extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText input_name, input_end_date, description, startDate;
    private WalletModel walletModel;

    private EventModel eventModel;

    private String input_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_add);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.add_event_tittle);
        getSupportActionBar().setTitle(title);

        init();
    }

    //Khoi tao gia tri
    private void init() {
        input_name = (EditText) findViewById(R.id.input_name);
        input_end_date = (EditText) findViewById(R.id.etEndtDate);
        startDate = (EditText) findViewById(R.id.etStartDate);
        description = (EditText) findViewById(R.id.description);

        walletModel = new WalletModel();
        walletModel.initNameAdapter(getApplicationContext());

        input_end_date.setShowSoftInputOnFocus(false);
        startDate.setShowSoftInputOnFocus(false);

        eventModel = new EventModel();
    }

    private Event getValue(){
        String name = input_name.getText().toString();
        String end_date = input_end_date.getText().toString();
        String start_date = startDate.getText().toString();
        String desciption = description.getText().toString();

        Event event = new Event(name, start_date, end_date, desciption);

        return event;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.mnDone) {
            eventModel.add(getValue());
            eventModel.getReference().addChildEventListener(onSavingChildListener);
            return true;
        }

        if(id == android.R.id.home){
            EventsAdd.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_add, menu);
        return true;
    }

    public void onStart(){
        super.onStart();
        input_end_date = (EditText) findViewById(R.id.etEndtDate);
        input_end_date.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    com.hcmut.moneymanagement.activity.Transaction.DateDialog dialog=new com.hcmut.moneymanagement.activity.Transaction.DateDialog(view);
                    android.app.FragmentTransaction ft =getFragmentManager().beginTransaction();
                    InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    dialog.show(ft, "DatePicker");
                }
            }

        });

        startDate = (EditText) findViewById(R.id.etStartDate);
        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    com.hcmut.moneymanagement.activity.Transaction.DateDialog dialog=new com.hcmut.moneymanagement.activity.Transaction.DateDialog(view);
                    android.app.FragmentTransaction ft =getFragmentManager().beginTransaction();
                    InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    dialog.show(ft, "DatePicker");
                }
            }

        });
    }

    // on child added
    private ChildEventListener onSavingChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Toast.makeText(EventsAdd.this,getResources().getString(R.string.new_event),Toast.LENGTH_SHORT).show();
            EventsAdd.this.finish();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(EventsAdd.this,getResources().getString(R.string.database_err),Toast.LENGTH_LONG).show();
        }
    };
}
