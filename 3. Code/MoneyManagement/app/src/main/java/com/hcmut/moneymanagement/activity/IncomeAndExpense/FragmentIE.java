package com.hcmut.moneymanagement.activity.IncomeAndExpense;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.DetailIncomeAndExpense.DetailIncomeAndExpense;

import java.util.ArrayList;


public class FragmentIE extends android.support.v4.app.Fragment {

    ListView lv;
    public FragmentIE() {
        // Required empty public constructor
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

        addItemToListView();
        selecteItemInListView();

        return rootView;
    }

    private void addItemToListView() {
        final ArrayList<ListViewModel> arr = new ArrayList<>();
        ListViewModel income = new ListViewModel("ic_profile", "Income");
        ListViewModel expense = new ListViewModel("ic_profile", "Expense");
        ListViewModel saving = new ListViewModel("ic_profile", "Saving");

        arr.add(income);
        arr.add(expense);
        arr.add(saving);

        MyArrayAdapter mayArr = new MyArrayAdapter(getActivity(), R.layout.list_row_income, arr);

        lv.setAdapter(mayArr);
    }

    private void selecteItemInListView() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DetailIncomeAndExpense.class);
                    getActivity().startActivity(intent);
            }
        });
    }

}