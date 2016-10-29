package com.hcmut.moneymanagement.activity.Wallet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.objects.Wallet;

import java.util.ArrayList;

/**
 * Created by Admin on 29-Oct-16.
 */
public class WalletAdapter extends ArrayAdapter<Wallet> {
    Activity context=null;
    ArrayList<Wallet> wallets=null;
    int layoutId;

    public WalletAdapter(Activity context, int layoutId, ArrayList<Wallet>arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.wallets = arr;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }

        Wallet wallet = wallets.get(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView walletName = (TextView) convertView.findViewById(R.id.wallet_name);
        TextView walletAmount = (TextView) convertView.findViewById(R.id.amount);

        //Gán giá trị cho những control đó
        walletName.setText(wallet.getName());
        walletAmount.setText(String.valueOf(wallet.getCurrentAmount()));

        return convertView;

    }


}
