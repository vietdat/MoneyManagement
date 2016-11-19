package com.hcmut.moneymanagement.models;

import com.google.firebase.database.FirebaseDatabase;

public class ExpenseCategoryModel extends CategoryModel {
    public ExpenseCategoryModel(){
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("expenseCategories"));
        reference.keepSynced(true);
    }
}
