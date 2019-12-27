package com.nhannn.generic_ecom.helpers.encryptors;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encryption {
    public static String encrypt(String toEncrypt)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            byte[] digest = md.digest();
            String encryptedData = DatatypeConverter.printHexBinary(digest).toUpperCase();
            return encryptedData;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
