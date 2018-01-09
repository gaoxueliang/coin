/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.util.TreeMap;

/**
 *
 * @author git
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.print("user.home=");
        System.out.println(System.getProperty("user.home"));

        System.out.println(System.getProperty("java.version"));
//        String s1 = new StringBuilder("go").append("od").toString();
//        System.out.println(s1.intern() == s1);
//        String s2 = new StringBuilder("ja").append("va").toString();
//        System.out.println(s2.intern() == s2);

//        String s1 = "Programming";
//        String s2 = new String("Programming");
//        String s3 = "Program";
//        String s4 = "ming";
//        String s5 = "Program" + "ming";
//        String s6 = s3 + s4;
//        System.out.println(s1 == s2);
//        System.out.println(s1 == s5);
//        System.out.println(s1 == s6);
//        System.out.println(s1 == s6.intern());
//        System.out.println(s2 == s2.intern());
        String s1 = "你好";
        String s2 = new String(s1.getBytes("GB2312"), "ISO-8859-1");
        System.out.println(s2);
        String s3 = new String(s1.getBytes("unicode"), "utf-8");
        s3 = new String(s1.getBytes() ,"utf-8");
        System.out.println(s3);
        
        TreeMap<Object,Object> tmap = new TreeMap<Object, Object>();
        tmap.put(s3, s2);
        Thread.sleep(0);
    }

}
