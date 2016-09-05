package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/2 上午10:13
 */
public class TestString {

    public static void main(String[] args) throws Exception {
        testSubString();
    }

    private static void testSubString() {
        String str1 = "abcde";
        String str2 = "abc";
        String str3 = str1.substring(0);
        String str4 = str1.substring(0, 3);
        String str5 = str1.substring(0, 3).intern();
        String str6 = str1.substring(0) + "";

        System.out.println("==> str1: " + str1);
        System.out.println("==> str2: " + str2);
        System.out.println("==> str3: " + str3);
        System.out.println("==> str4: " + str4);
        System.out.println("==> str5: " + str5);
        System.out.println("==> str6: " + str6);
        System.out.println();

        System.out.println("==> str1==str3: " + (str1 == str3));
        System.out.println("==> str2==str4: " + (str2 == str4));
        System.out.println("==> str2==str5: " + (str2 == str5));
        System.out.println("==> str1==str6: " + (str1 == str6));
    }

    private static void testStringIntern() throws Exception {
        String str1 = "abc";
        String str2 = "abc";
        System.out.println("==> " + (str1==str2));

        String str3 = new String("abc");
        String str4 = new String("abc");
        System.out.println("==> " + (str3== str4));

        String str5 = new String("abc").intern();
        String str6 = new String("abc").intern();
        System.out.println("==> " + (str5 == str6));
        System.out.println("==> " + (str5 == str3));
        System.out.println("==> " + (str5 == str1));
    }

    private static void testStringBufferBuilder() throws Exception {
        List<Thread> threadList = new ArrayList<Thread>(200000);

        final StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < 100000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    stringBuffer.append("x");
                }
            });
            thread.start();
            threadList.add(thread);
        }

        final StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 100000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    stringBuilder.append("x");
                }
            });
            thread.start();
            threadList.add(thread);
        }

        for(Thread thread : threadList) {
            thread.join();
        }

        System.out.println("===> StringBuffer : " + stringBuffer.length());
        System.out.println("===> StringBuilder: " + stringBuilder.length());
    }

    private static void testTimeConsume() throws Exception {
        long start1 = System.currentTimeMillis();
        String str1 = "This "  + "is " + "only " + "a " + "simple " + "test.";
        System.out.println("===> cost: " + (System.currentTimeMillis() - start1));

        long start2 = System.currentTimeMillis();
        String str2 = "This ";
        String str3 = "is ";
        String str4 = "only ";
        String str5 = "a ";
        String str6 = "simple ";
        String str7 = "test.";
        String str8 = str2 + str3 + str4 + str5 + str6 + str7;
        System.out.println("===> cost: " + (System.currentTimeMillis() - start2));
    }
}
