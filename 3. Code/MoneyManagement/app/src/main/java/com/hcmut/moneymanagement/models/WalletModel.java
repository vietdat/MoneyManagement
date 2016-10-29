package com.hcmut.moneymanagement.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.Wallet.WalletAdapter;
import com.hcmut.moneymanagement.objects.Wallet;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.google.android.gms.internal.zzs.TAG;
import static java.lang.Integer.parseInt;

public class WalletModel extends Model{
    private Context context;
    private ArrayList<String> names;
    private ArrayList<Wallet> wallets;
    private ArrayAdapter<String> nameAdapter;
    private WalletAdapter walletAdapter;

    public WalletModel(){
        names = new ArrayList<String>();
        wallets = new ArrayList<Wallet>();
        // Wallets refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("wallets"));
    }

    public void initWalletAdapter(Activity activity){
        walletAdapter = new WalletAdapter(activity, R.layout.wallet_item, wallets);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                wallets.clear();
                // [START_EXCLUDE]

                for (DataSnapshot walletSnapshot : dataSnapshot.getChildren()) {
                    Wallet wallet = new Wallet();

                    //get name
                    Object objWallet = walletSnapshot.child(encrypt("name")).getValue();
                    wallet.setName(decrypt(objWallet.toString()));
                    Log.w("Name", decrypt(objWallet.toString()));

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
        nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, names);

        //Event Listenner
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Object objWallet = categorySnapshot.child(encrypt("name")).getValue();
                    names.add(decrypt(objWallet.toString()));
                }
                nameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadWallet:onCancelled", databaseError.toException());
            }
        });
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
        return walletAdapter;
    }
 }
