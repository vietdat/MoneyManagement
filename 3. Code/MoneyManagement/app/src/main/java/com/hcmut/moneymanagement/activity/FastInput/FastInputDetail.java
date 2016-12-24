package com.hcmut.moneymanagement.activity.FastInput;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Savings.SavingEdit;
import com.hcmut.moneymanagement.models.ChangeCurrency;
import com.hcmut.moneymanagement.models.FastInputModel;
import com.hcmut.moneymanagement.objects.FastInput;

import java.io.Serializable;

public class FastInputDetail extends AppCompatActivity {

    private Toolbar mToolbar;
    FastInput fastInput;
    String key;
    FastInputModel fastInputModel;
    EditText keyWord, type, money, wallet, category, description;
    ChangeCurrency cc = new ChangeCurrency();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_input_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.detail_saving_tittle);
        getSupportActionBar().setTitle(title);

        fastInput = new FastInput();

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");
        fastInput = (FastInput) getIntent().getSerializableExtra("fastinput");
        fastInputModel = new FastInputModel();
        //add data to view
        initData();
    }

    private void initData() {
        keyWord = (EditText) findViewById(R.id.keyword);
        type = (EditText) findViewById(R.id.type);
        money = (EditText) findViewById(R.id.amount);
        wallet = (EditText) findViewById(R.id.wallet);
        category = (EditText) findViewById(R.id.category);
        description = (EditText) findViewById(R.id.description);

        SharedPreferences pre= getSharedPreferences("currency", 0);
        String lang = pre.getString("currency", "0");
        if (lang.equals("1")) {
            money.setText(cc.changeMoneyUSDToVND(fastInput.getMoney()) + "đ");
        } else {
            money.setText("$" + fastInput.getMoney());
        }

        keyWord.setText(fastInput.getKey());
        type.setText(fastInput.getType());
        money.setText(fastInput.getMoney());
        wallet.setText(fastInput.getWallet());
        category.setText(fastInput.getCategory());
        description.setText(fastInput.getDescription());

        keyWord.setFocusable(false);
        type.setFocusable(false);
        money.setFocusable(false);
        wallet.setFocusable(false);
        category.setFocusable(false);
        description.setFocusable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_his, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnUpdate) {
            Intent intent = new Intent(this, FastInputEdit.class);
            intent.putExtra("key", key);
            intent.putExtra("fastinput", (Serializable) fastInput);
            startActivity(intent);
            FastInputDetail.this.finish();
            return true;
        }

        if(id == R.id.mnDelete){
            fastInputModel.remove(key);
            FastInputDetail.this.finish();
            return true;
        }

        if(id == android.R.id.home){
            FastInputDetail.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
