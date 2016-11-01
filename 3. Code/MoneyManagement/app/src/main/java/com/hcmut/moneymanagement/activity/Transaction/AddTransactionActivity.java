package com.hcmut.moneymanagement.activity.Transaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.TransactionModel;
import com.hcmut.moneymanagement.objects.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity implements OnClickListener {

    private Toolbar mToolbar;
    private EditText dateView;
    private EditText amouthOfMoney;
    private EditText description;
    private Spinner typeTransaction;
    private Spinner wallet;
    private Spinner category;
    private Button btnSaving;

    private AdapterController adapterController;
    private String previousTypeSelected;

    private TransactionModel transactionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String title = getString(R.string.add_transaction_title);
        getSupportActionBar().setTitle(title);

        init();

        typeTransaction.setOnItemSelectedListener(onTransactionTypeItemSelected);
        category.setOnItemSelectedListener(onCategoryItemSelected);
        btnSaving.setOnClickListener(onSavingClickListener);
    }

    private void  init(){
        previousTypeSelected = "";

        dateView = (EditText) findViewById(R.id.input_date);
        // Set default date is today
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String today = df.format(c.getTime());
        dateView.setText(today);

        typeTransaction = (Spinner) findViewById(R.id.typeTransaction);
        category = (Spinner) findViewById(R.id.category);
        wallet = (Spinner) findViewById(R.id.wallet);
        amouthOfMoney = (EditText) findViewById(R.id.input_amount);
        description = (EditText) findViewById(R.id.desciption);
        btnSaving = (Button) findViewById(R.id.btnSaving);

        adapterController = new AdapterController(this);

        typeTransaction.setAdapter(adapterController.getTransactionTypesAdapter());
        wallet.setAdapter(adapterController.getWalletAdapter());
        category.setAdapter(adapterController.getIncomeCategoryAdapter());

        transactionModel = new TransactionModel();
    }

    // On Transaction type item selected
    private AdapterView.OnItemSelectedListener onTransactionTypeItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // If the selection has changed
            String selected = typeTransaction.getSelectedItem().toString();
            if( !previousTypeSelected.equals(selected) ) {
                if (selected.equals("Income")) {
                    category.setAdapter(adapterController.getIncomeCategoryAdapter());
                } else if (selected.equals("Expense")) {
                    category.setAdapter(adapterController.getExpenseCategoryAdapter());
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
                builder.setTitle("New category");
                builder.setView(input);

                // Add the buttons to Dialogs
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(previousTypeSelected.equals("Income")){
                            dialog.dismiss();
                            adapterController.addIncomeCategory(input.getText().toString());
                        }else if(previousTypeSelected.equals("Expense")){
                            dialog.dismiss();
                            adapterController.addExpenseCategory(input.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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


    // On Saving Click Listener
    private OnClickListener onSavingClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String typeOfTransactionValue = typeTransaction.getSelectedItem().toString().trim();
            int moneyAmount = Integer.parseInt(amouthOfMoney.getText().toString());
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

            }else if(typeOfTransactionValue.equals("Expense")){
                String categoryId = adapterController.expenseCategoryModel.keys.get(category.getSelectedItemPosition());
                Transaction transaction =
                        new Transaction(typeOfTransactionValue, moneyAmount, dateViewValue, walletId, categoryId, descriptionValue);

                transactionModel.add(transaction);
                //Child added handler
                transactionModel.getReference().addChildEventListener(onTransactionChildListener);
                adapterController.walletModel.decreateMoneyAmount(walletId, moneyAmount);

            }
        }
    };

    //Transaction on child added
    private ChildEventListener onTransactionChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Toast.makeText(AddTransactionActivity.this,"Successful",Toast.LENGTH_LONG).show();
            //AddTransactionActivity.this.finish();
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
            Toast.makeText(AddTransactionActivity.this,"Error Establishing a Database Connection",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }



    public void onStart(){
        super.onStart();

        EditText txtDate=(EditText)findViewById(R.id.input_date);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    com.hcmut.moneymanagement.activity.Transaction.DateDialog dialog=new com.hcmut.moneymanagement.activity.Transaction.DateDialog(view);
                    android.app.FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
    }

}
