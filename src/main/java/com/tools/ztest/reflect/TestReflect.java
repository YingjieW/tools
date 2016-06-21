package com.tools.ztest.reflect;

import com.alibaba.fastjson.JSON;
import com.tools.ztest.reflect.enumtype.CommonType;
import com.tools.ztest.yop.entity.YeepayProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/20 上午11:42
 */
public class TestReflect {

    private static final Logger logger = LoggerFactory.getLogger(TestReflect.class);

    private static final String START_MARK_CLASS = "Class->";
    private static final String START_MARK_JSON = "Json->";
    private static final String START_MARK_SPLITTER = ":";

    public static void main(String[] args) throws Throwable {
        testMethodName();
    }

    private static void testMethodName() throws Exception {
        Class clazz = TestReflect.class;
        Method method = clazz.getDeclaredMethod("testMethod1", String.class, String.class);
//        Method method = clazz.getDeclaredMethod("testMethod2");
//        Method method = clazz.getDeclaredMethod("testMethod4", YeepayProductEntity.class);
//        Method method = clazz.getDeclaredMethod("testMethod5", Map.class);
        logger.info("###   fullName: " + method.getName());
        logger.info("###   className: " + method.getClass().getName());
        logger.info("###   genericParameterTypes: " + JSON.toJSONString(method.getGenericParameterTypes()));
        logger.info("###   parameterTypes: ");
        for(Class<?> c : method.getParameterTypes()) {
            logger.info("        " + c.getSimpleName());
        }
        logger.info("###   returnType: " + method.getReturnType().getName());
        logger.info("###   returnType: " + method.getReturnType().getSimpleName());
    }

    private static void test03() throws Throwable {
        String name = "java.util.HashMap";
        Class<?> clazz = Class.forName(name);
        logger.info(clazz.getName());
        String str = "java.lang.Integer";
        Class<?> strClazz = Class.forName(str);
        logger.info("" + strClazz.equals(Integer.class));
        logger.info("" + int.class.getName());
    }

    private static void testParseExpression() throws Throwable {
        String classExpression = "Class->com.tools.ztest.yop.entity.YeepayProductEntity:" +
                "yeepayProductName^String^WECHAT_USM|" +
                "feeRate^BigDecimal^123.45|" +
                "testMap^Map^{\"k1\":\"v1\",\"k2\":\"v2\"}|" +
                "testList^List^[\"t1\",\"t2\"]|" +
                "testMapInteger^Map^{\"k1\":1,\"k2\":2}";
        String jsonExpression = "";
    }

    private static Object parseJsonExpression(String expression) throws Throwable {
        try {
            String typeName = expression.substring(START_MARK_JSON.length(), expression.indexOf(START_MARK_SPLITTER));
            String jsonExpression = expression.substring(expression.indexOf(START_MARK_SPLITTER)+1);
            Object result = CommonType.getValue(typeName, jsonExpression);
            return result;
        } catch (Throwable t) {
            throw t;
        }
    }

    private static Object parseClassExpression(String expression) throws Throwable {
        try {
            String className = expression.substring(START_MARK_CLASS.length(), expression.indexOf(START_MARK_SPLITTER));
            Class<?> clazz = Class.forName(className);
            Object object = clazz.newInstance();

            String[] paramExpressions = expression.substring(expression.indexOf(START_MARK_SPLITTER) + 1, expression.length()).split("\\|");
            for (String paramExpression : paramExpressions) {
                String[] exprs = paramExpression.split("\\^");
                if(exprs.length != 3) {
                    throw new IllegalArgumentException(JSON.toJSONString(exprs));
                }
                StringBuffer sb = new StringBuffer(exprs[0]);
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                String tmpMethodName = "set" + sb.toString();
                Class<?> tmpClazz = Class.forName(CommonType.parseType(exprs[1]).getClassName());
                try {
                    Method targetMethod = clazz.getDeclaredMethod(tmpMethodName, tmpClazz);
                    targetMethod.invoke(object, CommonType.getValue(exprs[1], exprs[2]));
                } catch (NoSuchMethodException e) {
                    Class<?> tmpClazzz = null;
                    if(tmpClazz.equals(Integer.class)) {
                        tmpClazzz = int.class;
                    } else if(tmpClazz.equals(Long.class)) {
                        tmpClazzz = long.class;
                    } else if(tmpClazz.equals(Float.class)) {
                        tmpClazzz = float.class;
                    } else if(tmpClazz.equals(Double.class)) {
                        tmpClazzz = double.class;
                    } else if(tmpClazz.equals(Boolean.class)) {
                        tmpClazzz = boolean.class;
                    }
                    try {
                        Method targetMethod = clazz.getDeclaredMethod(tmpMethodName, tmpClazzz);
                        targetMethod.invoke(object, CommonType.getValue(exprs[1], exprs[2]));
                    } catch (Throwable t) {
                        logger.error("!!!! Exceptioin.aaa", t);
                        throw t;
                    }
                } catch (Throwable e) {
                    logger.error("!!!   paramExpressions: " + JSON.toJSONString(paramExpressions));
                    logger.error("!!!   exprs: " + JSON.toJSONString(exprs));
                    logger.error("!!!   Exception.kkk", e);
                    throw e;
                }
            }
            return object;
        } catch (Throwable t) {
            throw t;
        }
    }

    private static void testReflect01() throws Exception {
        Method m = TestReflect.class.getDeclaredMethod("testMethod", String.class, String.class);
        Class<?>[] paramTypes = m.getParameterTypes();
        for(Class<?> pt : paramTypes) {
            System.out.println(pt.getName());
        }
        Class c = String.class;
        System.out.println("getSimpleName : " + c.getSimpleName());
        System.out.println("getName : " + c.getName());
    }

    private static void testMethod1(String name, String pwd) {
        System.out.println("name : " + name + ",  pwd : " + pwd);
    }

    private static YeepayProductEntity testMethod2() {
        YeepayProductEntity yeepayProductEntity = new YeepayProductEntity();
        return yeepayProductEntity;
    }

    private static String testMethod3() {
        return "testMethod3";
    }

    private static void testMethod4(YeepayProductEntity yeepayProductEntity) {}

    private static Map<String, String> testMethod5(Map<String, String> map) {
        return map;
    }
}
