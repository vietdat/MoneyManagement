package com.hcmut.moneymanagement.models;

import com.google.firebase.database.FirebaseDatabase;

public class ExpenseCategoryModel extends CategoryModel {
    public ExpenseCategoryModel(){
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("expenseCategories"));
        reference.keepSynced(true);

        icons.add("icon_bill");
        icons.add("icon_transportation");
        icons.add("icon_shopping_cart");
        icons.add("icon_game");
        icons.add("icon_travel");
        icons.add("icon_health_book");
        icons.add("icon_graduation_cap");
        icons.add("icon_web_shield");
        icons.add("icon_cardboard_box");
    }
}
