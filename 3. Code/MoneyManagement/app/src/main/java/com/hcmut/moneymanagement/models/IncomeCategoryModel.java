package com.hcmut.moneymanagement.models;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.R;

public class IncomeCategoryModel extends CategoryModel{
    public IncomeCategoryModel(){
        // Wallets refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("incomeCategories"));
        reference.keepSynced(true);

        icons.add("icon_money");
        icons.add("icon_gift");
        icons.add("icon_percentage");
        icons.add("icon_sale");
        icons.add("icon_cardboard_box");
    }



}
