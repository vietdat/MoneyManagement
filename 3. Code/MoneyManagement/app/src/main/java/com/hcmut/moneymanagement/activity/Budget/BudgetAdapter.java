package com.hcmut.moneymanagement.activity.Budget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.objects.Budget;

import java.util.ArrayList;

/**
 * Created by Admin on 17-Nov-16.
 */
public class BudgetAdapter extends ArrayAdapter<Budget> {
    Activity context=null;
    ArrayList<Budget> budgets=null;
    int layoutId;

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
        TextView current_amount = (TextView) convertView.findViewById(R.id.currentAmount);
        TextView endDate = (TextView) convertView.findViewById(R.id.endDate);
        TextView category = (TextView) convertView.findViewById(R.id.category);

        //Gán giá trị cho những control đó
        budgetName.setText(budget.getName());
        current_amount.setText(String.valueOf(budget.getAmount()));
        endDate.setText(budget.getEndDate());
        category.setText(budget.getCategory());

        return convertView;
    }
}
