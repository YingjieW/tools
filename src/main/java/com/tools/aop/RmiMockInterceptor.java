package com.tools.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.tools.Ztest;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/27 下午5:26
 */
public class RmiMockInterceptor implements MethodInterceptor {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Ztest.class);

    private final String MOCK_CONFIG_SWITCH_SUFFIX= "-rmimock-switch";
    private final String MOCK_CONFIG_EXPRESSION_SUFFIX = "-rmimock-expression-map";

    private final String MOCK_PROPERTIES_FILE_PATH = "runtimecfg/rmimock.properties";
    private final String MOCK_EXPRESSION_PROPERTIES_FILE_PATH = "runtimecfg/rmimock-expression.properties";
    private final String BASE_CHARSET = "ISO-8859-1";
    private final String DEFAULT_CHARSET = "UTF-8";
    private final String PROPERTIES_FILE_SUFFIX = ".properties";
    private final String PROPERTIES_FILE_MOCK_FLAG = "rmimock";

    private final String START_TAG_JSON_CLASS = "JsonClass->";
    private final String START_TAG_JSON_BASE = "JsonBase->";
    private final String START_TAG_CUSTOM_CLASS = "CustomClass->";
    private final String START_TAG_ENUM = "Enum->";

    private final String EXPRESSION_SPLITTER = ":";
    private final String EXPRESSION_RANDOM_DIGITAL = "digital";
    private final String EXPRESSION_RANDOM_CHARACTER = "char";
    private final String EXPRESSION_VOID = "void";

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 1. 读取统一配置，检查mock开关是否已开
     * 2. 检查是否已配置runtimecfg/rmimock.properties
     * 3. 从统一配置读取mock表达式
     * 4. 如果有配置mock表达式，则解析表达式，并返回；否则直接调用methodInvocation.proceed()
     *
     * @param methodInvocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if(checkMockPropertiesFile() && checkConfigMockSwitch() ) {
            logger.info("RmiMockInterceptor intercepted class: {}, intercepted method: {}",
                    methodInvocation.getMethod().getDeclaringClass().getName(), methodInvocation.getMethod().getName());
            Map<String, String> expressionMap = getConfigMockExpressionMap();
            String expression = getMockExpression(expressionMap, methodInvocation);
            if(StringUtils.isNotBlank(expression)) {
                if(EXPRESSION_VOID.equalsIgnoreCase(expression)) {
                    logger.info("当返回类型为void时，return null.");
                    return null;
                }
                Object result = parseMockExpression(expression);
                logger.info("mock表达式解析结果 returnType: {}, returnValue: {}", result.getClass().getName(), JSON.toJSONString(result));
                return result;
            }
        }
        return methodInvocation.proceed();
    }

    /**
     * 读取统一配置，检查mock开关是否已开
     * @return
     */
    private boolean checkConfigMockSwitch() {
//        String appName = applicationContext.getApplicationName().substring(1);
//        String configMockSwitchKey = appName + MOCK_CONFIG_SWITCH_SUFFIX;
//        boolean result = ConfigurationConstants.getMockSwitch(configMockSwitchKey);
//        logger.info("configMockSwitchKey: {}, value: {}", configMockSwitchKey, result);
        boolean result = true;
        return result;
    }

    /**
     * 检查是否已配置runtimecfg/rmimock.properties
     * @return
     * @throws Throwable
     */
    private boolean checkMockPropertiesFile() throws Throwable {
        try {
            Map<String, String> map = loadProperties(MOCK_PROPERTIES_FILE_PATH);
            logger.info("propertiesFile content: {}", JSON.toJSONString(map));
            if(map != null && map.size() > 0 && map.containsKey(PROPERTIES_FILE_MOCK_FLAG)) {
                return Boolean.parseBoolean(map.get(PROPERTIES_FILE_MOCK_FLAG));
            }
            return false;
        } catch (MissingResourceException e) {
            logger.info("prpertiesFile not found.");
            return false;
        } catch (Throwable t) {
            throw t;
        }
    }

    /**
     * 读配置文件
     * @param filePath
     * @return
     * @throws UnsupportedEncodingException
     * @throws MissingResourceException
     */
    private Map<String, String> loadProperties(String filePath) throws UnsupportedEncodingException, MissingResourceException{
        if(StringUtils.isBlank(filePath)) {
            throw new RuntimeException("filePath must be specified.");
        }
        if(filePath.lastIndexOf(PROPERTIES_FILE_SUFFIX) != -1) {
            filePath = filePath.substring(0, filePath.lastIndexOf(PROPERTIES_FILE_SUFFIX));
        }
        logger.info("filePath: {}", filePath);
        Map<String, String> result = new HashMap<String, String>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(filePath);
        Set<String> keySet = resourceBundle.keySet();
        for(String key : keySet) {
            String value = resourceBundle.getString(key);
            String keyConverted = new String(key.getBytes(BASE_CHARSET), DEFAULT_CHARSET);
            String valueConverted = new String(value.getBytes(BASE_CHARSET), DEFAULT_CHARSET);
            result.put(keyConverted, valueConverted);
        }
        return result;
    }

    /**
     * 从统一配置读取mock表达式Map
     * @return
     */
    private Map<String, String> getConfigMockExpressionMap() throws Exception {
//        String appName = applicationContext.getApplicationName().substring(1);
//        String configMockExpressionMapKey = appName + MOCK_CONFIG_EXPRESSION_SUFFIX;
//        Map<String, String> result = ConfigurationConstants.getMockExpressionMap(configMockExpressionMapKey);
//        logger.info("configMockExpressionMapKey: {}, value: {}", configMockExpressionMapKey, JSON.toJSONString(result));
        Map<String, String> result = loadProperties(MOCK_EXPRESSION_PROPERTIES_FILE_PATH);
        logger.info("expressionMap: {}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 获取mock表达式
     * @param expressionMap
     * @param methodInvocation
     * @return
     */
    private String getMockExpression(Map<String, String> expressionMap, MethodInvocation methodInvocation) {
        if(expressionMap == null || expressionMap.size() == 0) {
            return null;
        }

        Method method = methodInvocation.getMethod();
        String methodName = method.getName();
        Class declaringClass = method.getDeclaringClass();

        String simpleClassName = declaringClass.getSimpleName();
        String fullClassName = declaringClass.getName();

        Class<?>[] paramTypeClass = method.getParameterTypes();
        StringBuffer sb = new StringBuffer();
        for(Class<?> clazz : paramTypeClass) {
            sb.append(clazz.getSimpleName()).append(",");
        }
        String paramType = sb.length() == 0 ? "" : sb.substring(0, sb.length()-1);
        String methodNameWithParamType = methodName + "(" + paramType + ")";

        List<String> names = new ArrayList<String>(4);
        names.add(simpleClassName + "." + methodName);
        names.add(simpleClassName + "." + methodNameWithParamType);
        names.add(fullClassName + "." + methodName);
        names.add(fullClassName + "." + methodNameWithParamType);

        for(String name : names) {
            if(expressionMap.containsKey(name)) {
                return expressionMap.get(name);
            }
        }

        return null;
    }

    /**
     * 解析mock表达式
     * @param expression
     * @return
     * @throws Exception
     */
    private Object parseMockExpression(String expression) throws Exception {
        logger.info("mock表达式expression : " + expression);
        if(expression.startsWith(START_TAG_JSON_CLASS)) {
            return parseJsonClassExpresssion(expression);
        } else if (expression.startsWith(START_TAG_JSON_BASE)) {
            return parseJsonBaseExpression(expression);
        } else if(expression.startsWith(START_TAG_CUSTOM_CLASS)) {
            return parseCustomClassExpression(expression);
        } else if (expression.startsWith(START_TAG_ENUM)) {
            return parseEnumExpression(expression);
        } else {
            throw new RuntimeException("Expression is invalid.");
        }
    }

    /**
     * 解析json表达式：获取对象
     * @param expression
     * @return
     * @throws ClassNotFoundException
     */
    public Object parseJsonClassExpresssion(String expression) throws ClassNotFoundException {
        String className = expression.substring(START_TAG_JSON_CLASS.length(), expression.indexOf(EXPRESSION_SPLITTER));
        String jsonExpression = expression.substring(expression.indexOf(EXPRESSION_SPLITTER)+1);
        Class clazz = Class.forName(className);
        // 该行代码是为了解决反序列化时生成ASM出错的问题
        ParserConfig.getGlobalInstance().setAsmEnable(false);
        Object result = JSON.parseObject(jsonExpression, clazz);
        return result;
    }

    /**
     * 解析json表达式：获取常见的数据类型
     * @param expression
     * @return
     * @throws ClassNotFoundException
     */
    public Object parseJsonBaseExpression(String expression) throws ClassNotFoundException {
        String typeName = expression.substring(START_TAG_JSON_BASE.length(), expression.indexOf(EXPRESSION_SPLITTER));
        String jsonExpression = expression.substring(expression.indexOf(EXPRESSION_SPLITTER)+1);
        Object result = CommonType.getValue(typeName, jsonExpression);
        return result;
    }

    /**
     * 解析自定义表达式：获取对象，支持随机生成对象String类型属性的值。
     * @param expression
     * @return
     * @throws Exception
     */
    public Object parseCustomClassExpression(String expression) throws Exception {
        String className = expression.substring(START_TAG_CUSTOM_CLASS.length(), expression.indexOf(EXPRESSION_SPLITTER));
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
            Method targetMethod = clazz.getDeclaredMethod(tmpMethodName, CommonType.parseType(exprs[1]).getCommonClass());
            targetMethod.invoke(object, tmpArg);
        }
        return object;
    }

    /**
     * 解析枚举类型表达式
     * @param expression
     * @return
     * @throws ClassNotFoundException
     */
    public Object parseEnumExpression(String expression) throws ClassNotFoundException {
        String enumClassName = expression.substring(START_TAG_ENUM.length(), expression.indexOf(EXPRESSION_SPLITTER));
        String enumValue = expression.substring(expression.indexOf(EXPRESSION_SPLITTER) + 1);
        Class enumType = Class.forName(enumClassName);
        return Enum.valueOf(enumType, enumValue);
    }

    /**
     * 获取长度为lengthStr，类型为type的随机字符串
     * @param type
     * @param lengthStr
     * @return
     */
    private String getRandomStringValue(String type, String lengthStr) {
        if(StringUtils.isEmpty(lengthStr)) {
            throw new RuntimeException("lengthStr must be specified.");
        }
        int length = Integer.parseInt(lengthStr);
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        if(StringUtils.isBlank(type) || type.equals(EXPRESSION_RANDOM_CHARACTER)) {
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
        } else if(type.equals(EXPRESSION_RANDOM_DIGITAL)) {
            for(int i = 0; i < length; i++) {
                result.append(random.nextInt(10));
            }
            return result.toString();
        } else {
            throw new IllegalArgumentException(type + " is illegal.");
        }
    }

    /**
     * 常见的数据结构
     */
    private enum CommonType {
        // primitive datatype
        PRIMITIVE_BOOLEAN(new String[] {"boolean"}),
        PRIMITIVE_INT(new String[] {"int"}),
        PRIMITIVE_LONG(new String[] {"long"}),
        PRIMITIVE_FLOAT(new String[] {"float"}),
        PRIMITIVE_DOUBLE(new String[] {"double"}),

        // reference datatype
        REFERENCE_BOOLEAN(new String[] {"java.lang.Boolean", "Boolean"}),
        REFERENCE_INTEGER(new String[] {"java.lang.Integer", "Integer"}),
        REFERENCE_LONG(new String[] {"java.lang.Long", "Long"}),
        REFERENCE_FLOAT(new String[] {"java.lang.Float", "Float"}),
        REFERENCE_DOUBLE(new String[] {"java.lang.Double", "Double"}),

        STRING(new String[] {"java.lang.String", "String"}),
        BIGDECIMAL(new String[] {"java.math.BigDecimal", "BigDecimal"}),

        MAP(new String[] {"java.util.Map", "Map"}),
        HASHMAP(new String[] {"java.util.HashMap", "HashMap"}),
        TREEMAP(new String[] {"java.util.TreeMap", "TreeMap"}),
        LIST(new String[] {"java.util.List", "List"}),
        ARRAYLIST(new String[] {"java.util.ArrayList", "ArrayList"}),
        LINKEDLIST(new String[] {"java.util.LinkedList", "LinkedList"});

        private String[] names;

        private CommonType(String[] names) {
            this.names = names;
        }

        public Class getCommonClass() throws ClassNotFoundException {
            switch (this) {
                case PRIMITIVE_INT:
                    return int.class;
                case PRIMITIVE_LONG:
                    return long.class;
                case PRIMITIVE_FLOAT:
                    return float.class;
                case PRIMITIVE_DOUBLE:
                    return double.class;
                case PRIMITIVE_BOOLEAN:
                    return boolean.class;
                default:
                    return Class.forName(this.names[0]);
            }
        }

        public static CommonType parseType(String name) {
            for (CommonType commonType : CommonType.values()) {
                for (String tname : commonType.names) {
                    if (tname.equals(name)) {
                        return commonType;
                    }
                }
            }
            return null;
        }

        public static CommonType parseType(@SuppressWarnings("rawtypes") Class clz) {
            if (clz == null) {
                return null;
            }
            return parseType(clz.getName());
        }

        public static Object getValue(String typeName, String value)  {
            CommonType commonType = CommonType.parseType(typeName);
            switch (commonType) {
                case STRING:
                    return value;
                case REFERENCE_INTEGER:
                    return new Integer(value);
                case PRIMITIVE_INT:
                    return Integer.parseInt(value);
                case REFERENCE_LONG:
                    return new Long(value);
                case PRIMITIVE_LONG:
                    return Long.parseLong(value);
                case REFERENCE_FLOAT:
                    return new Float(value);
                case PRIMITIVE_FLOAT:
                    return Float.parseFloat(value);
                case REFERENCE_DOUBLE:
                    return new Double(value);
                case PRIMITIVE_DOUBLE:
                    return Double.parseDouble(value);
                case REFERENCE_BOOLEAN:
                    return new Boolean(value);
                case PRIMITIVE_BOOLEAN:
                    return Boolean.parseBoolean(value);
                case BIGDECIMAL:
                    return new BigDecimal(value);
                case MAP:
                    return JSON.parseObject(value, new TypeReference<Map>() {});
                case HASHMAP:
                    return JSON.parseObject(value, new TypeReference<HashMap>() {});
                case TREEMAP:
                    return JSON.parseObject(value, new TypeReference<TreeMap>() {});
                case LIST:
                    return JSON.parseObject(value, new TypeReference<List>() {});
                case ARRAYLIST:
                    return JSON.parseObject(value, new TypeReference<ArrayList>() {});
                case LINKEDLIST:
                    return JSON.parseObject(value, new TypeReference<LinkedList>() {});
                default:
                    throw new RuntimeException("Unsupport CommonType : " + commonType);
            }
        }
    }
}

