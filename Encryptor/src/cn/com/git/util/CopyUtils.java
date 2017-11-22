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
public class CopyUtils {

    public static boolean equals(String str1, String str2) {
        return str1 == null ? (str2 == null)
                : (str2 == null ? false : str1.equals(str2));
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? (str2 == null)
                : (str2 == null) ? false : str1.equalsIgnoreCase(str2);
    }
}
