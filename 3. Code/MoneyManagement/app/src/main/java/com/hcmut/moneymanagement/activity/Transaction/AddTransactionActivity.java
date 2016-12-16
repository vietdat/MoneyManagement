package com.hcmut.moneymanagement.activity.Transaction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.BudgetModel;
import com.hcmut.moneymanagement.models.EventModel;
import com.hcmut.moneymanagement.models.InputEditTextFormat;
import com.hcmut.moneymanagement.models.TransactionModel;
import com.hcmut.moneymanagement.objects.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity implements OnClickListener {

    private Toolbar mToolbar;
    private EditText dateView;
    private EditText amouthOfMoney;
    private EditText description;
    private Spinner typeTransaction;
    private TextView tvCategory;
    private Spinner wallet;
    private TextView tvWallet;
    private Spinner category;

    private AdapterController adapterController;
    private String previousTypeSelected;

    private TransactionModel transactionModel;
    private EventModel eventModel;
    private BudgetModel budgetModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.add_transaction_title);
        getSupportActionBar().setTitle(title);

        init();

        typeTransaction.setOnItemSelectedListener(onTransactionTypeItemSelected);
        category.setOnItemSelectedListener(onCategoryItemSelected);

    }

    private void  init(){
        previousTypeSelected = "";

        dateView = (EditText) findViewById(R.id.input_date);
        dateView.setInputType(InputType.TYPE_NULL);
        // Set default date is today
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String today = df.format(c.getTime());
        dateView.setText(today);
        dateView.setOnClickListener(this);

        typeTransaction = (Spinner) findViewById(R.id.typeTransaction);
        category = (Spinner) findViewById(R.id.category);
        wallet = (Spinner) findViewById(R.id.wallet);
        amouthOfMoney = (EditText) findViewById(R.id.input_amount);
        description = (EditText) findViewById(R.id.desciption);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvWallet = (TextView) findViewById(R.id.tvWallet);
        adapterController = new AdapterController(this);

        typeTransaction.setAdapter(adapterController.getTransactionTypesAdapter());
        wallet.setAdapter(adapterController.getWalletAdapter());
        category.setAdapter(adapterController.getIncomeCategoryAdapter());

        transactionModel = new TransactionModel();
        eventModel = new EventModel();
        eventModel.initEventAdapter(AddTransactionActivity.this);
        budgetModel = new BudgetModel();
        budgetModel.initBudgetAdapter(AddTransactionActivity.this);
        amouthOfMoney.addTextChangedListener(new InputEditTextFormat(amouthOfMoney, "#,###"));

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
                final EditText input = new EditText(AddTransactionActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTransactionActivity.this);
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

    //Transaction on child added
    private ChildEventListener onTransactionChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Toast.makeText(AddTransactionActivity.this,getResources().getString(R.string.add_transaction_successful),
                    Toast.LENGTH_SHORT).show();
            AddTransactionActivity.this.finish();
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
            Toast.makeText(AddTransactionActivity.this,getResources().getString(R.string.database_err),
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
            String typeOfTransactionValue = typeTransaction.getSelectedItem().toString().trim();

            if(amouthOfMoney.getText().toString().equals("")){
                Toast.makeText(AddTransactionActivity.this,getResources().getString(R.string.input_amount),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            int moneyAmount = Integer.parseInt(amouthOfMoney.getText().toString().substring(1));
            String dateViewValue = dateView.getText().toString().trim();
            String descriptionValue = description.getText().toString().trim();
            String walletId = adapterController.walletModel.keys
                    .get(wallet.getSelectedItemPosition());

            if(typeOfTransactionValue.equals("Income")) {
                String categoryId = adapterController.incomeCategoryModel.keys.get(category.getSelectedItemPosition());
                Transaction transaction =
                        new Transaction(typeOfTransactionValue, moneyAmount, dateViewValue, walletId, categoryId, descriptionValue);

                transactionModel.add(transaction);
                //Child added handler
                transactionModel.getReference().addChildEventListener(onTransactionChildListener);
                adapterController.walletModel.increaseMoneyAmount(walletId, moneyAmount);

                //Check event. If event is running => auto add transaction to event
                ArrayList<String> arr = eventModel.keyRunnings;
                for(int i = 0; i < arr.size(); i++) {
                    eventModel.increaseMoneyAmount(arr.get(i), moneyAmount);
                }

            }else if(typeOfTransactionValue.equals("Expense")){
                String categoryId = adapterController.expenseCategoryModel.keys.get(category.getSelectedItemPosition());
                Transaction transaction =
                        new Transaction(typeOfTransactionValue, moneyAmount, dateViewValue, walletId, categoryId, descriptionValue);
                transactionModel.add(transaction);
                //Child added handler
                transactionModel.getReference().addChildEventListener(onTransactionChildListener);
                adapterController.walletModel.decreateMoneyAmount(walletId, moneyAmount);

                //Check event. If event is running => auto add transaction to event
                ArrayList<String> arr = eventModel.keyRunnings;
                for(int i = 0; i < arr.size(); i++) {
                    eventModel.increaseMoneyAmount(arr.get(i), -moneyAmount);
                }

                //Check budget. If budget is running => auto add transaction to budget
                ArrayList<String> arrBudgets = budgetModel.keyRunnings;
                for(int i = 0; i < arrBudgets.size(); i++) {
                    budgetModel.decreaseMoneyAmount(arrBudgets.get(i), moneyAmount);
                }

            } else if(typeOfTransactionValue.equals("Saving")){
                String categoryId = adapterController.savingModel.keys.get(category.getSelectedItemPosition());
                Transaction transaction =
                        new Transaction(typeOfTransactionValue, moneyAmount, dateViewValue, walletId, categoryId, descriptionValue);

                transactionModel.add(transaction);
                //Child added handler
                transactionModel.getReference().addChildEventListener(onTransactionChildListener);
                adapterController.walletModel.decreateMoneyAmount(walletId, moneyAmount);
                adapterController.savingModel.increaseMoneyAmount(categoryId, moneyAmount);
            } else if(typeOfTransactionValue.equals("Transfer")){
                String categoryId = adapterController.walletModel.keys.get(category.getSelectedItemPosition());
                Transaction transaction =
                        new Transaction(typeOfTransactionValue, moneyAmount, dateViewValue, walletId, categoryId, descriptionValue);

                transactionModel.add(transaction);
                //Child added handler
                transactionModel.getReference().addChildEventListener(onTransactionChildListener);
                adapterController.walletModel.decreateMoneyAmount(walletId, moneyAmount);
                adapterController.walletModel.increaseMoneyAmount(categoryId, moneyAmount);
            }
            return true;
        }

        if(id == android.R.id.home) {
            AddTransactionActivity.this.finish();
            return true;
        }
            return super.onOptionsItemSelected(item);
    }

    public void onStart(){
        super.onStart();
        dateView.setOnFocusChangeListener(new View.OnFocusChangeListener(){
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
    public void onClick(View view) {
        if(view == dateView) {
            com.hcmut.moneymanagement.activity.Transaction.DateDialog dialog =
                    new com.hcmut.moneymanagement.activity.Transaction.DateDialog(view);
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

            InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);
            dialog.show(ft, "DatePicker");
        }
    }

}
