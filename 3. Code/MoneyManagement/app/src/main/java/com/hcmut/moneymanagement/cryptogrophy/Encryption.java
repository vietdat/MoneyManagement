package com.hcmut.moneymanagement.cryptogrophy;

import android.util.Base64;
import android.util.Log;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class Encryption extends Crypto {
    public Encryption(){
        this.opmode = Cipher.ENCRYPT_MODE;
    }
    public Encryption(String key){
        this.opmode = Cipher.ENCRYPT_MODE;
        this.setKey(key);
    }

    public String encrypt(String input){
        try {
            if( !input.isEmpty() ){
                // Encrypt the text
                byte[] inputBytes = input.getBytes();
                byte[] outputBytes = cipher.doFinal(inputBytes);
                // Encode for Firebase storeable
                String output = Base64.encodeToString(outputBytes, Base64Flags);
                //return output;
                return input;
            }
        }catch(IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        return "";
    }
}
