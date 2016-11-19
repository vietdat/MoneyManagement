package com.hcmut.moneymanagement.activity.Events;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.objects.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 29-Oct-16.
 */
public class EventsAdapter extends ArrayAdapter<Event> {

    Activity context = null;
    ArrayList<Event> events = null;
    int layoutId;

    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    public EventsAdapter(Activity context, int layoutId, ArrayList<Event>arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.events = arr;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }

        Event event = events.get(position);

        TextView eventName = (TextView) convertView.findViewById(R.id.saving_name);
        TextView left = (TextView) convertView.findViewById(R.id.left);
        TextView spent = (TextView) convertView.findViewById(R.id.spent);

        //Gán giá trị cho những control đó
        eventName.setText(event.getName());
        spent.setText(String.valueOf(event.getSpent()));

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date endDate = df.parse(event.getEndDate());
            Date now = new Date();

            Long leftDate = (endDate.getTime() - now.getTime())
                    / (24 * 3600 * 1000)+1;
            int i = leftDate.intValue();
            if(i < 0) {
                i = 0;
            }
            left.setText(String.valueOf(i));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return convertView;
    }
}
