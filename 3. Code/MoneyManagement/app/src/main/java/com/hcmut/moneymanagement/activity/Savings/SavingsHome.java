package com.hcmut.moneymanagement.activity.Savings;

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
import android.widget.TabHost;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.SavingModel;
import com.hcmut.moneymanagement.objects.Saving;

import java.io.Serializable;

public class SavingsHome extends Fragment implements View.OnClickListener {

    private TabHost tabHost;
    private ListView lvRunning;
    private ListView lvFinish;
    FloatingActionButton addButton;
    FloatingActionButton editButton;
    FloatingActionButton deleteButton;
    private SavingModel savingModel;
    private Saving chooseSaving = new Saving();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savingModel = new SavingModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_savings_home, container, false);

        // TabHost Init
        tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        //Running
        TabHost.TabSpec tabRunning = tabHost.newTabSpec("Running");
        tabRunning.setContent(R.id.tabRunning);
        tabRunning.setIndicator("Running");
        tabHost.addTab(tabRunning);
        lvRunning = (ListView) rootView.findViewById(R.id.lvRunning);

        //Finish
        TabHost.TabSpec tabFinish = tabHost.newTabSpec("Finish");
        tabFinish.setContent(R.id.tabFinish);
        tabFinish.setIndicator("Finish");
        tabHost.addTab(tabFinish);
        lvFinish = (ListView) rootView.findViewById(R.id.lvFinish);

        selecteItemInListViewRunning();
        selecteItemInListViewFinish();

        addButton = (FloatingActionButton) rootView.findViewById(R.id.btnNew);
        addButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        savingModel.initSavingAdapter(getActivity());
        lvRunning.setAdapter(savingModel.getSavingsRunningAdapter());
        lvFinish.setAdapter(savingModel.getSavingsFinishAdapter());
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
            intent.setClass(getActivity(), SavingsAdd.class);
            getActivity().startActivity(intent);
        }
    }

    private void selecteItemInListViewRunning() {
        lvRunning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Saving saving = savingModel.savingsRunning.get(position);
                Intent intent = new Intent(getActivity(), SavingDetail.class);
                String key = savingModel.keyRunnings.get(position);
                intent.putExtra("key", key);
                intent.putExtra("saving", (Serializable) saving);
                startActivity(intent);
            }
        });
    }

    private void selecteItemInListViewFinish() {
        lvFinish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Saving saving = savingModel.savingsFinish.get(position);
                Intent intent = new Intent(getActivity(), SavingDetail.class);
                String key = savingModel.keyFinishs.get(position);
                intent.putExtra("key", key);
                intent.putExtra("saving", (Serializable) saving);
                startActivity(intent);
            }
        });
    }


}
