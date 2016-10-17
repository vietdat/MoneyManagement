package com.hcmut.moneymanagement.cryptogrophy;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public abstract class Crypto {
    // Using DES algorithm for encryption
    protected final String algorithm = "DES";

    // Flags for Firebase storeable
    // More infomation: https://developer.android.com/reference/android/util/Base64.html
    protected final int Base64Flags = Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING;

    // Offset to make key
    private final int offset = 10;

    protected Cipher cipher;
    // Protected option mode for cipher object: Cipher.DECRYPT_MODE || Cipher.ENCRYPT_MODE
    protected int opmode;

    // Key for encryption and decyption
    protected SecretKey key;

    protected Crypto(){
    }

    protected void setKey(String key){
        byte[] keyBytes = key.getBytes();
        try {
            // Creates a DESKeySpec object using the first 8 bytes in key, beginning at offset
            DESKeySpec desKeySpec = new DESKeySpec(keyBytes, offset);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
            this.key = keyFactory.generateSecret(desKeySpec);
        }catch(InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }

        this.init();
    }

    protected void init(){
        try{
            cipher = Cipher.getInstance(algorithm);
            cipher.init(opmode, key);
        }catch(NoSuchAlgorithmException  | NoSuchPaddingException | InvalidKeyException e){
            e.printStackTrace();
        }
    }
}
