package com.hcmut.moneymanagement.activity.IncomeAndExpense;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;

import java.util.ArrayList;


public class FragmentIE extends android.support.v4.app.Fragment {

    ListView lv;
    public FragmentIE() {
        // Required empty public constructor
    }

    //create listview
    public void createListview() {

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

}