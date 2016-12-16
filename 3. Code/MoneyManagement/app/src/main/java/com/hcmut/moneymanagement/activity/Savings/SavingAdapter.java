package com.hcmut.moneymanagement.activity.Savings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.ChangeCurrency;
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
            ProgressBar androidProgressBar = (ProgressBar) convertView.findViewById(R.id.horizontal_progress_bar);

            int progressStatusCounter = Integer.parseInt(saving.getCurrent_amount());

            androidProgressBar.setMax(Integer.valueOf(saving.getGoal()));
            androidProgressBar.setProgress(progressStatusCounter);
            TextView goal = (TextView) convertView.findViewById(R.id.endAmount);
            TextView start = (TextView) convertView.findViewById(R.id.startAmount);
//            TextView currentAmount = (TextView) convertView.findViewById(R.id.current);

            //Gán giá trị cho những control đó
            savingName.setText(saving.getName());

            SharedPreferences pre= context.getSharedPreferences("currency", 0);
            String lang = pre.getString("currency", "0");
            String currency;
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
//                left.setText(String.valueOf(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ChangeCurrency cc = new ChangeCurrency();

            if (lang.equals("1")) {
                currency = "đ";
                goal.setText(cc.changeMoneyUSDToVND(saving.getGoal()) + currency);
                start.setText(cc.changeMoneyUSDToVND(saving.getStartAmount()) + currency);
            } else {
                currency = "$";
                goal.setText(currency + saving.getGoal());
                start.setText(currency + saving.getStartAmount());
            }

//            currentAmount.setText(saving.getCurrent_amount());

            return convertView;
        }
}
