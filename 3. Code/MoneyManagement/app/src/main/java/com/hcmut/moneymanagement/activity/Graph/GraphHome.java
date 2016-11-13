package com.hcmut.moneymanagement.activity.Graph;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcmut.moneymanagement.R;
import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.PieChartL;

import java.util.ArrayList;
import java.util.List;

public class GraphHome extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_home, container, false);

        PieChartL pie = (PieChartL) rootView.findViewById(R.id.pie_chart);
        List<ChartData> values = new ArrayList <>();
        values.add(new ChartData("Cricket", 1000f));
        values.add(new ChartData("Football", 60000f));
        values.add(new ChartData("Hockey", 3000f));
        values.add(new ChartData("Tenis", 2000f));
        values.add(new ChartData("Rugby",400000f));
        values.add(new ChartData("Polo",1000f));
        pie.setData(values);

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
