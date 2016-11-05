package com.hcmut.moneymanagement.activity.Savings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.SavingModel;
import com.hcmut.moneymanagement.objects.Saving;

import java.util.ArrayList;
import java.util.List;

public class SavingsAdd extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText input_name, input_goal, input_starting_amount, input_start_date, input_end_date,
                    description;
    private Spinner curentUnit;

    private SavingModel savingModel;

    private String input_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_saving);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.add_saving_tittle);
        getSupportActionBar().setTitle(title);

        init();
    }

    //Khoi tao gia tri
    private void init() {
        input_name = (EditText) findViewById(R.id.input_name);
        input_goal = (EditText) findViewById(R.id.input_goal);
        input_starting_amount = (EditText) findViewById(R.id.input_starting_amount);
        input_start_date = (EditText) findViewById(R.id.etStartDate);
        input_end_date = (EditText) findViewById(R.id.etEndtDate);
        description = (EditText) findViewById(R.id.description);

        typeOfCurrency();

        savingModel = new SavingModel();
    }

    private void typeOfCurrency() {
        // currencySpinner
        curentUnit = (Spinner) findViewById(R.id.currencyUnit);

        List<String> currency = new ArrayList<String>();
        currency.add("VND");

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currency);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        curentUnit.setAdapter(currencyAdapter);
    }

    private Saving getValue(){
        String name = input_name.getText().toString();
        String goal = input_goal.getText().toString();
        String starting_amount = input_starting_amount.getText().toString();
        String start_date = input_start_date.getText().toString();
        String end_date = input_end_date.getText().toString();
        String desciption = description.getText().toString();
        String currentUnit = curentUnit.getSelectedItem().toString();

        Saving saving = new Saving(name,goal, starting_amount, currentUnit, start_date,
                                    end_date, desciption);
        return saving;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.mnDone) {
            savingModel.add(getValue());
            savingModel.getReference().addChildEventListener(onSavingChildListener);
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

        input_start_date = (EditText) findViewById(R.id.etStartDate);
        input_end_date = (EditText) findViewById(R.id.etEndtDate);
        input_start_date.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    com.hcmut.moneymanagement.activity.Transaction.DateDialog dialog=new com.hcmut.moneymanagement.activity.Transaction.DateDialog(view);
                    android.app.FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }

        });
        input_end_date.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    com.hcmut.moneymanagement.activity.Transaction.DateDialog dialog=new com.hcmut.moneymanagement.activity.Transaction.DateDialog(view);
                    android.app.FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }

        });
    }

    // on child added
    private ChildEventListener onSavingChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Toast.makeText(SavingsAdd.this,"Add new saving successful",Toast.LENGTH_LONG).show();
            SavingsAdd.this.finish();
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
            Toast.makeText(SavingsAdd.this,"Error Establishing a Database Connection",Toast.LENGTH_LONG).show();
        }
    };
}
