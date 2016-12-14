package com.hcmut.moneymanagement.activity.Category;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.ExpenseCategoryModel;
import com.hcmut.moneymanagement.models.IncomeCategoryModel;
import com.hcmut.moneymanagement.models.WalletCategoryModel;
import com.hcmut.moneymanagement.objects.Category;

import java.util.HashMap;
import java.util.Map;

public class CategoryHome extends Fragment{
    private TabHost tabHost;

    private ListView lvIncome;
    private ListView lvExpense;
    private ListView lvWallet;
    private IncomeCategoryModel incomeCategoryModel;
    private ExpenseCategoryModel expenseCategoryModel;
    private WalletCategoryModel walletCategoryModel;
    private FloatingActionButton btnEdit;
    private FloatingActionButton btnNew;
    private FloatingActionButton btnDelete;
    private int selectedPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        // TabHost Init
        tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabIncome = tabHost.newTabSpec("income");
        tabIncome.setContent(R.id.tabIncome);
        tabIncome.setIndicator(getResources().getString(R.string.income));
        tabHost.addTab(tabIncome);

        TabHost.TabSpec tabExpense = tabHost.newTabSpec("Expense");
        tabExpense.setContent(R.id.tabExpense);
        tabExpense.setIndicator(getResources().getString(R.string.expense));
        tabHost.addTab(tabExpense);

        TabHost.TabSpec tabWallet = tabHost.newTabSpec("Wallet");
        tabWallet.setContent(R.id.tabWallet);
        tabWallet.setIndicator(getResources().getString(R.string.wallet));
        tabHost.addTab(tabWallet);

        // Init Models
        incomeCategoryModel = new IncomeCategoryModel();
        incomeCategoryModel.initListViewAdapter(getActivity());
        expenseCategoryModel = new ExpenseCategoryModel();
        expenseCategoryModel.initListViewAdapter(getActivity());
        walletCategoryModel = new WalletCategoryModel();
        walletCategoryModel.initListViewAdapter(getActivity());

        // init selected posstion
        selectedPosition = -1;

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                selectedPosition = -1;
            }
        });

        // income category Listview init
        lvIncome = (ListView) rootView.findViewById(R.id.lvIncomeCategories);
        lvIncome.setAdapter(incomeCategoryModel.getNameAdapter());
        lvIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
            }
        });

        lvExpense = (ListView) rootView.findViewById(R.id.lvExpenseCategories);
        lvExpense.setAdapter(expenseCategoryModel.getNameAdapter());
        lvExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
            }
        });

        lvWallet = (ListView) rootView.findViewById(R.id.lvWalletCategories);
        lvWallet.setAdapter(walletCategoryModel.getNameAdapter());
        lvWallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
            }
        });

        // Action buttons init
        btnNew = (FloatingActionButton) rootView.findViewById(R.id.btnNew);
        btnNew.setOnClickListener(onNewClickListener);
        btnEdit = (FloatingActionButton) rootView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(onEditClickListener);
        btnDelete = (FloatingActionButton) rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(onDeleteClickListener);

        return rootView;

    }

    // On New Button Click
    private View.OnClickListener onNewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final EditText input = new EditText(getActivity());
            input.requestFocus();

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder
                    .setTitle(getResources().getString(R.string.add_new))
                    .setMessage(getResources().getString(R.string.add_new_category))
                    .setView(input)
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String value = input.getText().toString().trim();
                            if( !value.equals("") ) {
                                Category category = new Category(value);
                                if(tabHost.getCurrentTab() == 0){
                                    incomeCategoryModel.add(category);
                                }else if(tabHost.getCurrentTab() == 1){
                                    expenseCategoryModel.add(category);
                                }else{
                                    walletCategoryModel.add(category);
                                }

                                dialog.dismiss();
                            }
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            Dialog dialog = alertDialogBuilder.create();
            dialog.show();
        }
    };

    // On Edit button click
    private View.OnClickListener onEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(selectedPosition != -1){
                final EditText input = new EditText(getActivity());
                if(tabHost.getCurrentTab() == 0) {
                    input.setText(incomeCategoryModel.getNameAdapter().getItem(selectedPosition));
                }else if(tabHost.getCurrentTab() == 1){
                    input.setText(expenseCategoryModel.getNameAdapter().getItem(selectedPosition));
                }else{
                    input.setText(walletCategoryModel.getNameAdapter().getItem(selectedPosition));
                }

                input.requestFocus();

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder
                        .setTitle(getResources().getString(R.string.edit))
                        .setMessage(getResources().getString(R.string.add_new_category))
                        .setView(input)
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = input.getText().toString().trim();
                                if( !value.equals("") ) {
                                    Map<String, Object> updateData = new HashMap<String, Object>();
                                    updateData.put(incomeCategoryModel.encrypt("name"), incomeCategoryModel.encrypt(value));

                                    if(tabHost.getCurrentTab() == 0) {
                                        String key = incomeCategoryModel.keys.get(selectedPosition);
                                        incomeCategoryModel.update(key, updateData);
                                    }else if(tabHost.getCurrentTab() == 1){
                                        String key = expenseCategoryModel.keys.get(selectedPosition);
                                        expenseCategoryModel.update(key, updateData);
                                    }else{
                                        String key = walletCategoryModel.keys.get(selectedPosition);
                                        walletCategoryModel.update(key, updateData);
                                    }

                                    dialog.dismiss();
                                }
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
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


    // On Delete Button Click
    private View.OnClickListener onDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(selectedPosition != -1){
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder
                        .setTitle(getResources().getString(R.string.delete_confirm))
                        .setMessage(getResources().getString(R.string.sure_delete_category))
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(tabHost.getCurrentTab() == 0){
                                    String key = incomeCategoryModel.keys.get(selectedPosition);
                                    incomeCategoryModel.remove(key);
                                }else if(tabHost.getCurrentTab() == 1){
                                    String key = expenseCategoryModel.keys.get(selectedPosition);
                                    expenseCategoryModel.remove(key);
                                }else{
                                    String key = walletCategoryModel.keys.get(selectedPosition);
                                    walletCategoryModel.remove(key);
                                }

                                selectedPosition = -1;
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
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
                .setTitle(getResources().getString(R.string.no_item))
                .setMessage(getResources().getString(R.string.select_item))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
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
}
