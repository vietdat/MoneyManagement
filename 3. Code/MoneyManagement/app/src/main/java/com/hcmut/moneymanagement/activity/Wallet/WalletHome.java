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
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.objects.Wallet;

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

        WalletModel walletModel = new WalletModel();
        ArrayList<Wallet> wallets = walletModel.getWallets();
        System.out.println("====================");
        System.out.println(wallets.size());
        System.out.println("====================");
        final ArrayList<ListViewModel> arr = new ArrayList<>();
//        for(int i =0 ; i < wallets.getCount(); i++){
//            ListViewModel lvWallet = new ListViewModel("ic_profile", wallets.getItem(i).getName(), wallets.getItem(i).getCurrencyUnit());
//            arr.add(lvWallet);
//        }

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
