/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.git.crypto.util;

import cn.com.git.util.StringUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author git
 */
 final class CryptoUtil {

    public static Charset CHARSET = Charset.forName("UTF-8");
    public static String DES_ALGORITHM = "DES";
    public static String AES_ALGORITHM = "AES";
    public static final String DESede_ALGORITHM = "DESede";
    private static final int HEADLEN = 12;
    private static final int KEYLEN = 24;
    public static final String ALGORITHM = "3DES";

    public static byte[] getKey(String algorithm)
            throws NoSuchAlgorithmException {
        algorithm = assertAlgorithm(algorithm);
        KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
        keygen.init(getKeySize(algorithm), new SecureRandom());
        SecretKey deskey = keygen.generateKey();
        return deskey.getEncoded();
    }

    public static byte[] encrypt(byte[] input, byte[] key, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        algorithm = assertAlgorithm(algorithm);
        SecretKey deskey = new SecretKeySpec(key, algorithm);
        Cipher c1 = Cipher.getInstance(algorithm);
        c1.init(1, deskey);
        byte[] cipherByte = c1.doFinal(input);
        return cipherByte;
    }

    public static byte[] decrypt(byte[] input, byte[] key, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        algorithm = assertAlgorithm(algorithm);
        SecretKey deskey = new SecretKeySpec(key, algorithm);
        Cipher c1 = Cipher.getInstance(algorithm);
        c1.init(2, deskey);
        byte[] clearByte = c1.doFinal(input);
        return clearByte;
    }

    public static byte[] md5(byte[] input)
            throws NoSuchAlgorithmException {
        MessageDigest alg = MessageDigest.getInstance("MD5");
        alg.update(input);
        byte[] digest = alg.digest();
        return digest;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }
        return hs.toUpperCase();
    }

    private static String assertAlgorithm(String algorithm) {
        if (StringUtils.equalsIgnoreCase(algorithm, AES_ALGORITHM)) {
            return AES_ALGORITHM;
        }
        return DES_ALGORITHM;
    }

    public static final String encrypt(String plain) {
        if ((plain == null) || (plain.length() == 0)) {
            return null;
        }
        try {
            byte[] seed = getSeed();
            String seedStr = base64Encode(seed);
            byte[] key = generateKey(seed);
            return seedStr + encrypt(plain, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plain;
    }

    public static final String encrypt(String plain, String key)
            throws Exception {
        return encrypt(plain, getKeyByString(key));
    }

    private static String encrypt(String plain, byte[] key)
            throws Exception {
        byte[] encrypted = encryptByJCE(plain.getBytes(CHARSET), key);
        return base64Encode(encrypted);
    }

    private static byte[] encryptByJCE(byte[] plainText, byte[] key)
            throws Exception {
        SecretKey securekey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(1, securekey);
        return cipher.doFinal(plainText);
    }

    public static final String decrypt(String cryptograph) {
        if ((cryptograph == null) || (cryptograph.length() == 0)) {
            return "";
        }
        try {
            String seedStr = cryptograph.substring(0, 12);
            byte[] seed = base64Decode(seedStr);
            byte[] key = generateKey(seed);
            return decryptByJCE(cryptograph.substring(12), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final String decrypt(String cryptograph, String key)
            throws Exception {
        return decryptByJCE(cryptograph, getKeyByString(key));
    }

    private static final String decryptByJCE(String cryptograph, byte[] key)
            throws Exception {
        byte[] encrypted = base64Decode(cryptograph);
        return new String(decrypt(encrypted, key));
    }

    private static byte[] decrypt(byte[] cryptograph, byte[] key)
            throws Exception {
        SecretKey securekey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(2, securekey);
        return cipher.doFinal(cryptograph);
    }

    private static byte[] generateKey(byte[] seed)
            throws Exception {
        byte[] key = {36, 80, 114, 105, 109, 101, 116, 111, 110, 45, 69, 79, 83, 32, 87, 105, 108, 108, 95, 87, 105, 110, 33, 36};
        for (int i = 0; i < seed.length; i++) {
            for (int j = 0; j < key.length; j++) {
                key[j] = ((byte) (key[j] ^ seed[i]));
            }
        }
        return key;
    }

    private static byte[] getSeed() {
        long seed = new Date().getTime();
        byte[] seedBytes = String.valueOf(seed).getBytes(CHARSET);
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return base64Decode(base64Encode(digest.digest(seedBytes)).substring(0, 12));
        } catch (Exception e) {
        }
        return seedBytes;
    }

    public static String base64Encode(byte[] bytes) {
        Base64Encoder encoder = new Base64Encoder();
        return encoder.encode(bytes);
    }

    public static byte[] base64Decode(String str)
            throws IOException {
        Base64Encoder encoder = new Base64Encoder();
        return encoder.decode(str);
    }

    private static byte[] getKeyByString(String key) {
        byte[] oldKeys = key.getBytes(CHARSET);
        byte[] newKeys = new byte[24];
        for (int i = 0; i < oldKeys.length; i++) {
            if (i == 24) {
                break;
            }
            newKeys[i] = oldKeys[i];
        }
        return newKeys;
    }

    private static int getKeySize(String algorithm) {
        if (algorithm.equals(DES_ALGORITHM)) {
            return 56;
        }
        if (algorithm.equals("DESede")) {
            return 112;
        }
        if (algorithm.equals(AES_ALGORITHM)) {
            return 128;
        }
        return 0;
    }
}
