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

    public Chat(String sender, String receiver, String encrypted) {
        this.sender = sender;
        this.receiver = receiver;
        this.encrypted = encrypted;

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

    public String getEncrypted() {
        String encryptedMessage = encrypted;
        return encryptedMessage;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public String getDecrypted() throws UnsupportedEncodingException {
        byte[] encryptionKey = {2, 1, -2, 0, 9, 5, 7, 4, 4, 2, 5, 3, 2, 5, 8, 9};
        Cipher cipher = null;
        Cipher decipher = null;
        SecretKeySpec secretKeySpec = null;
        String encryptedString = getEncrypted();

        byte[] EncryptedByte = encryptedString.getBytes("ISO-8859-1");
        byte[] decryption;
        //private decryptionMethod requires string variable
        //variable byte array is created, function to convert string into bytes and use ISO-8859-1 for reference if decrypted
        //byte type array variable created
        try {
            cipher = Cipher.getInstance("AES");
            decipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        try {
            decipher.init(cipher.DECRYPT_MODE, secretKeySpec);
            decryption = decipher.doFinal(EncryptedByte);
            encryptedString = new String(decryption);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return encryptedString;
        //try attempts to initialize decipher variable by calling decryption mode to encrypt byte while using the private key and the specified algorithm
        //new variable created, function do final method resets cipher from any bytes that was previously encrypted
        //new variable string is set to decryption
        //catches errors such as wrongKey, wrongBlockSize and badPadding and throws them all as exceptions
    }


}

