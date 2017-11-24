/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.tomcat.dbcp.dbcp2;

import cn.com.git.crypto.util.CryptoUtil;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author git
 */
@RunWith(Parameterized.class)
public class BasicDataSourceFactoryExTest {

    private final String parameter;

    public BasicDataSourceFactoryExTest(String parameter) {
        this.parameter = parameter;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Parameters
    public static String[] getParameters() {
        int size = 100000;
        int maxLength = 100;
        String book = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
                + "~!@#$%^&*()_-+={}[]|\\;:'\",./<>?";
        int bookLength = book.length();
        String[] parameters = new String[size];
        Random random = new Random();
        StringBuilder sb = new StringBuilder(maxLength);
        for (int i = 0; i < parameters.length; i++) {
            sb.setLength(0);

            int length = random.nextInt(maxLength) + 1;
//            length = maxLength;
            for (int j = 0; j < length; j++) {
                int index = random.nextInt(bookLength);
                sb.append(book.charAt(index));
            }
            parameters[i] = sb.toString();
        }
        parameters[size - 2] = "c0me0n345";
        parameters[size - 1] = "loan";
        return parameters;
    }

    @Test
    public void testDecrypt() {
        String cryptograph = "{3DES}" + CryptoUtil.encrypt(parameter);
        String expResult = parameter;
        String result = BasicDataSourceFactoryEx.decrypt(cryptograph);
        assertEquals(expResult, result);
        if (!expResult.equals(result)) {
            System.out.print(parameter);
            System.out.print("\t!=\t");
            System.out.println(result);
        } else {
            System.out.print(parameter);
            System.out.print("\t==\t");
            System.out.println(result);
        }
    }

}
