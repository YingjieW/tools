package com.tools.utils;

import com.alibaba.fastjson.JSON;
import com.tools.enumtype.CommonType;
import com.tools.enumtype.StartTag;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/26 上午11:32
 */
public class ExpressionUtils {

    private static Logger logger = LoggerFactory.getLogger(ExpressionUtils.class);

    private static final String EXPRESSION_SPLITTER = ":";
    private static final String DIGITAL = "digital";
    private static final String CHARACTER = "char";
    private static final int DEFAULT_LENGTH = 11;

    public static Object parseJsonBaseExpression(String expression) throws Throwable {
        logger.info("expression : " + expression);
        String typeName = expression.substring(StartTag.JSON_BASE.getTag().length(), expression.indexOf(EXPRESSION_SPLITTER));
        String jsonExpression = expression.substring(expression.indexOf(EXPRESSION_SPLITTER)+1);
        try {
            Object result = CommonType.getValue(typeName, jsonExpression);
            return result;
        } catch (Throwable t) {
            throw t;
        }
    }

    public static Object parserJsonClassExpresssion(String expression) throws Throwable {
        logger.info("expression : " + expression);
        String className = expression.substring(StartTag.JSON_CLASS.getTag().length(), expression.indexOf(EXPRESSION_SPLITTER));
        String jsonExpression = expression.substring(expression.indexOf(EXPRESSION_SPLITTER)+1);
        try {
            Class clazz = Class.forName(className);
            Object result = JSON.parseObject(jsonExpression, clazz);
            return result;
        } catch (Throwable t) {
            throw t;
        }
    }

    // 可定制
    public static Object parseCustomClassExpression(String expression) throws Throwable {
        logger.info("expression : " + expression);
        try {
            String className = expression.substring(StartTag.CUSTOM_CLASS.getTag().length(), expression.indexOf(EXPRESSION_SPLITTER));
            Class<?> clazz = Class.forName(className);
            Object object = clazz.newInstance();

            String[] paramExpressions = expression.substring(expression.indexOf(EXPRESSION_SPLITTER) + 1, expression.length()).split("\\|");
            for (String paramExpression : paramExpressions) {
                String[] exprs = paramExpression.split("\\^");
                Object tmpArg = null;
                if(exprs.length == 3) {
                    tmpArg = CommonType.getValue(exprs[1], exprs[2]);
                } else if(exprs.length == 4) {
                    tmpArg = getRandomStringValue(exprs[2], exprs[3]);
                } else {
                    throw new IllegalArgumentException(JSON.toJSONString(exprs));
                }
                StringBuffer sb = new StringBuffer(exprs[0]);
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                String tmpMethodName = "set" + sb.toString();
                try {
                    Method targetMethod = clazz.getDeclaredMethod(tmpMethodName, CommonType.parseType(exprs[1]).getCommonClass());
                    targetMethod.invoke(object, tmpArg);
                } catch (Throwable e) {
                    throw e;
                }
            }
            return object;
        } catch (Throwable t) {
            throw t;
        }
    }

    private static String getRandomStringValue(String type, String lengthStr) {
        int length = DEFAULT_LENGTH;
        if(StringUtils.isNotEmpty(lengthStr)) {
            length = Integer.parseInt(lengthStr);
        }
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        if(StringUtils.isBlank(type) || type.equals(CHARACTER)) {
            for (int i = 0; i < length; i++) {
                boolean isChar = (random.nextInt(2) % 2 == 0);
                if (isChar) {
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    result.append((char) (choice + random.nextInt(26)));
                } else {
                    result.append(Integer.toString(random.nextInt(10)));
                }
            }
            return result.toString();
        } else if(type.equals(DIGITAL)) {
            for(int i = 0; i < length; i++) {
                result.append(random.nextInt(10));
            }
            return result.toString();
        } else {
            throw new IllegalArgumentException(type + " is illegal.");
        }
    }
}
