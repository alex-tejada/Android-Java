package com.example.root.aplicacionbasedatos;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * Created by root on 5/08/17.
 */

public class Encriptar {

    public String sha1Encryption (String stringToHash)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] result = digest.digest(stringToHash.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();

            for (byte b : result)
            {
                sb.append(String.format("%02X", b));
            }

            return sb.toString();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String encrypt(String message)
    {
        String encryptedMessage=null;
        try
        {
            encryptedMessage= AESCrypt.encrypt("KEYENCRYPTION", message);
        }
        catch (GeneralSecurityException e)
        {
            //handle error
        }
        return  encryptedMessage;
    }


    public String decrypt(String encryptedMessage)
    {
        String decryptedMessage=null;
        try
        {
            decryptedMessage = AESCrypt.decrypt("KEYENCRYPTION", encryptedMessage);
        }
        catch (GeneralSecurityException e)
        {
            //handle error - could be due to incorrect password or tampered encryptedMsg
        }
        return  decryptedMessage;
    }
}
