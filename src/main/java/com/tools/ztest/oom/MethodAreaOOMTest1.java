package com.tools.ztest.oom;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/8 下午5:28
 */
public class MethodAreaOOMTest1 {

    /**
     * 测试失败!!!
     * 异常信息: Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     */
    public static void main(String[] args) throws Throwable {
        long classCounter = 0l;
        ClassPool classPool = ClassPool.getDefault();
        String className = null;
        CtClass ctClass = null;
        CtField ctField = null;
        CtMethod ctMethod = null;
        try {
            while (true) {
                className = "Test" + classCounter;
                ctClass = classPool.makeClass(className);
                ctField = new CtField(CtClass.intType, "i", ctClass);
                ctClass.addField(ctField);
                ctMethod = CtMethod.make("public String toString() {  return (\"Hello world. this.i = \" + this.i); }", ctClass);
                ctClass.addMethod(ctMethod);
                ctClass.toClass();
                classCounter++;
            }
        } catch (Throwable t) {
            System.out.println("=== classCounter: " + classCounter);
            throw t;
        }
    }
}
