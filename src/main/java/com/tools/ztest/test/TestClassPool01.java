package com.tools.ztest.test;

import javassist.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/28 下午4:46
 */
public class TestClassPool01 {
    // 使用ClassPool、CtClass生成一个新的类,
    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        // 生成类
        CtClass ctClass = classPool.makeClass("Test01");
        // 添加属性
        CtField ctField = new CtField(CtClass.intType, "i", ctClass);
        ctClass.addField(ctField);
        // 添加方法
        CtMethod ctMethod = CtMethod.make("public String toString() {  return (\"Hello world. this.i = \" + this.i); }", ctClass);
        ctClass.addMethod(ctMethod);
        Class clazz = ctClass.toClass();
        ctClass.detach();
        System.out.println(clazz.newInstance());
        System.out.println(clazz.getName());
        System.out.println(String.class.getName());
        System.out.println(TestClassPool01.class.getName());
    }
}
