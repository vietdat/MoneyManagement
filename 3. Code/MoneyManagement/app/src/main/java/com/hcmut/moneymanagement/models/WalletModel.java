package com.hcmut.moneymanagement.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Wallets.WalletAdapter;
import com.hcmut.moneymanagement.objects.Wallet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.internal.zzs.TAG;
import static java.lang.Integer.parseInt;

public class WalletModel extends Model{
    private Context context;

    public ArrayList<String> keys;

    private ArrayList<String> names;

    public ArrayList<Wallet> wallets;

    private ArrayAdapter<String> nameAdapter;
    private WalletAdapter walletAdapter;

    public WalletModel(){
        keys = new ArrayList<String>();

        names = new ArrayList<String>();
        wallets = new ArrayList<Wallet>();
        keys = new ArrayList<String>();

        // Wallets refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("wallets"));
        reference.keepSynced(true);
    }

    public void initWalletAdapter(Activity activity){
        walletAdapter = new WalletAdapter(activity, R.layout.wallet_item, wallets);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wallets.clear();
                for (DataSnapshot walletSnapshot : dataSnapshot.getChildren()) {
                    Wallet wallet = new Wallet();

                    //get name
                    Object objWallet = walletSnapshot.child(encrypt("name")).getValue();
                    wallet.setName(decrypt(objWallet.toString()));

                    //get type
                    Object objType = walletSnapshot.child(encrypt("type")).getValue();
                    wallet.setType(decrypt(objType.toString()));

                    //currencyUnit
                    Object objCurrencyUnit = walletSnapshot.child(encrypt("currencyUnit")).getValue();
                    wallet.setCurrencyUnit(decrypt(objCurrencyUnit.toString()));

                    //description
                    Object objDescription = walletSnapshot.child(encrypt("description")).getValue();
                    wallet.setDescription(decrypt(objDescription.toString()));

                    //date
                    Object objDate = walletSnapshot.child(encrypt("date")).getValue();
                    wallet.setDate(decrypt(objDate.toString()));

                    //initialAmount
                    Object objInitialAmount = walletSnapshot.child(encrypt("initialAmount")).getValue();
                    wallet.setInitialAmount(parseInt(decrypt(objInitialAmount.toString())));

                    //currentAmount
                    Object objCurrentAmount = walletSnapshot.child(encrypt("currentAmount")).getValue();
                    wallet.setCurrentAmount(parseInt(decrypt(objCurrentAmount.toString())));

                    wallets.add(wallet);
                }
                walletAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadWallet:onCancelled", databaseError.toException());
            }
        });
    }

    public void initNameAdapter(Context context){
        nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, names);

        //Event Listenner
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                keys.clear();
                names.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object objWallet = categorySnapshot.child(encrypt("name")).getValue();
                    names.add(decrypt(objWallet.toString()));
                    keys.add(categorySnapshot.getKey());
                }
                nameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadWallet:onCancelled", databaseError.toException());
            }
        });
    }

    // Update a wallet
    public void update(String key, Map<String, Object> data){
        reference.child(key).updateChildren(data);
    }

    // Remove a wallet
    public void remove(String key){
        reference.child(key).removeValue();
    }

    public void add(Wallet wallet){
        Field[] fields = Wallet.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length; i++){
            try {
                String fieldName = fields[i].getName();
                if( !fieldName.equals("serialVersionUID") && !fieldName.equals("$change")){
                    // Get value object of wallet
                    Object value = fields[i].get(wallet);
                    if(value != null){
                        String valueEncrypted = encryption.encrypt(value.toString());
                        // Write encypted value to Firebase
                        reference.child(key).child(encrypt(fieldName)).setValue(valueEncrypted);
                    }
                }

            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }


    public ArrayAdapter<String> getNameAdapter(){
        if(nameAdapter != null) {
            return nameAdapter;
        }
        return  null;
    }

    public WalletAdapter getWalletAdapter() {
        if(walletAdapter!=null){
            return walletAdapter;
        }
        return null;
    }

    // Update a amount
    public void increaseMoneyAmount(String key, final int amount){
        final DatabaseReference walletReference =  FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("wallets")).child(key);

        walletReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object objCurrentAmount = dataSnapshot.child(encrypt("currentAmount")).getValue();
                if(objCurrentAmount != null) {
                    int currentAmount = Integer.parseInt(objCurrentAmount.toString());
                    currentAmount += amount;
                    Map<String, Object> update = new HashMap<String, Object>();
                    update.put(encrypt("currentAmount"), currentAmount);

                    walletReference.updateChildren(update);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void decreateMoneyAmount(String key, int amount){
        this.increaseMoneyAmount(key, -amount);
    }

 }
