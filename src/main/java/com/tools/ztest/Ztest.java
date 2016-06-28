package com.tools.ztest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tools.utils.BeanUtils;
import com.tools.utils.PropertiesFileUtils;
import com.tools.utils.ThreadSafeDateUtils;
import com.tools.ztest.javabeans.PersonDTO;
import com.tools.ztest.javabeans.PersonEntity;
import com.tools.ztest.reflect.enumtype.CommonType;
import com.tools.ztest.yop.entity.TestEntity;
import com.tools.ztest.yop.entity.YeepayProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Ztest {

    public Ztest() {};

    public Ztest(String str) {
        logger.info("Ztest str : " + str);
    }

    private static final Logger logger = LoggerFactory.getLogger(Ztest.class);

    private static void scanner() {
        while(true) {
            System.out.print("Please enter something: ");
            Scanner scanner = new Scanner(System.in);
            String text = scanner.next();
            if("quit".equalsIgnoreCase(text) || "exit".equalsIgnoreCase(text)) {
                break;
            }
            test03(text);
        }
    }

    public static void main(String[] args) throws Throwable {
//        testJavaBean();
//        testPropertyCopy();
//        testString();
    }

    private static void testString() throws Exception {
        String text = "银行订单不可重复使用sdnsofn";
        print("   : " + text.indexOf("1银行订单不可重复使用"));
    }

    private static void testJavaBean() throws Throwable {

        Class clazz = PersonEntity.class;
        PersonEntity personEntity = new PersonEntity("ZhangSan", 13);
        PersonDTO personDTO = new PersonDTO("personDTO", 99);
//        personEntity.setPersonDTO(personDTO);
        print("   personEntity: " + JSON.toJSONString(personEntity));

        String propertyName = "name";
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, clazz);
        print("   propertyType: " + propertyDescriptor.getPropertyType().getSimpleName());
        print("   propertyType: " + (propertyDescriptor.getPropertyType() == int.class));

        // 获取属性值
        Method getMethod = propertyDescriptor.getReadMethod();
        print("   invoke getMethod: " + getMethod.invoke(personEntity));

        // 给属性赋值
        Object nameValue = "LiSi";
        Method setMethod = propertyDescriptor.getWriteMethod();
        setMethod.invoke(personEntity, nameValue);
        print("   personEntity: " + JSON.toJSONString(personEntity));

        // 以下使用BeanInfo操作javabean
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor propertyDescriptor1 : propertyDescriptors) {
//            print("      * " + JSON.toJSONString(propertyDescriptor1));
            print("      - " + propertyDescriptor1.getName());
        }
    }

    // BeanUtils.copyProperties()为浅拷贝，有可能引发一些潜在的问题
    private static void testPropertyCopy() throws Exception {
        PersonEntity source = new PersonEntity("ZhangSan", 23);

        Integer aaa = 18;

        Float score = 89.5f;

        BigDecimal bigDecimal = new BigDecimal("99.5");

        PersonDTO personDTO = new PersonDTO("personDTO", 99);

        Map map = new HashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");

        List list = new LinkedList();
        list.add("element0");
        list.add("element1");
        list.add(personDTO);

        LinkedList linkedList = new LinkedList();
        linkedList.add("linkedList0");
        linkedList.add("linkedList1");

        HashSet hashSet = new HashSet();
        hashSet.add("hashSet0");
        hashSet.add("hashSet1");

        TreeSet treeSet = new TreeSet();
        treeSet.add("treeSet0");
        treeSet.add("treeSet1");

        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("hkey0", "hvalue0");
        hashMap.put("hkey1", "hvalue1");

        TreeMap treeMap = new TreeMap<String, PersonDTO>();
        PersonDTO personDTO0 = new PersonDTO("treeMap0", 0);
        PersonDTO personDTO1 = new PersonDTO("treeMap1", 1);
        treeMap.put("tkey0", personDTO0);
        treeMap.put("tkey1", personDTO1);

        CommonType commonType = CommonType.BIGDECIMAL;

        source.setAaa(aaa);
        source.setScore(score);
        source.setBigDecimal(bigDecimal);
//        source.setPersonDTO(personDTO);
//        source.setMap(map);
        source.setList(list);
        source.setLinkedList(linkedList);
        source.setHashSet(hashSet);
        source.setTreeSet(treeSet);
        source.setHashMap(hashMap);
        source.setTreeMap(treeMap);
        source.setCommonType(commonType);

        PersonDTO target = new PersonDTO();

//        BeanUtils.copyProperties(source, target);
        BeanUtils.deepCopyProperties(source, target);

        personDTO.setName("sjdfsf");
        list.add("element2");
        linkedList.add("linkedList2");
        hashSet.add("hashSet2");
        treeSet.add("treeSet2");
        hashMap.put("hkey0", "00000");
        hashMap.put("hkey2", "hvalue2");
        personDTO0.setAaa(9999);

        print("   source: " + JSON.toJSONString(source));
        print("   target: " + JSON.toJSONString(target));
    }

    private static void testDate() throws Exception {
        Date date1 = new Date();
        Thread.sleep(10);
        Date date2 = new Date();
        print("date1: " + ThreadSafeDateUtils.formatDateTimeMillis(date1));
        print("date2: " + ThreadSafeDateUtils.formatDateTimeMillis(date2));
        print(date1.before(date2));
    }

    // 建议使用split()方法替代StringTokenizer
    private static void testStringTokenizer() throws Exception {
        StringTokenizer stringTokenizer = new StringTokenizer("This is a .  test.", ".");
        while(stringTokenizer.hasMoreTokens()) {
            print(stringTokenizer.nextToken());
        }
    }

    private static void testInteger() throws Exception {
        Integer integer = null;
        int i = 1;
        logger.info("integer = " + integer);
        YeepayProductEntity yeepayProductEntity = new YeepayProductEntity();
        yeepayProductEntity.setYeepayProductName("testName");
        logger.info("yeepayProductEntity: " + JSON.toJSONString(yeepayProductEntity));
        yeepayProductEntity.setAssurePeriodInt(3);
        logger.info("yeepayProductEntity: " + JSON.toJSONString(yeepayProductEntity));
        logger.info("i = " + i);
        if(i == integer) {
            logger.info("true");
        } else {
            logger.info("false");
        }
    }

    private static void testStringBuffer() throws Exception {
        String[] test = new String[0];
        logger.info("length: " + test.length);
        StringBuffer sb = new StringBuffer();
        logger.info("sb.length: " + sb.length());
        logger.info("sb: [" + sb.toString() + "]");
        sb.append("args1").append(",").append("args2").append(",");
        logger.info("sb: [" + sb.toString() + "]");
        sb.substring(0, sb.length()-1);
        logger.info("sb: [" + sb.toString() + "]");
        logger.info("sb: [" + sb.substring(0, sb.length()-1).toString() + "]");
    }

    private static void testSplit() throws Exception {
        String text = "1^2^^3";
        String[] result = text.split("\\^");
        logger.info("length : " + result.length);
        for(String s : result) {
            logger.info("- " + s);
        }
    }

    private static void testJson() throws Exception {
        YeepayProductEntity y1 = new YeepayProductEntity();
        y1.setFeeRate(new BigDecimal(213.5));
        y1.setYeepayProductName("name01");
        y1.setFeeRateChargeMethod("method01");
        List<String> list1 = new ArrayList<String>();
        list1.add("l1");
        list1.add("l2");
        list1.add("l3");
        y1.setTestList(list1);
        TestEntity testEntity = new TestEntity();
        testEntity.setTestName("Tom");
        testEntity.setTestAge(19);
        testEntity.setTestScore(98f);
        y1.setTestEntity(testEntity);
        String y1Json = JSON.toJSONString(y1);
        logger.info(y1Json);

        YeepayProductEntity y1Parsed = JSON.parseObject(y1Json, YeepayProductEntity.class);
        logger.info("... " + y1Parsed.getFeeRateChargeMethod());
        logger.info("... " + y1Parsed.getFeeRate());
        logger.info("... " + y1Parsed.getTestEntity());

        int i = 10;
        logger.info(JSON.toJSONString(i));
        float f = 8.9f;
        logger.info(JSON.toJSONString(f));
        boolean b = true;
        logger.info(JSON.toJSONString(b));
        boolean c = Boolean.parseBoolean(JSON.toJSONString(b));
        logger.info("" + c);
    }

    private static void testPropertiesUtil() throws Exception{
        String uri = "test/test1.properties";
        Map<String, String> result = PropertiesFileUtils.loadProps(uri);
        logger.info("###   result.size: " + result.size());
        logger.info("###   result: " + JSON.toJSONString(result));
    }

    /**
     * WeakHashMap，此种Map的特点是，当除了自身有对key的引用外，此key没有其他引用那么此map会自动丢弃此值
     * @throws Exception
     */
    private static void testWeakHashMap() throws Exception {

        String k1 = new String("a");
        String k2 = new String("b");

        WeakHashMap<String, String> weakHashMap = new WeakHashMap<String, String>();
        weakHashMap.put(k1, "111");
        weakHashMap.put(k2, "222");

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(k1, "aaa");
        hashMap.put(k2, "bbb");

        logger.info("###   weakHashMap: " + JSON.toJSONString(weakHashMap));
        logger.info("###   hashMap: " + JSON.toJSONString(hashMap));

        hashMap.remove(k1);
        k1 = null;
        //k2 = null;
        System.gc();

        logger.info("###   weakHashMap: " + JSON.toJSONString(weakHashMap));
        logger.info("###   hashMap: " + JSON.toJSONString(hashMap));
    }

    private static void testGetConstructor() throws Exception {
        Constructor<?>[] constructors = Ztest.class.getConstructors();
        logger.info("###   constructors: " + JSON.toJSONString(constructors));
        Constructor<?> constructor = Ztest.class.getConstructor(String.class);
        logger.info("###   constructor: " + JSON.toJSONString(constructor));
    }

    private static void testBoolean() throws Exception {
        String str = "true";
        Boolean b1 = new Boolean(str);
        logger.info("###   b1 : " + b1.toString());
        boolean b2 = Boolean.parseBoolean(str);
        logger.info("###   b2: " + b2);
    }

    private static void testPraseJson() throws Exception {
        String jsonStr1 = "[99.9,98,\"test\"]";
        List<?> list1 = JSON.parseObject(jsonStr1, new TypeReference<List<?>>() {});
        logger.info("###   " + list1.get(0).getClass().getName());
        logger.info("###   " + list1.get(1).getClass().getName());
        logger.info("###   " + list1.get(2).getClass().getName());
        String jsonStr2 = "[{\"ka\":\"va\",\"kb\":\"vb\",\"kc\":\"vc\"}]";
        List<Map<?, ?>> list2 = JSON.parseObject(jsonStr2, new TypeReference<List<Map<?, ?>>>() {});
        logger.info("###   " + list2.get(0).getClass().getName());
        String className = "java.util.List";
        Class<?> clazz = Class.forName(className);
        logger.info("###   " + clazz.getName());
    }

    private static void testJsonString() throws Exception {
        HashMap<String, String> mapString = new HashMap<String, String>();
        mapString.put("k1", "v1");
        mapString.put("k2", "v2");
        logger.info("###   mapString: " + JSON.toJSONString(mapString));
        List<String> listString = new ArrayList<String>();
        listString.add("t1");
        listString.add("t2");
        logger.info("###   listString: " + JSON.toJSONString(listString));
        HashMap<String, Integer> mapInteger = new HashMap<String, Integer>();
        mapInteger.put("k1", 1);
        mapInteger.put("k2", 2);
        logger.info("###   mapInteger: " + JSON.toJSONString(mapInteger));
        List<Integer> listInteger = new ArrayList<Integer>();
        listInteger.add(99);
        listInteger.add(98);
        listInteger.add(97);
        logger.info("###   listInteger: " + JSON.toJSONString(listInteger));
        List<Map<String, String>> listMapString = new ArrayList<Map<String, String>>();
        Map<String, String> tmpMap01 = new HashMap<String, String>();
        tmpMap01.put("ka", "va");
        tmpMap01.put("kb", "vb");
        tmpMap01.put("kc", "vc");
        listMapString.add(tmpMap01);
        logger.info("###   listMapString: " + JSON.toJSONString(listMapString));
        Map<String, List<Integer>> mapListInteger = new HashMap<String, List<Integer>>();
        List<Integer> mapList1 = new ArrayList<Integer>();
        mapList1.add(99);
        mapList1.add(98);
        mapList1.add(97);
        List<Integer> mapList2 = new ArrayList<Integer>();
        mapList2.add(66);
        mapList2.add(65);
        mapList2.add(64);
        mapListInteger.put("Fisrt", mapList1);
        mapListInteger.put("Last", mapList2);
        logger.info("###   mapListInteger: " + JSON.toJSONString(mapListInteger));
    }
    private static void testProperty() {
        System.setProperty("mockconfig", "mockconfig_test");
        System.out.println("###  " + System.getProperty("mockconfig"));
        System.out.println("###  " + System.getProperty("java.version"));
    }

    private static void testExpression() throws Exception {
        String startLabel = "->";
        String expression = "->com.tools.ztest.yop.entity.YeepayProductEntity:" +
                "yeepayProductName^String^WECHAT_USM|" +
                "feeRate^BigDecimal^123.45|" +
                "testMap^Map^{\"k1\":\"v1\",\"k2\":\"v2\"}|" +
                "testList^List^[\"t1\",\"t2\"]|" +
                "testMapInteger^Map^{\"k1\":1,\"k2\":2}";
        if(expression.startsWith(startLabel)) {
            String className = expression.substring(2, expression.indexOf(":"));
            logger.info("###   className: " + className);
        }
    }

    private static void testMapPutAll() {
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("A", "111");
        map1.put("B", "222");
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("B", "bbb");
        map2.put("C", "333");
        System.out.println("map1: " + JSON.toJSONString(map1));
        System.out.println("map2: " + JSON.toJSONString(map2));
        map1.putAll(map2);
        System.out.println("putAll: " + JSON.toJSONString(map1));
    }

    private static void test03(String text) {
        String regex = "[YyNn]";
        System.out.println("###  regex: " + regex);
        System.out.println("###  text: " + text);
        System.out.println("###  result: " + Pattern.matches(regex, text));
    }

    private static void test02() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(ThreadSafeDateUtils.formatDate(date));
        System.out.println(ThreadSafeDateUtils.formatDateTime(date));
        SimpleDateFormat simpleDateFormat= ThreadSafeDateUtils.getDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss.sss");
        System.out.println(simpleDateFormat.format(date));
    }

    private static void test01() {
        int a = 0;
        int b = 1;
        if(a == 0) {
            System.out.println("Test");
            b = a + 1;
            a = 9;
        }
    }

    private static void print(Object text) {
        print(text, 0);
    }

    private static void print(Object text, int selfDefine) {
        if(0 == selfDefine) {
            System.out.println("#####  " + text);
        } else if(1 == selfDefine) {
            System.out.println(text);
        }
    }
}
