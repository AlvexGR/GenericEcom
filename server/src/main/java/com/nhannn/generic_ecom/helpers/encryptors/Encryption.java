package com.nhannn.generic_ecom.helpers.encryptors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Author: nhannn
 * Source: https://stackoverflow.com/questions/1132567/encrypt-password-in-configuration-files
 */
public class Encryption {
    private static final String password = "8b538870-f9b1-42cc-b241-6e3b4c2de020";
    private static final String salt = "9d45a5db-dd1d-4300-8435-c102dd2d1dc3";
    private static final int iterationCount = 5000;
    private static final int keyLength = 256;
    private static SecretKeySpec key;

    private Encryption() {}


    private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static String encrypt(String toEncrypt) {
        try {
            if (key == null) {
                key = createSecretKey(password.toCharArray(), salt.getBytes(), iterationCount, keyLength);
            }
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key);
            AlgorithmParameters parameters = pbeCipher.getParameters();
            IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
            byte[] cryptoText = pbeCipher.doFinal(toEncrypt.getBytes("UTF-8"));
            byte[] iv = ivParameterSpec.getIV();
            return base64Encode(iv) + ":" + base64Encode(cryptoText);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decrypt(String toDecrypt) {
        try {
            if (key == null) {
                key = createSecretKey(password.toCharArray(), salt.getBytes(), iterationCount, keyLength);
            }
            String iv = toDecrypt.split(":")[0];
            String property = toDecrypt.split(":")[1];
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
            return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static byte[] base64Decode(String property) {
        return Base64.getDecoder().decode(property);
    }
}
