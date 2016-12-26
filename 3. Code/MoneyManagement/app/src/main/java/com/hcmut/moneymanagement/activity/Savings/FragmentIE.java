package com.hcmut.moneymanagement.activity.Savings;

import android.content.SharedPreferences;
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
import com.hcmut.moneymanagement.models.ChangeCurrency;
import com.hcmut.moneymanagement.models.Model;
import com.hcmut.moneymanagement.models.SavingModel;
import com.hcmut.moneymanagement.models.TransactionModel;

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

    private String key; // Transaction types: {"income", "Expense", "saving"}
    private int month;  // month of the transaction    final ChangeCurrency cc = new ChangeCurrency();
    final ChangeCurrency cc = new ChangeCurrency();

    public FragmentIE(String key, int month) {
        this.key = key;
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
        View rootView =  inflater.inflate(R.layout.fragment_fragment_ie, container, false);
        lv = (ListView)rootView.findViewById(R.id.lv_wallet);

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
                    Object objCateId = transactionSnapshot.child(transactionModel.encrypt("category")).getValue();
                    if (objType != null) {
                        if (objType.toString().equals("Saving")) {
                            final Model categoryModel = new SavingModel();
                            DatabaseReference itemReference;
                            itemReference = categoryModel.getReference().child(objCateId.toString());

                            final Object objAmount = transactionSnapshot.child(transactionModel.encrypt("money")).getValue();
                            final Object objDate = transactionSnapshot.child(transactionModel.encrypt("date")).getValue();

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

                                            SharedPreferences pre= getContext().getSharedPreferences("currency", 0);
                                            String lang = pre.getString("currency", "0");
                                            if (lang.equals("1")) {
                                                items.add(new Transaction(objCateName.toString(), objDate.toString(), cc.changeMoneyUSDToVND(objAmount.toString()) + "đ"));
                                            } else {
                                                items.add(new Transaction(objCateName.toString(), objDate.toString(), "$" + objAmount.toString()));
                                            }
                                            transactions.notifyDataSetChanged();
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
                transactions.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
