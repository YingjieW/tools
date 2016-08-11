package com.tools.ztest.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/11 下午2:54
 */
public class ParseAnnotation {

    public static void main(String[] args) throws Exception {
        parseMethodAnnotation();
        parseFieldAnnation();
        parseTypeAnnotation();
        parseConstructorAnnotation();
    }

    /**
     * 打印输出类TestAnnotation中的类成员方法注解
     * @throws Exception
     */
    public static void parseMethodAnnotation() throws Exception {
        Class clazz = UseAnnotation.class;
        Method[] methods = clazz.getDeclaredMethods();
        if(methods.length > 0) {
            boolean hasAnnotation = false;
            TestAnnotation testAnnotation = null;
            for(Method method : methods) {
                hasAnnotation = method.isAnnotationPresent(TestAnnotation.class);
                if(hasAnnotation) {
                    testAnnotation = method.getAnnotation(TestAnnotation.class);
                    print(testAnnotation);
                }
            }
        }
    }

    /**
     * 打印输出类TestAnnotation中的成员变量注解
     * @throws Exception
     */
    public static void parseFieldAnnation() throws Exception {
        Class clazz = UseAnnotation.class;
        Field[] fields = clazz.getDeclaredFields();
        if(fields.length > 0) {
            boolean hasAnnotation = false;
            TestAnnotation testAnnotation = null;
            for(Field field : fields) {
                hasAnnotation = field.isAnnotationPresent(TestAnnotation.class);
                if(hasAnnotation) {
                    testAnnotation = field.getAnnotation(TestAnnotation.class);
                    print(testAnnotation);
                }
            }
        }
    }

    /**
     * 打印输出类TestAnnotation中的类注解
     * @throws Exception
     */
    public static void parseTypeAnnotation() throws Exception {
        Class clazz = UseAnnotation.class;
        Annotation[] annotations = clazz.getAnnotations();
        if(annotations.length > 0) {
            TestAnnotation testAnnotation = null;
            for(Annotation annotation : annotations) {
                testAnnotation = (TestAnnotation) annotation;
                print(testAnnotation);
            }
        }
    }

    /**
     * 打印输出类TestAnnotation中的构造方法注解
     * @throws Exception
     */
    public static void parseConstructorAnnotation() throws Exception {
        Class clazz = UseAnnotation.class;
        Constructor[] constructors = clazz.getConstructors();
        if(constructors.length > 0) {
            boolean hasAnnotation = false;
            TestAnnotation testAnnotation = null;
            for(Constructor constructor : constructors) {
                hasAnnotation = constructor.isAnnotationPresent(TestAnnotation.class);
                if(hasAnnotation) {
                    testAnnotation = (TestAnnotation) constructor.getAnnotation(TestAnnotation.class);
                    print(testAnnotation);
                }
            }
        }
    }

    /**
     * 打印输出TestAnnotation
     * @param testAnnotation
     */
    public static void print(TestAnnotation testAnnotation) {
        System.out.println("id = \"" + testAnnotation.id() + "\"; name = \""
                + testAnnotation.name() + "\"; gid = \"" + testAnnotation.gid() + "\"");
    }
}
