package com.hcmut.moneymanagement.activity.Savings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.objects.Saving;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 29-Oct-16.
 */
public class SavingAdapter extends ArrayAdapter<Saving> {

        Activity context=null;
        ArrayList<Saving> savings=null;
        int layoutId;

        @Override
        public Saving getItem(int position) {
            return savings.get(position);
        }

        public SavingAdapter(Activity context, int layoutId, ArrayList<Saving>arr){
            super(context, layoutId, arr);
            this.context=context;
            this.layoutId=layoutId;
            this.savings = arr;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView==null){
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(layoutId, null);
            }

            Saving saving = savings.get(position);

            TextView savingName = (TextView) convertView.findViewById(R.id.saving_name);
            TextView goal = (TextView) convertView.findViewById(R.id.goal);
            TextView left = (TextView) convertView.findViewById(R.id.left);
            TextView currentAmount = (TextView) convertView.findViewById(R.id.current);

            //Gán giá trị cho những control đó
            savingName.setText(saving.getName());
            goal.setText(String.valueOf(saving.getGoal()));


            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date endDate = df.parse(saving.getEndDate());
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

            currentAmount.setText(saving.getCurrent_amount());

            return convertView;
        }
}
