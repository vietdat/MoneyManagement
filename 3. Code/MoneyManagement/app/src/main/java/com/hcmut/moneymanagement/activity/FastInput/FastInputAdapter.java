package com.hcmut.moneymanagement.activity.FastInput;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.models.ChangeCurrency;
import com.hcmut.moneymanagement.objects.FastInput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Admin on 29-Oct-16.
 */
public class FastInputAdapter extends ArrayAdapter<FastInput> {

        Activity context=null;
        ArrayList<FastInput> fastInputs = null;
        int layoutId;

        @Override
        public FastInput getItem(int position) {
            return fastInputs.get(position);
        }

        public FastInputAdapter(Activity context, int layoutId, ArrayList<FastInput>arr){
            super(context, layoutId, arr);
            this.context=context;
            this.layoutId=layoutId;
            this.fastInputs = arr;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView==null){
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(layoutId, null);
            }

            FastInput fastInput = fastInputs.get(position);

            TextView fastInputName = (TextView) convertView.findViewById(R.id.fast_input_name);
            TextView category = (TextView) convertView.findViewById(R.id.category);
            TextView moneyAmount = (TextView) convertView.findViewById(R.id.moneyAmount);
//            TextView currentAmount = (TextView) convertView.findViewById(R.id.current);

            //Gán giá trị cho những control đó
            fastInputName.setText(fastInput.getKey());
            category.setText(fastInput.getCategory());

            SharedPreferences pre= context.getSharedPreferences("currency", 0);
            String lang = pre.getString("currency", "0");
            String currency;
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            ChangeCurrency cc = new ChangeCurrency();

            if (lang.equals("1")) {
                currency = "đ";
                moneyAmount.setText(cc.changeMoneyUSDToVND(fastInput.getMoney()) + currency);
            } else {
                currency = "$";
                moneyAmount.setText(currency + fastInput.getMoney());
            }

            return convertView;
        }
}
