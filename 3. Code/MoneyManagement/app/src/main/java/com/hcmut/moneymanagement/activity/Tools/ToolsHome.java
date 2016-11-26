package com.hcmut.moneymanagement.activity.Tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Model.ListViewModel;
import com.hcmut.moneymanagement.activity.Tools.Exchanger.ExchangeHome;
import com.hcmut.moneymanagement.activity.Tools.Settings.SettingsHome;
import com.hcmut.moneymanagement.activity.Tools.Tips.TipsHome;

import java.util.ArrayList;

public class ToolsHome extends Fragment implements View.OnClickListener {
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //add item to listview
    private void addItemToListView() {
        final ArrayList<ListViewModel> arr = new ArrayList<>();
        ListViewModel tips = new ListViewModel("ic_profile", getContext().getString(R.string.tips), "Thong tin ve thu nhap cua ban");
        ListViewModel atmFinder = new ListViewModel("ic_profile", "ATM Finder", "Thong tin ve chi tieu cua ban");
        ListViewModel bankFinder = new ListViewModel("ic_profile", "Bank Finder", "Thong tin ve vi tien cua ban");
        ListViewModel exchanger = new ListViewModel("ic_profile", "Exchanger", "Thong tin ve vi tien cua ban");
        ListViewModel settings = new ListViewModel("ic_profile", "Settings", "Thong tin ve vi tien cua ban");
        ListViewModel aboutUs = new ListViewModel("ic_profile", "About us", "Thong tin ve vi tien cua ban");

        arr.add(tips);
        arr.add(atmFinder);
        arr.add(bankFinder);
        arr.add(exchanger);
        arr.add(settings);
        arr.add(aboutUs);

        ToolsAdapter mayArr = new ToolsAdapter(getActivity(), R.layout.tool_item, arr);

        lv.setAdapter(mayArr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tools_home, container, false);
        lv = (ListView) rootView.findViewById(R.id.lvTools);
        addItemToListView();
        selecteItemInListView();

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

    }

    private void selecteItemInListView() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0) {
                    Intent intent = new Intent(getActivity(), TipsHome.class);
                    startActivity(intent);
                }
                else if (position == 1) {
                    Uri gmmIntentUri = Uri.parse("geo:10.8435,-106.75820?q=" + Uri.encode("atms"));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
                else if(position == 2) {
                    final EditText input = new EditText(getActivity());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Enter bank name");
                    builder.setView(input);

                    // Add the buttons to Dialogs
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            Uri gmmIntentUri = Uri.parse("geo:10.8435,-106.75820?q=" + Uri.encode(input.getText().toString()));
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    // Create the AlertDialog
                    Dialog dialog = builder.create();

                    dialog.show();
                }
                else if (position == 3) {
                    Intent intent = new Intent(getActivity(), ExchangeHome.class);
                    startActivity(intent);
                }
                else if (position == 4) {
                    Intent intent = new Intent(getActivity(), SettingsHome.class);
                    startActivity(intent);
                }


            }
        });
    }

}
