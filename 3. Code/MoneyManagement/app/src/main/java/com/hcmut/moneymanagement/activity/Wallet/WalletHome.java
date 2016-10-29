package com.hcmut.moneymanagement.activity.Wallet;

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
import android.widget.TextView;
import android.widget.Toast;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.WalletModel;


public class WalletHome extends Fragment implements View.OnClickListener {
    ListView lv;
    FloatingActionButton addButton;
    private WalletModel walletModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletModel = new WalletModel();
        walletModel.initWalletAdapter(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet_home, container, false);

        lv = (ListView) rootView.findViewById(R.id.wallet_list);
        addButton = (FloatingActionButton) rootView.findViewById(R.id.addNewWallet);
        WalletAdapter wallets = walletModel.getWalletAdapter();
        lv.setAdapter(wallets);

        selecteItemInListView();
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

    private void selecteItemInListView() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected =((TextView)view.findViewById(R.id.wallet_name)).getText().toString();

                Toast toast=Toast.makeText(getContext(), selected, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}
