package com.tools.ztest.test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/28 下午5:08
 */
class Point {
    private int x = 0;
    private int y = 0;
    public void move(int dx, int dy) {
        x = x + dx;
        y = y + dy;
    }

    public void print() {
        System.out.println("***  x = " + x + ", y = " + y);
    }
}

public class TestClassPool02 {
    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.tools.ztest.test.Point");
        CtMethod ctMethod = ctClass.getDeclaredMethod("move");
        ctMethod.insertBefore("System.out.println(\"###  x = \" + x + \", y = \" + y);");
        ctMethod.insertBefore("System.out.println(\"##  dx = \" + dx + \",dy = \" + dy);");
        Class clazz = ctClass.toClass();
        Point point = (Point) clazz.newInstance();
        point.move(1, 1);
        point.print();

//        ctClass.defrost();
        ctMethod.insertAfter("System.out.println(\"###  x = \" + x + \", y = \" + y);");
        point.print();
    }
}
