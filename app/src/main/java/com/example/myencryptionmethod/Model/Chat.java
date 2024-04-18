package com.example.myencryptionmethod.Model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Chat {

    private String sender;
    private String receiver;
    private String encrypted;
    private String decrypted;
    private String message;

    public Chat (String sender, String receiver, String encrypted,String decrypted,String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.encrypted = encrypted;
        this.decrypted = decrypted;
        this.message = message;


    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEncrypted() {
        String msg = encrypted;
        return msg;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public String getDecrypted() {
        //String msg = encrypted.toString().getBytes("ISO-8859-1");
        //String dMSG = AESDecryptionMethod(msg);
        return decrypted;
    }

    public void setDecrypted(String decrypted) {
        this.decrypted = decrypted;
    }



}

