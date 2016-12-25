package com.hcmut.moneymanagement.activity.FastInput;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Transaction.AdapterController;
import com.hcmut.moneymanagement.models.FastInputModel;
import com.hcmut.moneymanagement.models.InputEditTextFormat;
import com.hcmut.moneymanagement.objects.FastInput;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FastInputEdit extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText amouthOfMoney, description, edKey, typeTransaction, wallet, category;
    FastInput fastInput;
    FastInputModel fastInputModel;
    String key;
    private AdapterController adapterController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_input_edit);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.edit_fast_input);
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
        typeTransaction = (EditText) findViewById(R.id.typeTransaction);
        category = (EditText) findViewById(R.id.category);
        wallet = (EditText) findViewById(R.id.wallet);
        amouthOfMoney = (EditText) findViewById(R.id.input_amount);
        description = (EditText) findViewById(R.id.desciption);
        adapterController = new AdapterController(this);
        edKey = (EditText) findViewById(R.id.input_key);

        edKey.setText(fastInput.getKey());
        typeTransaction.setText(fastInput.getType());
        category.setText(fastInput.getCategory());
        wallet.setText(fastInput.getWallet());
        amouthOfMoney.setText(fastInput.getMoney());
        description.setText(fastInput.getDescription());

        typeTransaction.setEnabled(false);
        category.setEnabled(false);
        wallet.setEnabled(false);

        amouthOfMoney.addTextChangedListener(new InputEditTextFormat(amouthOfMoney, "#,###"));
        fastInputModel = new FastInputModel();
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
            if(amouthOfMoney.getText().toString().equals("")){
                Toast.makeText(FastInputEdit.this,getResources().getString(R.string.input_amount),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            String moneyAmount = amouthOfMoney.getText().toString();
            String edkey = edKey.getText().toString();
            String descriptionValue = description.getText().toString().trim();
            String typeOfTransactionValue = typeTransaction.getText().toString();
            String walletId = wallet.getText().toString();
            String categoryId = category.getText().toString();

            FastInput fastInput =
                    new FastInput(edkey,typeOfTransactionValue, moneyAmount, walletId, categoryId, descriptionValue);

            Map<String, Object> updateData = new HashMap<String, Object>();
            updateData.put(fastInputModel.encrypt("key"), fastInputModel.encrypt(fastInput.getKey()));
            updateData.put(fastInputModel.encrypt("money"), fastInputModel.encrypt(fastInput.getMoney()));
            updateData.put(fastInputModel.encrypt("description"), fastInputModel.encrypt(fastInput.getDescription()));

            fastInputModel.update(key, updateData);

            Intent intent = new Intent(this, FastInputDetail.class);
            intent.putExtra("key", key);
            intent.putExtra("fastinput", (Serializable) fastInput);
            startActivity(intent);
            FastInputEdit.this.finish();

            return true;
        }

        if(id == android.R.id.home) {
            FastInputEdit.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
