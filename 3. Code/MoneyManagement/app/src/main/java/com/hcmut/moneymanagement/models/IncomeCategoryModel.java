package com.hcmut.moneymanagement.models;

import com.google.firebase.database.FirebaseDatabase;

public class IncomeCategoryModel extends CategoryModel{
    public IncomeCategoryModel(){
        // Wallets refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("incomeCategories"));
    }

}
