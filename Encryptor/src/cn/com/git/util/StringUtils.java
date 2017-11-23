/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.git.util;

/**
 *
 * @author git
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? (str2 == null)
                : (str2 == null ? false : str1.equals(str2));
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? (str2 == null)
                : (str2 == null) ? false : str1.equalsIgnoreCase(str2);
    }
}
