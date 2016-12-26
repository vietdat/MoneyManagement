package com.hcmut.moneymanagement.activity.Transaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.activity.CustomListView.Model.Transaction;
import com.hcmut.moneymanagement.models.TransactionModel;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.models.CategoryModel;
import com.hcmut.moneymanagement.objects.Category;

public class TransactionDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private String key;

    private EditText typeOfTransaction;
    private EditText date;
    private EditText amount;
    private EditText wallet;
    private EditText category;
    private EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key");

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        typeOfTransaction = (EditText) findViewById(R.id.typeOfTransaction);
        date = (EditText) findViewById(R.id.input_date);
        amount = (EditText) findViewById(R.id.input_amount);
        wallet = (EditText) findViewById(R.id.wallet);
        category = (EditText) findViewById(R.id.category);
        description = (EditText) findViewById(R.id.desciption);

        final TransactionModel transactionModel = new TransactionModel();
        DatabaseReference reference = transactionModel.getReference().child(key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object objTypeOfTransaction = dataSnapshot.child(transactionModel.encrypt("type")).getValue();
                Object objAmount = dataSnapshot.child(transactionModel.encrypt("money")).getValue();
                Object objDate = dataSnapshot.child(transactionModel.encrypt("date")).getValue();
                Object objWallet = dataSnapshot.child(transactionModel.encrypt("wallet")).getValue();
                Object objCategory = dataSnapshot.child(transactionModel.encrypt("category")).getValue();
                Object objDescription = dataSnapshot.child(transactionModel.encrypt("description")).getValue();

                typeOfTransaction.setText(objTypeOfTransaction.toString());
                amount.setText(objAmount.toString());
                date.setText(objDate.toString());
                String wallet_key = objWallet.toString();
                String category_key = objCategory.toString();
                description.setText(objDescription.toString());

                final WalletModel walletModel = new WalletModel();
                DatabaseReference walletReference = walletModel.getReference().child(wallet_key);
                walletReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object name = dataSnapshot.child(walletModel.encrypt("name")).getValue();
                        wallet.setText(name.toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final CategoryModel categoryModel = new CategoryModel();
                DatabaseReference categoryReference = categoryModel.getReference().child(category_key);
                walletReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object name = dataSnapshot.child(categoryModel.encrypt("name")).getValue();
                        category.setText(name.toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Load data when know key


}
