package com.hcmut.moneymanagement.models;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Admin on 26-Nov-16.
 */
public class UserNameModel extends Model{

    public String userName;

    public UserNameModel(){
        reference = FirebaseDatabase.getInstance().getReference().child(uidEncrypted).child(encrypt("username"));
        reference.keepSynced(true);
    }

    public void initNameAdapter(final TextView name){
        //Event Listenner
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object objName = dataSnapshot.getValue();
                userName = objName.toString();
                name.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadWallet:onCancelled", databaseError.toException());
            }
        });
    }

    public String getName() {
        return userName;
    }

}
