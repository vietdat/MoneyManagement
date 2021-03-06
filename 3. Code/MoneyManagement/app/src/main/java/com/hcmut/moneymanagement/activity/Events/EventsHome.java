package com.hcmut.moneymanagement.activity.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.ToggleButton;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.EventModel;
import com.hcmut.moneymanagement.objects.Event;

import java.io.Serializable;

public class EventsHome extends Fragment implements View.OnClickListener {

    private TabHost tabHost;
    private ListView lvRunning;
    private ListView lvFinish;
    FloatingActionButton addButton;
    private EventModel eventModel;
    private Event chooseEvent = new Event();
    ToggleButton toggleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventModel = new EventModel();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events_home, container, false);

        // TabHost Init
        tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        //Running
        TabHost.TabSpec tabRunning = tabHost.newTabSpec(getResources().getString(R.string.running));
        tabRunning.setContent(R.id.tabRunning);
        tabRunning.setIndicator(getResources().getString(R.string.running));
        tabHost.addTab(tabRunning);
        lvRunning = (ListView) rootView.findViewById(R.id.lvRunning);

        //Finish
        TabHost.TabSpec tabFinish = tabHost.newTabSpec(getResources().getString(R.string.finish));
        tabFinish.setContent(R.id.tabFinish);
        tabFinish.setIndicator(getResources().getString(R.string.finish));
        tabHost.addTab(tabFinish);
        lvFinish = (ListView) rootView.findViewById(R.id.lvFinish);

        selecteItemInListViewRunning();
        selecteItemInListViewFinish();

        addButton = (FloatingActionButton) rootView.findViewById(R.id.btnNew);
        addButton.setOnClickListener(this);



        return rootView;
    }

    private void statusToggleButton () {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        ToggleButton mSwitchShowSecure;
//        mSwitchShowSecure = (ToggleButton) item.findItem(R.id.status).getActionView().findViewById(R.id.switch_show_protected);
//        mSwitchShowSecure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    //Your code when checked
//
//                } else {
//                    //Your code when unchecked
//                }
//            }
//        });

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //inflater.inflate(R.menu.menu_event, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        eventModel.initEventAdapter(getActivity());
        lvRunning.setAdapter(eventModel.getEventsRunningAdapter());
        lvFinish.setAdapter(eventModel.getEventsFinishAdapter());
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
            intent.setClass(getActivity(), EventsAdd.class);
            getActivity().startActivity(intent);
        }
    }

    private void selecteItemInListViewRunning() {
        lvRunning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event event = eventModel.eventsRunning.get(position);
                Intent intent = new Intent(getActivity(), EventsDetail.class);
                String key = eventModel.keyRunnings.get(position);
                intent.putExtra("key", key);
                intent.putExtra("event", (Serializable) event);
                startActivity(intent);
            }
        });
    }

    private void selecteItemInListViewFinish() {
        lvFinish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Event event = eventModel.eventsFinish.get(position);
                Intent intent = new Intent(getActivity(), EventsDetail.class);
                String key = eventModel.keyFinishs.get(position);
                intent.putExtra("key", key);
                intent.putExtra("event", (Serializable) event);
                startActivity(intent);
            }
        });
    }


}
