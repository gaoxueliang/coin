/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.git.crypto;

import cn.com.git.crypto.util.Base64Encoder;
import cn.com.git.util.StringUtils;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author git
 */
public class Encryptor {

    public static String DES_ALGORITHM = "DES";
    public static String AES_ALGORITHM = "AES";
    public static final String DESede_ALGORITHM = "DESede";
    private static final int HEADLEN = 12;
    private static final int KEYLEN = 24;
    public static final String ALGORITHM = "3DES";
    public static final byte[] DEFAULT_DES_KEY = {-99, 118, 97, -105, -51, -17, 81, 14};

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

    private static String decryptByJCE(String cryptograph, byte[] key)
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

    public static byte[] decrypt(byte[] input, byte[] key, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        algorithm = assertAlgorithm(algorithm);
        SecretKey deskey = new SecretKeySpec(key, algorithm);
        Cipher c1 = Cipher.getInstance(algorithm);
        c1.init(2, deskey);
        byte[] clearByte = c1.doFinal(input);
        return clearByte;
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
        return null;
    }

    public static final String encrypt(String plain, String key)
            throws Exception {
        return encrypt(plain, getKeyByString(key));
    }

    private static final String encrypt(String plain, byte[] key)
            throws Exception {
        byte[] encrypted = encryptByJCE(plain.getBytes(), key);
        return base64Encode(encrypted);
    }

    private static byte[] encryptByJCE(byte[] plainText, byte[] key)
            throws Exception {
        SecretKey securekey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(1, securekey);
        return cipher.doFinal(plainText);
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

    private static byte[] fixToBytes(String s, int destLength) {
        byte[] bytes = s.getBytes();
        int xLength = destLength - bytes.length;
        if (xLength == 0) {
            return bytes;
        }
        if (xLength > 0) {
            byte[] result = new byte[destLength];
            System.arraycopy(bytes, 0, result, 0, bytes.length);
            return result;
        }
        byte[] result = new byte[destLength];
        System.arraycopy(bytes, 0, result, 0, destLength - 1);
        return result;
    }

    private static String assertAlgorithm(String algorithm) {
        if (StringUtils.equalsIgnoreCase(algorithm, AES_ALGORITHM)) {
            return AES_ALGORITHM;
        }
        return DES_ALGORITHM;
    }

    private static byte[] getSeed() {
        long seed = new Date().getTime();
        byte[] seedBytes = String.valueOf(seed).getBytes();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return base64Decode(base64Encode(digest.digest(seedBytes)).substring(0, 12));
        } catch (Exception e) {
        }
        return seedBytes;
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

    private static byte[] getKeyByString(String key) {
        byte[] oldKeys = key.getBytes();
        byte[] newKeys = new byte[24];
        for (int i = 0; i < oldKeys.length; i++) {
            if (i == 24) {
                break;
            }
            newKeys[i] = oldKeys[i];
        }
        return newKeys;
    }

    public static String base64Encode(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        Base64Encoder encoder = new Base64Encoder();
        return encoder.encode(bytes);
    }

    public static byte[] base64Decode(String text) {
        if (text == null) {
            return new byte[0];
        }
        Base64Encoder encoder = new Base64Encoder();
        return encoder.decode(text);
    }

    public static String decryptByDES(String cryptograph, String keyString) {
        if (cryptograph == null) {
            return null;
        }
        byte[] bytes = base64Decode(cryptograph);
        byte[] key = DEFAULT_DES_KEY;
        if (!StringUtils.isBlank(keyString)) {
            key = fixToBytes(keyString, 8);
        }
        try {
            return new String(decrypt(bytes, key, DES_ALGORITHM));
        } catch (Exception e) {
            e.printStackTrace();
            return "EOS Error 24000011";
        }
    }

    public static String encryptByDES(String plainText, String keyString) {
        if (plainText == null) {
            return null;
        }
        byte[] key = DEFAULT_DES_KEY;
        if (!StringUtils.isBlank(keyString)) {
            key = fixToBytes(keyString, 8);
        }
        try {
            byte[] cryptograph = encrypt(plainText.getBytes(), key, DES_ALGORITHM);

            return base64Encode(cryptograph);
        } catch (Exception e) {
            e.printStackTrace();
            return "EOS Error 24000012";
        }
    }

}
