package com.hcmut.moneymanagement.activity.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.EventModel;
import com.hcmut.moneymanagement.objects.Event;

import java.io.Serializable;

public class EventsDetail extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText input_name, input_end_date, description, spent;
    private EventModel eventModel;
    private String key;
    Event event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.detail_event_tittle);
        getSupportActionBar().setTitle(title);

        event = new Event();

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");
        event = (Event) getIntent().getSerializableExtra("event");
        eventModel = new EventModel();

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

        input_name.setFocusable(false);
        input_end_date.setFocusable(false);
        spent.setFocusable(false);
        description.setFocusable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_detai, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnUpdate) {
            Intent intent = new Intent(this, EventEdit.class);
            intent.putExtra("key", key);
            intent.putExtra("event", (Serializable) event);
            startActivity(intent);
            EventsDetail.this.finish();
            return true;
        }

        if(id == R.id.mnDelete){
            eventModel.remove(key);
            EventsDetail.this.finish();
            return true;
        }

        if(id == android.R.id.home){
            EventsDetail.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
