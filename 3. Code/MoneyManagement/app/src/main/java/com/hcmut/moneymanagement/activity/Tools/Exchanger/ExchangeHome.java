package com.hcmut.moneymanagement.activity.Tools.Exchanger;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hcmut.moneymanagement.R;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class ExchangeHome extends AppCompatActivity {

    private Toolbar mToolbar;
    private Spinner type;
    private EditText usd, vnd, jpy, cny, rud, eur, amount;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.exchange);
        getSupportActionBar().setTitle(title);

        init();
        exchange();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            ExchangeHome.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        type = (Spinner) findViewById(R.id.typeCurrency);
        amount = (EditText) findViewById(R.id.input_amount);
        usd = (EditText) findViewById(R.id.usd);
        jpy = (EditText) findViewById(R.id.jpy);
        cny = (EditText) findViewById(R.id.cny);
        rud = (EditText) findViewById(R.id.rud);
        eur = (EditText) findViewById(R.id.eur);
        vnd = (EditText) findViewById(R.id.vnd);

        String[] cu = getResources().getStringArray(R.array.currency_array);
        List<String> list = Arrays.asList(cu);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(dataAdapter);

        //disable
        disableEditText(usd);
        disableEditText(vnd);
        disableEditText(eur);
        disableEditText(jpy);
        disableEditText(cny);
        disableEditText(rud);
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void exchange() {
        amount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() > 0) {
                    if(type.getSelectedItem().toString().equals("USD")) {
                        Double usd = Double.parseDouble(s.toString());
                        setValue(usd);
                    }
                    else if(type.getSelectedItem().toString().equals("VND")) {
                        Double usd = VNDtoUSD(Double.parseDouble(s.toString()));
                        setValue(usd);
                    }
                    else if(type.getSelectedItem().toString().equals("EUR")) {
                        Double usd = EURtoUSD(Double.parseDouble(s.toString()));
                        setValue(usd);
                    }
                    else if(type.getSelectedItem().toString().equals("JPY")) {
                        Double usd = JPYtoUSD(Double.parseDouble(s.toString()));
                        setValue(usd);
                    }
                    else if(type.getSelectedItem().toString().equals("RUB")) {
                        Double usd = RUBtoUSD(Double.parseDouble(s.toString()));
                        setValue(usd);
                    }
                    else if(type.getSelectedItem().toString().equals("CNY")) {
                        Double usd = CNYtoUSD(Double.parseDouble(s.toString()));
                        setValue(usd);
                    }
                } else {
                    usd.setText("");
                    vnd.setText("");
                    eur.setText("");
                    jpy.setText("");
                    cny.setText("");
                    rud.setText("");
                }
            }
        });
    }

    private void setValue(Double amount) {
        usd.setText(USDtoUSD(amount));
        vnd.setText(USDtoVND(amount));
        eur.setText(USDtoJPY(amount));
        jpy.setText(USDtoCNY(amount));
        cny.setText(USDtoEUR(amount));
        rud.setText(USDtoRUB(amount));
    }

    private Double VNDtoUSD(Double usd) {
        return usd*22680;
    }

    private Double JPYtoUSD(Double usd) {
        return usd*113.21;
    }
    private Double CNYtoUSD(Double usd) {
        return usd*6.92;
    }
    private Double EURtoUSD(Double usd) {
        return usd*0.94;
    }
    private Double RUBtoUSD(Double usd) {
        return usd*64.94;
    }

    private String USDtoUSD(Double usd) {
        return String.valueOf(df.format(usd));
    }

    private String USDtoVND(Double usd) {
        return String.valueOf(df.format(usd*22680));
    }

    private String USDtoJPY(Double usd) {
        return String.valueOf(df.format(usd*113.21));
    }

    private String USDtoCNY(Double usd) {
        return String.valueOf(df.format(usd*6.92));
    }

    private String USDtoEUR(Double usd) {
        return String.valueOf(df.format(usd*0.94));
    }

    private String USDtoRUB(Double usd) {
        return String.valueOf(df.format(usd*64.94));
    }
}
