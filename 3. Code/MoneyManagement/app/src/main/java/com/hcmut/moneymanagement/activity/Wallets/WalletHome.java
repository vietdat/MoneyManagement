package com.hcmut.moneymanagement.activity.Wallets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.WalletModel;
import com.hcmut.moneymanagement.objects.Wallet;

import java.io.Serializable;

public class WalletHome extends Fragment implements View.OnClickListener {
    ListView lv;
    FloatingActionButton addButton;
    private WalletModel  walletModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletModel = new WalletModel();
        Log.w("On Create Wallet home", "nothing");
    }

    @Override
    public void onStart() {
        super.onStart();
        walletModel.initNameAdapter(getContext());
        walletModel.initWalletAdapter(getActivity());
        lv.setAdapter(walletModel.getWalletAdapter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet_home, container, false);
        lv = (ListView) rootView.findViewById(R.id.wallet_list);
        addButton = (FloatingActionButton) rootView.findViewById(R.id.addNewWallet);

        selecteItemInListView();
        addButton.setOnClickListener(this);
        Log.w("Create View", "nothing");
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
            intent.setClass(getActivity(), WalletAdd.class);
            getActivity().startActivity(intent);
        }
    }

    private void selecteItemInListView() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Wallet wallet = walletModel.wallets.get(position);
                Intent intent = new Intent(getActivity(), WalletDetai.class);
                String key = walletModel.keys.get(position);
                intent.putExtra("key", key);
                intent.putExtra("wallet", (Serializable) wallet);
                startActivity(intent);
            }
        });
    }

}
