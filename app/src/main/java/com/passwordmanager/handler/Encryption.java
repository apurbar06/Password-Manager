package com.passwordmanager.handler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Main
 */
public class Encryption {
    static Cipher cipher;
    SecretKeySpec secretKey;

    public Encryption(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {

        /*
         * create key If we need to generate a new key use a KeyGenerator If we have
         * existing plaintext key use a SecretKeyFactory
         */
        // KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        // keyGenerator.init(128); // block size is 128bits
        // SecretKey secretKey = keyGenerator.generateKey();

        byte[] salt = "salt1234".getBytes();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256); // AES-256
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] key = f.generateSecret(spec).getEncoded();
        secretKey = new SecretKeySpec(key, "AES");
        cipher = Cipher.getInstance("AES"); // SunJCE provider AES algorithm,

        /*
         * Cipher Info Algorithm : for the encryption of electronic data mode of
         * operation : to avoid repeated blocks encrypt to the same values. padding:
         * ensuring messages are the proper length necessary for certain ciphers
         * mode/padding are not used with stream cyphers.
         *
         * cipher = Cipher.getInstance("AES"); // SunJCE provider AES algorithm,
         * mode(optional) and padding // schema(optional)
         *
         * String plainText = "AES Symmetric Encryption Decryption";
         * System.out.println("Plain Text Before Encryption: " + plainText);
         *
         * String encryptedText; encryptedText = encrypt(plainText, secretKey);
         *
         * System.out.println("Encrypted Text After Encryption: " + encryptedText);
         *
         * String decryptedText = decrypt(encryptedText, secretKey);
         * System.out.println("Decrypted Text After Decryption: " + decryptedText);
         *
         */

    }

    public String encrypt(String plainText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        String encryptedText = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(encryptedByte);
        } else {
            encryptedText = android.util.Base64.encodeToString(encryptedByte, android.util.Base64.DEFAULT);
        }
        return encryptedText;
    }

    public String decrypt(String encryptedText)
            throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] encryptedTextByte;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Base64.Decoder decoder = Base64.getDecoder();
            encryptedTextByte = decoder.decode(encryptedText);
        } else {
            encryptedTextByte = android.util.Base64.decode(encryptedText, android.util.Base64.DEFAULT);
        }
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        return new String(decryptedByte);
    }
}