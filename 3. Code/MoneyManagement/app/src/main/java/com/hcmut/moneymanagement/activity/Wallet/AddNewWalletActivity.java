package com.hcmut.moneymanagement.activity.Wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import com.hcmut.moneymanagement.R;

public class AddNewWalletActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String title = getString(R.string.add_wallet_title);
        getSupportActionBar().setTitle(title);

        // typeSpinner
        Spinner typeSpinner = (Spinner) findViewById(R.id.spType);

        List<String> types = new ArrayList<String>();
        types.add("Tiền mặt");
        types.add("Tài khoản ngân hàng");
        types.add("Thêm mới...");

        final ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(item.toString().equals("Thêm mới...")){
                    Log.w("add", "haha");
                    typeAdapter.add("haha");
                    typeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // currencySpinner
        Spinner currencySpinner = (Spinner) findViewById(R.id.spCurrency);

        List<String> currency = new ArrayList<String>();
        currency.add("VND");

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currency);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_transaction, menu);
        return true;
    }



    public void onStart(){
        super.onStart();
    }
}
