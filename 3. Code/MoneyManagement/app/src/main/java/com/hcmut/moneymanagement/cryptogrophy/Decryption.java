package com.hcmut.moneymanagement.cryptogrophy;

import android.util.Base64;
import android.util.Log;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class Decryption extends Crypto{
    public Decryption(){
        this.opmode = Cipher.DECRYPT_MODE;
    }

    public Decryption(String key){
        this.opmode = Cipher.DECRYPT_MODE;
        this.setKey(key);
    }

    public String decrypt(String input){
        /*
        try {
            if( !input.isEmpty()){
                // Decode from String to Byte array
                byte[] inputBytes = Base64.decode(input, Base64Flags);
                // Decrypt the Byte array
                byte[] outputBytes = cipher.doFinal(inputBytes);
                // Return output string
                // return new String(outputBytes);
                return input;
            }
        }catch(IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        return "";
        */
        return input;
    }
}
