package com.hcmut.moneymanagement.activity.Transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Adapter.MyArrayAdapter;
import com.hcmut.moneymanagement.activity.CustomListView.Model.ListViewModel;
import com.hcmut.moneymanagement.activity.IncomeAndExpense.IncomeAndExpenseHome;

import java.util.ArrayList;

public class TransactionHome extends Fragment implements View.OnClickListener {

    ListView lv;
    FloatingActionButton addButton;

    public TransactionHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction__home, container, false);

        lv = (ListView) rootView.findViewById(R.id.transaction_home_list);
        addButton = (FloatingActionButton) rootView.findViewById(R.id.addNewTransaction);

        addItemToListView();
        selecteItemInListView();

        addButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    //add item to listview
    private void addItemToListView() {
        final ArrayList<ListViewModel> arr = new ArrayList<>();
        ListViewModel income = new ListViewModel("ic_profile", "Income", "Thong tin ve thu nhap cua ban");
        ListViewModel expense = new ListViewModel("ic_profile", "Expense", "Thong tin ve chi tieu cua ban");
        ListViewModel saving = new ListViewModel("ic_profile", "Saving", "Thong tin ve vi tien cua ban");

        arr.add(income);
        arr.add(expense);
        arr.add(saving);

        MyArrayAdapter mayArr = new MyArrayAdapter(getActivity(), R.layout.list_row, arr);

        lv.setAdapter(mayArr);
    }

    private void selecteItemInListView() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if(i == 0) {
                   Intent intent = new Intent();
                   intent.putExtra("type", "Income");
                   intent.setClass(getActivity(), IncomeAndExpenseHome.class);
                   getActivity().startActivity(intent);
               } else if(i == 1) {
                   Intent intent = new Intent();
                   intent.putExtra("type", "Expense");
                   intent.setClass(getActivity(), IncomeAndExpenseHome.class);
                   getActivity().startActivity(intent);
               }

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if(view == addButton) {

            Intent intent = new Intent();
            intent.setClass(getActivity(), com.hcmut.moneymanagement.activity.Transaction.AddTransactionActivity.class);

            getActivity().startActivity(intent);
        }


    }
}