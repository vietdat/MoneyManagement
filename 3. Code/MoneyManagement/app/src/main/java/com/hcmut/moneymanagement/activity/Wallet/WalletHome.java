package com.hcmut.moneymanagement.activity.Wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Adapter.MyArrayAdapter;
import com.hcmut.moneymanagement.activity.CustomListView.Model.ListViewModel;

import java.util.ArrayList;


public class WalletHome extends Fragment implements View.OnClickListener {
    ListView lv;
    FloatingActionButton addButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet_home, container, false);

        lv = (ListView) rootView.findViewById(R.id.wallet_list);
        addButton = (FloatingActionButton) rootView.findViewById(R.id.addNewWallet);

        final ArrayList<ListViewModel> arr = new ArrayList<>();
        ListViewModel income = new ListViewModel("ic_profile", "vi so 1", "vi so 1");
        ListViewModel expense = new ListViewModel("ic_profile", "vi so 2", "vi so 2");

        arr.add(income);
        arr.add(expense);

        MyArrayAdapter mayArr = new MyArrayAdapter(getActivity(), R.layout.list_row, arr);

        lv.setAdapter(mayArr);


        addButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
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
            intent.setClass(getActivity(), AddNewWalletActivity.class);
            getActivity().startActivity(intent);
        }
    }

}
