package com.hcmut.moneymanagement.activity.Wallet;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    FloatingActionButton btnEdit;
    FloatingActionButton btnDelete;
    WalletAdapter walletAdapter;

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

    private void showNoItemSelectedDialog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder
                .setTitle("No Item Selected!")
                .setMessage("Please select a category from the list.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        Dialog dialog = alertDialogBuilder.create();
        dialog.show();
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
