package com.hcmut.moneymanagement.models;

import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.objects.Category;
import com.hcmut.moneymanagement.objects.Wallet;


public class UserModel extends Model {
    public UserModel(){
        // Database reference of current user
        reference = FirebaseDatabase.getInstance().getReference().child(uidEncrypted);
    }

    public void initUserData(){
        Wallet cash = new Wallet("Cash", "Tiền mặt", 0);
        Wallet bank  = new Wallet("Bank Account", "Tài khoản ngân hàng", 0);
        WalletModel walletModel = new WalletModel();
        walletModel.add(cash);
        walletModel.add(bank);

        IncomeCategoryModel incomeCategoryModel = new IncomeCategoryModel();
        incomeCategoryModel.add(new Category("Tiền lương"));
        incomeCategoryModel.add(new Category("Tiền thưởng"));
        incomeCategoryModel.add(new Category("Tiền lãi"));
        incomeCategoryModel.add(new Category("Bán đồ"));
        incomeCategoryModel.add(new Category("Khác"));

        ExpenseCategoryModel expenseCategoryModel = new ExpenseCategoryModel();
        expenseCategoryModel.add(new Category("Hóa đơn"));
        expenseCategoryModel.add(new Category("Giao thông"));
        expenseCategoryModel.add(new Category("Mua sắm"));
        expenseCategoryModel.add(new Category("Giải trí"));
        expenseCategoryModel.add(new Category("Mua sắm"));
        expenseCategoryModel.add(new Category("Du lịch"));
        expenseCategoryModel.add(new Category("Sức khỏe"));
        expenseCategoryModel.add(new Category("Giáo dục"));
        expenseCategoryModel.add(new Category("Bảo hiểm"));
        expenseCategoryModel.add(new Category("Khác"));

        WalletCategoryModel walletCategoryModel = new WalletCategoryModel();
        walletCategoryModel.add(new Category("Tiền mặt"));
        walletCategoryModel.add(new Category("Tài khoản ngân hàng"));
        walletCategoryModel.add(new Category("Khác"));
    }

    public void write(String field, String value){
        reference.child(encrypt(field)).setValue(encrypt(value));
    }
}
