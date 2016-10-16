package com.hcmut.moneymanagement.models;

import com.google.firebase.database.FirebaseDatabase;
import com.hcmut.moneymanagement.objects.User;
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
    }

    public void write(String field, String value){
        reference.child(encrypt(field)).setValue(encrypt(value));
    }
}
