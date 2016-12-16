package com.hcmut.moneymanagement.models;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Admin on 24-Nov-16.
 */
public class Utils {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}