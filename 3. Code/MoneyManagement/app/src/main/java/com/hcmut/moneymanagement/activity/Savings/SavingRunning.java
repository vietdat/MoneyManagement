package com.hcmut.moneymanagement.activity.Savings;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcmut.moneymanagement.R;

import java.util.ArrayList;


public class SavingRunning extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    public SavingRunning() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_saving_running, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SavingsAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }



    private ArrayList<Saving> getDataSet() {
        ArrayList results = new ArrayList<Saving>();
        for (int index = 0; index < 20; index++) {
            Saving obj = new Saving("Tien to chuc dam cuoi", "20000000", "200");
            results.add(index, obj);
        }
        return results;
    }

}