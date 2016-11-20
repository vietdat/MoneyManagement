package com.hcmut.moneymanagement.activity.Events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.EventModel;
import com.hcmut.moneymanagement.objects.Event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EventEdit extends AppCompatActivity {

    private Toolbar mToolbar;
    Event event;
    String key;
    private EditText input_name, input_end_date, description, spent;
    EventModel eventModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.edit_events_tittle);
        getSupportActionBar().setTitle(title);

        event = new Event();

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");
        event = (Event) getIntent().getSerializableExtra("event");
        eventModel = new EventModel();

        //add data to view
        initData();
    }

    private void initData() {
        input_name = (EditText) findViewById(R.id.input_name);
        input_end_date = (EditText) findViewById(R.id.etEndtDate);
        spent = (EditText) findViewById(R.id.spent);
        description = (EditText) findViewById(R.id.description);

        input_name.setText(event.getName());
        input_end_date.setText(event.getEndDate());
        spent.setText(String.valueOf(event.getSpent()));
        description.setText(event.getDescription());

        input_end_date.setShowSoftInputOnFocus(false);
        spent.setFocusable(false);
    }

    private Event getValue() {
        String name = input_name.getText().toString();
        String end_date = input_end_date.getText().toString();
        int spent1 = event.getSpent();
        String desciption1 = description.getText().toString();

        Event event = new Event(name, end_date, desciption1, spent1);
        return event;
    }

    @Override
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnDone) {
            Event update = getValue();
            Map<String, Object> updateData = new HashMap<String, Object>();
            updateData.put(eventModel.encrypt("name"), eventModel.encrypt(update.getName()));
            updateData.put(eventModel.encrypt("endDate"), eventModel.encrypt(update.getEndDate()));
            updateData.put(eventModel.encrypt("description"), eventModel.encrypt(update.getDescription()));

            eventModel.update(key, updateData);

            Intent intent = new Intent(this, EventsDetail.class);
            intent.putExtra("key", key);
            intent.putExtra("event", (Serializable) update);
            startActivity(intent);
            EventEdit.this.finish();

            return true;
        }

        if(id == android.R.id.home){
            EventEdit.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
