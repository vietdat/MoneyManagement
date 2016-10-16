package com.hcmut.moneymanagement.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.hcmut.moneymanagement.cryptogrophy.Decryption;
import com.hcmut.moneymanagement.cryptogrophy.Encryption;

public abstract class Model {
    protected Encryption encryption;
    protected Decryption decryption;

    protected DatabaseReference reference;
    protected FirebaseUser currentUser;

    // Key for Encription and Decryption
    private String key;

    // UID after encrypted
    protected String uidEncrypted;

    public Model(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        key = currentUser.getUid();
        encryption = new Encryption(key);
        decryption = new Decryption(key);

        uidEncrypted = encryption.encrypt(currentUser.getUid());
    }

    public DatabaseReference getReference(){
        return reference;
    }

    public String decrypt(String input){
        return decryption.decrypt(input);
    }

    public String encrypt(String input){
        return encryption.encrypt(input);
    }

}
