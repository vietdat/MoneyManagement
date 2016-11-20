package com.hcmut.moneymanagement.activity.IncomeAndExpense;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.DetailIncomeAndExpense.DetailIncomeAndExpense;
import com.hcmut.moneymanagement.models.IncomeCategoryModel;
import com.hcmut.moneymanagement.models.TransactionModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class FragmentIE extends android.support.v4.app.Fragment {
    private ListView lv;
    private ArrayList<ListViewModel> items;
    private MyArrayAdapter transactions;

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
        transactions = new MyArrayAdapter(getActivity(), R.layout.list_row_income, items);
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


                            if (objAmount != null && objDate != null && objCateId != null ){

                                final IncomeCategoryModel incomeCategoryModel = new IncomeCategoryModel();
                                DatabaseReference itemReference = incomeCategoryModel.getReference().child(objCateId.toString());

                                itemReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Object objCateName = dataSnapshot.child(incomeCategoryModel.encrypt("name")).getValue();

                                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        try {
                                            Date itemDate = format.parse(objDate.toString());

                                            Calendar cal = Calendar.getInstance();
                                            cal.setTime(itemDate);
                                            int itemMonth = cal.get(Calendar.MONTH) + 1;

                                            if (itemMonth == month) {
                                                if(type.equals("Income")) {
                                                    items.add(new ListViewModel(objCateName.toString(), "+ " + objAmount.toString()));
                                                }else if( type.equals("Expense") ){
                                                    items.add(new ListViewModel(objCateName.toString(), "- " + objAmount.toString()));
                                                }
                                                transactions.notifyDataSetChanged();
                                            }
                                        } catch (ParseException ex) {
                                            ex.printStackTrace();
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