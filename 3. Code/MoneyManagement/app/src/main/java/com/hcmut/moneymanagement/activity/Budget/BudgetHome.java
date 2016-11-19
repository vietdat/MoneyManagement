package com.hcmut.moneymanagement.activity.Budget;

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
import com.hcmut.moneymanagement.models.BudgetModel;
import com.hcmut.moneymanagement.objects.Budget;

import java.io.Serializable;

public class BudgetHome extends Fragment implements View.OnClickListener {

    FloatingActionButton addButton;
    private TabHost tabHost;
    private ListView lvRunning;
    private ListView lvFinish;
    private BudgetModel budgetModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budgetModel = new BudgetModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_budget_home, container, false);

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

        addButton = (FloatingActionButton) rootView.findViewById(R.id.btnNew);
        addButton.setOnClickListener(this);

        selecteItemInListViewRunning();
        selecteItemInListViewFinish();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        budgetModel.initBudgetAdapter(getActivity());
        lvRunning.setAdapter(budgetModel.getBudgetsRunningAdapter());
        lvFinish.setAdapter(budgetModel.getBudgetsFinishAdapter());

    }

    @Override
    public void onClick(View view) {
        if(view == addButton) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), BudgetAdd.class);
            getActivity().startActivity(intent);
        }
    }

    private void selecteItemInListViewRunning() {
        lvRunning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Budget budget = budgetModel.budgetsRunning.get(position);
                Intent intent = new Intent(getActivity(), BudgetDetail.class);
                String key = budgetModel.keyRunnings.get(position);
                intent.putExtra("key", key);
                intent.putExtra("budget", (Serializable) budget);
                startActivity(intent);
            }
        });
    }

    private void selecteItemInListViewFinish() {
        lvFinish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Budget budget = budgetModel.budgetsFinish.get(position);
                Intent intent = new Intent(getActivity(), BudgetDetail.class);
                String key = budgetModel.keyFinishs.get(position);
                intent.putExtra("key", key);
                intent.putExtra("budget", (Serializable) budget);
                startActivity(intent);
            }
        });
    }

}
