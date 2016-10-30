package com.hcmut.moneymanagement.activity.Wallet;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.WalletModel;


public class WalletHome extends Fragment implements View.OnClickListener {
    ListView lv;
    FloatingActionButton addButton;
    private int selectedPosition;
    private WalletModel  walletModel;
    private FloatingActionButton btnEdit;
    private FloatingActionButton btnDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletModel = new WalletModel();

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


        // init selected posstion
        selectedPosition = -1;

        selecteItemInListView();

        addButton = (FloatingActionButton) rootView.findViewById(R.id.addNewWallet);
        addButton.setOnClickListener(this);
        btnEdit = (FloatingActionButton) rootView.findViewById(R.id.btnEdit);
        //btnEdit.setOnClickListener(onEditClickListener);
        btnDelete = (FloatingActionButton) rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(onDeleteClickListener);
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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               selectedPosition = position;
            }
        });
    }

    // On Delete Button Click
    private View.OnClickListener onDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(selectedPosition != -1){
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder
                        .setTitle("Deletion Confirm")
                        .setMessage("Are you sure you want to delete this wallet?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String key = walletModel.keys.get(selectedPosition);
                                System.err.println("key " + key);
                                System.err.println("Position " + selectedPosition);

                                walletModel.remove(key);

                                selectedPosition = -1;
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                Dialog dialog = alertDialogBuilder.create();
                dialog.show();
            }else{
                showNoItemSelectedDialog();
            }
        }
    };

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

}
