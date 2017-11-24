/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.tomcat.dbcp.dbcp2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author git
 */
class BasicDataSourceFactoryEx {

    private static Charset CHARSET = Charset.forName("UTF-8");
    private static final char[] SIXTY_FOUR_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[] REVERSE_MAPPING = new int[123];
    private static final String PREFIX_3DES = "{3DES}";

    static {
        Arrays.fill(REVERSE_MAPPING, -1);
        for (int i = 0; i < SIXTY_FOUR_CHARS.length; i++) {
            REVERSE_MAPPING[SIXTY_FOUR_CHARS[i]] = i;
        }
    }

    protected static final String decrypt(String cryptograph) {
        if (cryptograph == null || cryptograph.isEmpty()) {
            return "";
        }
        int index = cryptograph.indexOf(PREFIX_3DES);
        if (index < 0) {
            return cryptograph;
        }
        return cryptograph.substring(0, index)
                + decrypt_3DES(cryptograph.substring(index + PREFIX_3DES.length()));
    }

    private static String decrypt_3DES(String cryptograph) {
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
        return cryptograph;
    }

    private static String decryptByJCE(String cryptograph, byte[] key)
            throws Exception {
        byte[] encrypted = base64Decode(cryptograph);
        return new String(decrypt(encrypted, key), CHARSET);
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

    private static byte[] base64Decode(String str) throws IOException {
        return decode(str);
    }

    private static byte[] decode(String input) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            StringReader in = new StringReader(input);
            for (int i = 0; i < input.length(); i += 4) {
                int[] a = {mapCharToInt(in), mapCharToInt(in), mapCharToInt(in), mapCharToInt(in)};
                int oneBigNumber = (a[0] & 0x3F) << 18 | (a[1] & 0x3F) << 12 | (a[2] & 0x3F) << 6 | a[3] & 0x3F;
                for (int j = 0; j < 3; j++) {
                    if (a[(j + 1)] >= 0) {
                        out.write(0xFF & oneBigNumber >> 8 * (2 - j));
                    }
                }
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new Error(e + ": " + e.getMessage());
        }
    }

    private static int mapCharToInt(Reader input)
            throws IOException {
        int c;
        while ((c = input.read()) != -1) {
            if (c > 0 && c < 123) {
                return REVERSE_MAPPING[c];
            }
            return -1;
        }
        return -1;
    }
}
