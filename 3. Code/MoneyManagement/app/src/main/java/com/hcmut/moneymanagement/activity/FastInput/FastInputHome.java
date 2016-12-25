package com.hcmut.moneymanagement.activity.FastInput;

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

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.FastInputModel;
import com.hcmut.moneymanagement.objects.FastInput;

import java.io.Serializable;

public class FastInputHome  extends Fragment implements View.OnClickListener {

    private FastInputModel fastInputModel;
    FloatingActionButton addButton;
    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fastInputModel =  new FastInputModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fast_input_home, container, false);

        lv = (ListView) rootView.findViewById(R.id.lv);
        addButton = (FloatingActionButton) rootView.findViewById(R.id.addNewFastInput);
        addButton.setOnClickListener(this);
        selecteItemInListView();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        fastInputModel.initFastInputAdapter(getActivity());
        lv.setAdapter(fastInputModel.getFastInputAdapter());
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
            intent.setClass(getActivity(), FastInputAdd.class);
            getActivity().startActivity(intent);
        }
    }

    private void selecteItemInListView() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FastInput fastInput = fastInputModel.fastInputs.get(position);
                Intent intent = new Intent(getActivity(), FastInputDetail.class);
                String key = fastInputModel.key.get(position);
                intent.putExtra("key", key);
                intent.putExtra("fastinput", (Serializable) fastInput);
                startActivity(intent);
            }
        });
    }
}
