package com.hcmut.moneymanagement.activity.FastInput;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private String previousTypeSelected;
    private EditText amouthOfMoney, description, edKey;
    private Spinner typeTransaction, wallet, category;
    private TextView tvCategory, tvWallet;
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

        typeTransaction.setOnItemSelectedListener(onTransactionTypeItemSelected);
        category.setOnItemSelectedListener(onCategoryItemSelected);
    }

    private void initData() {
        previousTypeSelected = "";

        typeTransaction = (Spinner) findViewById(R.id.typeTransaction);
        category = (Spinner) findViewById(R.id.category);
        wallet = (Spinner) findViewById(R.id.wallet);
        amouthOfMoney = (EditText) findViewById(R.id.input_amount);
        description = (EditText) findViewById(R.id.desciption);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvWallet = (TextView) findViewById(R.id.tvWallet);
        adapterController = new AdapterController(this);
        edKey = (EditText) findViewById(R.id.input_key);

        typeTransaction.setAdapter(adapterController.getTransactionTypesAdapter());
        wallet.setAdapter(adapterController.getWalletAdapter());
        category.setAdapter(adapterController.getIncomeCategoryAdapter());

        amouthOfMoney.addTextChangedListener(new InputEditTextFormat(amouthOfMoney, "#,###"));
        fastInputModel = new FastInputModel();
    }

    // On Transaction type item selected
    private AdapterView.OnItemSelectedListener onTransactionTypeItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // If the selection has changed
            String selected = typeTransaction.getSelectedItem().toString();
            if( !previousTypeSelected.equals(selected) ) {
                if (selected.equals(getResources().getString(R.string.income))) {
                    category.setAdapter(adapterController.getIncomeCategoryAdapter());
                } else if (selected.equals(getResources().getString(R.string.expense))) {
                    category.setAdapter(adapterController.getExpenseCategoryAdapter());
                } else if(selected.equals(getResources().getString(R.string.saving))) {
                    category.setAdapter(adapterController.getSavingNameAdapter());
                    tvCategory.setText(getResources().getString(R.string.saving_name));
                } else if(selected.equals(getResources().getString(R.string.transfer))) {
                    category.setAdapter(adapterController.getWalletAdapter());
                    tvCategory.setText(getResources().getString(R.string.to_wallet));
                    tvWallet.setText(getResources().getString(R.string.from_wallet));
                }
                previousTypeSelected = selected;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // On Category item selected
    private AdapterView.OnItemSelectedListener onCategoryItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = category.getSelectedItem().toString();
            if(selected.equals("Create new")){
                // Create dialog
                final EditText input = new EditText(FastInputEdit.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(FastInputEdit.this);
                builder.setTitle(getResources().getString(R.string.new_category));
                builder.setView(input);

                // Add the buttons to Dialogs
                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(previousTypeSelected.equals(getResources().getString(R.string.income))){
                            dialog.dismiss();
                            adapterController.addIncomeCategory(input.getText().toString());
                        }else if(previousTypeSelected.equals(getResources().getString(R.string.expense))){
                            dialog.dismiss();
                            adapterController.addExpenseCategory(input.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                // Create the AlertDialog
                Dialog dialog = builder.create();

                dialog.show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnDone) {
            String typeOfTransactionValue = typeTransaction.getSelectedItem().toString().trim();

            if(amouthOfMoney.getText().toString().equals("")){
                Toast.makeText(FastInputEdit.this,getResources().getString(R.string.input_amount),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            String moneyAmount = amouthOfMoney.getText().toString();
            String edkey = edKey.getText().toString();
            String descriptionValue = description.getText().toString().trim();
            String walletId = adapterController.walletModel.keys
                    .get(wallet.getSelectedItemPosition());
            String categoryId = "";
            if(typeOfTransactionValue.equals("Income")) {
                categoryId = adapterController.incomeCategoryModel.keys.get(category.getSelectedItemPosition());
            }else if(typeOfTransactionValue.equals("Expense")){
                categoryId = adapterController.expenseCategoryModel.keys.get(category.getSelectedItemPosition());
            } else if(typeOfTransactionValue.equals("Saving")){
                categoryId = adapterController.savingModel.keys.get(category.getSelectedItemPosition());
            } else if(typeOfTransactionValue.equals("Transfer")){
                categoryId = adapterController.walletModel.keys.get(category.getSelectedItemPosition());
            }

            FastInput fastInput =
                    new FastInput(edkey,typeOfTransactionValue, moneyAmount, walletId, categoryId, descriptionValue);

            Map<String, Object> updateData = new HashMap<String, Object>();
            updateData.put(fastInputModel.encrypt("key"), fastInputModel.encrypt(fastInput.getKey()));
            updateData.put(fastInputModel.encrypt("money"), fastInputModel.encrypt(fastInput.getMoney()));
            updateData.put(fastInputModel.encrypt("type"), fastInputModel.encrypt(fastInput.getType()));
            updateData.put(fastInputModel.encrypt("wallet"), fastInputModel.encrypt(fastInput.getWallet()));
            updateData.put(fastInputModel.encrypt("category"), fastInputModel.encrypt(fastInput.getCategory()));
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
