package com.hcmut.moneymanagement.activity.Savings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.SavingModel;
import com.hcmut.moneymanagement.objects.Saving;

import java.io.Serializable;

public class SavingDetail extends AppCompatActivity {

    private Toolbar mToolbar;
    Saving saving;
    String key;
    SavingModel savingModel;
    EditText nameOfSaving, goal, currentAmount, startDate, endDate, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.detail_saving_tittle);
        getSupportActionBar().setTitle(title);

        saving = new Saving();

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");
        saving = (Saving) getIntent().getSerializableExtra("saving");
        savingModel = new SavingModel();
        //add data to view
        initData();
    }

    private void initData() {
        nameOfSaving = (EditText) findViewById(R.id.input_name);
        goal = (EditText) findViewById(R.id.goal);
        currentAmount = (EditText) findViewById(R.id.currentAmount);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        description = (EditText) findViewById(R.id.description);
        nameOfSaving.setText(saving.getName());
        goal.setText(saving.getGoal());
        currentAmount.setText(saving.getCurrent_amount());
        startDate.setText(saving.getStartDate());
        endDate.setText(saving.getEndDate());
        description.setText(saving.getDescription());

        nameOfSaving.setFocusable(false);
        goal.setFocusable(false);
        currentAmount.setFocusable(false);
        startDate.setFocusable(false);
        endDate.setFocusable(false);
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
            Intent intent = new Intent(this, SavingEdit.class);
            intent.putExtra("key", key);
            intent.putExtra("saving", (Serializable) saving);
            startActivity(intent);
            SavingDetail.this.finish();
            return true;
        }

        if(id == R.id.mnDelete){
            savingModel.remove(key);
            SavingDetail.this.finish();
            return true;
        }

        if(id == android.R.id.home){
            SavingDetail.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
