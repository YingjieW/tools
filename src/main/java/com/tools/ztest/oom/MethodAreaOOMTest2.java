package com.tools.ztest.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/8 下午6:09
 */
public class MethodAreaOOMTest2 {

    public static void main(String[] args) throws Throwable {
        List<String> list = new ArrayList<String>();
        int i = 0;
        try {
            while (true) {
                list.add(String.valueOf(i++).intern());
            }
        } catch (Throwable t) {
            System.out.println("=== i: " + i);
            throw t;
        }
    }
}
