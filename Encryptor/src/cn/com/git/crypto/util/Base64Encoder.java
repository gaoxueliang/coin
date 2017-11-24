/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.git.crypto.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

/**
 *
 * @author git
 */
public class Base64Encoder {

    private static final char[] SIXTY_FOUR_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[] REVERSE_MAPPING = new int[123];

    static {
        Arrays.fill(REVERSE_MAPPING, -1);
        for (int i = 0; i < SIXTY_FOUR_CHARS.length; i++) {
            REVERSE_MAPPING[SIXTY_FOUR_CHARS[i]] = i;
        }
    }

    public String encode(byte[] input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length; i += 3) {
            int remaining = Math.min(3, input.length - i);
            int oneBigNumber = (input[i] & 0xFF) << 16 | (remaining <= 1 ? 0 : input[(i + 1)] & 0xFF) << 8 | (remaining <= 2 ? 0 : input[(i + 2)] & 0xFF);
            for (int j = 0; j < 4; j++) {
                result.append(remaining + 1 > j ? SIXTY_FOUR_CHARS[(0x3F & oneBigNumber >> 6 * (3 - j))] : '=');
            }
        }
        return result.toString();
    }

    public byte[] decode(String input) {
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

    private int mapCharToInt(Reader input)
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
