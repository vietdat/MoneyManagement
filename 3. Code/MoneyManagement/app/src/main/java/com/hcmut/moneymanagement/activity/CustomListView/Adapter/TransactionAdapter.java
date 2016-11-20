package com.hcmut.moneymanagement.activity.CustomListView.Adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.CustomListView.Model.Transaction;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction>{
    Activity context = null;
    int layoutId;
    ArrayList<Transaction> arr = null;

    //Contructor này dùng để lấy về những giá trị được truyền vào từ Activity
    public TransactionAdapter(Activity context, int layoutId, ArrayList<Transaction> list){
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.arr = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }

        Transaction item = arr.get(position);

        TextView category = (TextView)convertView.findViewById(R.id.tvCategory);
        TextView date = (TextView)convertView.findViewById(R.id.tvDate);
        TextView amount = (TextView)convertView.findViewById(R.id.tvAmount);

        //Gán giá trị cho những control đó
        category.setText(item.getCategory());
        date.setText(item.getDate());
        amount.setText(item.getAmount());

        return convertView;
    }

}
