/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.git.crypto.util;

import cn.com.git.crypto.Encryptor;
import cn.com.git.crypto.util.CryptoUtil;
import java.nio.charset.Charset;
import java.util.Date;

/**
 *
 * @author git
 */
public class Test {

    public static void main(String[] args) throws Exception {
        System.err.println(Charset.defaultCharset().name());

        String s = "c0me0n345";
        String key = "git";

        System.out.println(Encryptor.encrypt(s));
        System.out.println(Encryptor.encrypt(s, key));
        System.out.println(CryptoUtil.encrypt(s));
        System.out.println(CryptoUtil.encrypt(s, key));

        long seed = new Date().getTime();
        System.out.println(seed);
        long time = System.currentTimeMillis();
        System.out.println(time);
    }

}
