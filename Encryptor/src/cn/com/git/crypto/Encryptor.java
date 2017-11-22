/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.git.crypto;

import cn.com.git.util.CopyUtils;
import com.sun.xml.internal.ws.util.StringUtils;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    public static void main(String[] args) {
        System.out.println(CopyUtils.equals(null, null));
        System.out.println(CopyUtils.equals("ab","AB"));
        System.out.println(CopyUtils.equals("abc","abd"));
        System.out.println(CopyUtils.equals("abc","abc"));
        System.out.println(CopyUtils.equals(null, "a"));
        System.out.println(CopyUtils.equals("a", null));
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

    private static String assertAlgorithm(String algorithm) {
        if (CopyUtils.equalsIgnoreCase(algorithm, AES_ALGORITHM)) {
            return AES_ALGORITHM;
        }
        return DES_ALGORITHM;
    }

}
