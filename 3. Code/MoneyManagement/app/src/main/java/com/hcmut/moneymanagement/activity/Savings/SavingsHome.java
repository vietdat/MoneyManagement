package com.hcmut.moneymanagement.activity.Savings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.objects.Saving;

import java.util.ArrayList;


public class SavingsHome extends Fragment implements View.OnClickListener {
    private TabHost tabHost;
    private ListView lvRunning;
    private ListView lvFinish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_savings_home, container, false);
        // TabHost Init
        tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabRunning = tabHost.newTabSpec("Running");
        tabRunning.setContent(R.id.tabRunning);
        tabRunning.setIndicator("Running");
        tabHost.addTab(tabRunning);

        TabHost.TabSpec tabFinish = tabHost.newTabSpec("Finish");
        tabFinish.setContent(R.id.tabFinish);
        tabFinish.setIndicator("Finish");
        tabHost.addTab(tabFinish);

        Saving saving = new Saving("Tien dam cuoi", "3000000", "0", "365 days");
        ArrayList<Saving> arr = new ArrayList<Saving>();
        arr.add(saving);
        arr.add(saving);
        arr.add(saving);
        //add adapter
        SavingAdapter savingAdapter = new SavingAdapter(getActivity(), R.layout.saving_item ,arr);

        lvRunning = (ListView) rootView.findViewById(R.id.lvRunning);
        lvRunning.setAdapter(savingAdapter);
        lvRunning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lvFinish = (ListView) rootView.findViewById(R.id.lvFinish);
        lvFinish.setAdapter(savingAdapter);
        lvFinish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


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


}
