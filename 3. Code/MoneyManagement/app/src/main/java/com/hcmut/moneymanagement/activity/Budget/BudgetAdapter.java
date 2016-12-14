package com.hcmut.moneymanagement.activity.Budget;

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
import com.hcmut.moneymanagement.objects.Budget;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Admin on 17-Nov-16.
 */
public class BudgetAdapter extends ArrayAdapter<Budget> {
    Activity context=null;
    ArrayList<Budget> budgets=null;
    int layoutId;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public Budget getItem(int position) {
        return budgets.get(position);
    }

    public BudgetAdapter(Activity context, int layoutId, ArrayList<Budget>arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.budgets = arr;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }

        Budget budget = budgets.get(position);



        TextView budgetName = (TextView) convertView.findViewById(R.id.budgetName);
        TextView endDate = (TextView) convertView.findViewById(R.id.endDate);
        TextView category = (TextView) convertView.findViewById(R.id.category);
        ProgressBar androidProgressBar = (ProgressBar) convertView.findViewById(R.id.horizontal_progress_bar);
        TextView endAmount = (TextView) convertView.findViewById(R.id.endAmount);

        int progressStatusCounter = Integer.parseInt(budget.getCurrentAmount());
        androidProgressBar.setMax(Integer.valueOf(budget.getAmount()));
        androidProgressBar.setProgress(progressStatusCounter);

        //Gán giá trị cho những control đó
        budgetName.setText(budget.getName());
        endDate.setText(budget.getEndDate());
        category.setText(budget.getCategory());

        SharedPreferences pre= context.getSharedPreferences("currency", 0);
        String lang = pre.getString("currency", "0");
        String currency;
        ChangeCurrency cc = new ChangeCurrency();
        if (lang.equals("1")) {
            currency = "đ"; // your language
            endAmount.setText(cc.changeMoneyUSDToVND(budget.getAmount()) +" " + currency);
        } else {
            currency = "$"; // your language
            endAmount.setText(currency + budget.getAmount());
        }
        return convertView;
    }
}
