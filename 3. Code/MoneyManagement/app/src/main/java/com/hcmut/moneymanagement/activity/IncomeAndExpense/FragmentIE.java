package com.hcmut.moneymanagement.activity.IncomeAndExpense;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Adapter.TransactionAdapter;
import com.hcmut.moneymanagement.activity.CustomListView.Model.Transaction;
import com.hcmut.moneymanagement.models.ExpenseCategoryModel;
import com.hcmut.moneymanagement.models.IncomeCategoryModel;
import com.hcmut.moneymanagement.models.Model;
import com.hcmut.moneymanagement.models.SavingModel;
import com.hcmut.moneymanagement.models.TransactionModel;
import com.hcmut.moneymanagement.models.WalletModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class FragmentIE extends android.support.v4.app.Fragment {
    private ListView lv;
    private ArrayList<Transaction> items;
    private TransactionAdapter transactions;

    private String type; // Transaction types: {"Income", "Expense", "Saving"}
    private int month;  // month of the transaction

    public FragmentIE(String type, int month) {
        this.type = type;
        this.month = month;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_income_expense, container, false);
        lv = (ListView)rootView.findViewById(R.id.lv_income_expense);

        items = new ArrayList<>();
        transactions = new TransactionAdapter(getActivity(), R.layout.transaction_item, items);
        lv.setAdapter(transactions);

        addItemToListView();

        return rootView;
    }

    private void addItemToListView() {
        final TransactionModel transactionModel = new TransactionModel();
        transactionModel.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()) {
                    Object objType = transactionSnapshot.child(transactionModel.encrypt("type")).getValue();
                    if (objType != null) {
                        if (objType.toString().equals(type)) {
                            final Object objAmount = transactionSnapshot.child(transactionModel.encrypt("money")).getValue();
                            final Object objDate = transactionSnapshot.child(transactionModel.encrypt("date")).getValue();
                            final Object objCateId = transactionSnapshot.child(transactionModel.encrypt("category")).getValue();
                            final Object objWalletId = transactionSnapshot.child(transactionModel.encrypt("wallet")).getValue();


                            if (objAmount != null && objDate != null && objCateId != null && objWalletId != null){
                                final Model categoryModel,walletModel;
                                walletModel = new WalletModel();

                                DatabaseReference itemReference;
                                if( type.equals("Income") ){
                                    categoryModel = new IncomeCategoryModel();
                                }else if( type.equals("Expense") ){
                                    categoryModel = new ExpenseCategoryModel();
                                }else if( type.equals("Saving") ){
                                    categoryModel = new SavingModel();
                                } else {
                                    categoryModel = new WalletModel();
                                }

                                itemReference = categoryModel.getReference().child(objCateId.toString());

                                itemReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final Object objCateName = dataSnapshot.child(categoryModel.encrypt("name")).getValue();
                                        if( objCateName != null) {
                                            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                            try {
                                                Date itemDate = format.parse(objDate.toString());

                                                Calendar cal = Calendar.getInstance();
                                                cal.setTime(itemDate);
                                                int itemMonth = cal.get(Calendar.MONTH) + 1;

                                                if (itemMonth == month) {
                                                    if (type.equals("Income")) {
                                                        items.add(new Transaction(objCateName.toString(), objDate.toString(), "+ " + objAmount.toString()));
                                                    } else if (type.equals("Expense")) {
                                                        items.add(new Transaction(objCateName.toString(), objDate.toString(), "- " + objAmount.toString()));
                                                    }else if(type.equals("Saving")){
                                                        items.add(new Transaction(objCateName.toString(), objDate.toString(), "+" + objAmount.toString()));
                                                    } else {
                                                        DatabaseReference reference = walletModel.getReference().child(objWalletId.toString());
                                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                             @Override
                                                             public void onDataChange(DataSnapshot dataSnapshot) {
                                                                 Object objWalletName = dataSnapshot.child(categoryModel.encrypt("name")).getValue();

                                                                 items.add(new Transaction("From " + objCateName.toString() + " to " + objWalletName.toString(), objDate.toString(), objAmount.toString()));
                                                                 transactions.notifyDataSetChanged();
                                                             }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                    transactions.notifyDataSetChanged();
                                                }
                                            } catch (ParseException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                }
                transactions.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}