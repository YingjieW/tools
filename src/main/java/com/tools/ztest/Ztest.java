package com.tools.ztest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tools.utils.*;
import com.tools.ztest.javabeans.PersonDTO;
import com.tools.ztest.javabeans.PersonEntity;
import com.tools.ztest.reflect.enumtype.CommonType;
import com.tools.ztest.yop.entity.TestEntity;
import com.tools.ztest.yop.entity.YeepayProductEntity;
import com.yeepay.g3.utils.common.httpclient.SimpleHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ztest {

    public Ztest() {};

    public Ztest(String str) {
        logger.info("Ztest str : " + str);
    }

    private static final Logger logger = LoggerFactory.getLogger(Ztest.class);

    private static void scanner(String methodName) throws Exception {
        while(true) {
            System.out.print("Please enter something: ");
            Scanner scanner = new Scanner(System.in);
            String text = scanner.next();
            text.notify();
            if("quit".equalsIgnoreCase(text) || "exit".equalsIgnoreCase(text) || "q".equalsIgnoreCase(text)) {
                break;
            }
            Method method = Ztest.class.getDeclaredMethod(methodName, String.class);
            method.invoke(null, text);
        }
    }

    public static void main(String[] args) throws Throwable {
        String filePath = "/Users/YJ/Downloads/ttttt.jpeg";
        StringBuffer buffer = new StringBuffer();
        buffer.append("data:image/").append("jpeg").append(";base64,")
                .append(FileUtils.writeToBase64(new FileInputStream(new File(filePath))));
        System.out.println(buffer.toString());
    }

    private static void testkk(Integer i) {
        i = null;
    }
    private static void testOctHex() {
        int a = 011;
        System.out.println("===> a: " + a);
        int b = 0x11;
        System.out.println("===> b: " + b);
    }

    public static void testArrayCopy() throws Exception {
        int[] arr = {0, 1, 2, 3, 4, 5, 0};
        for(int i : arr) {
            System.out.print(i + ", ");
        }
        System.out.println();

        System.arraycopy(arr, 3, arr, 4, 3);
        for(int i : arr) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }

    public static void testLinkedHashMap() throws Exception {
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<Integer, String>(16, 0.75f, true);
        linkedHashMap.put(2, "two");
        linkedHashMap.put(3, "three");
        linkedHashMap.put(1, "one");
        for(Map.Entry<Integer, String> entry : linkedHashMap.entrySet()) {
            System.out.println("===> key:[" + entry.getKey() + "], value:[" + entry.getValue() + "].");
        }
        System.out.println();

        linkedHashMap.get(3);
        for(Map.Entry<Integer, String> entry : linkedHashMap.entrySet()) {
            System.out.println("===> key:[" + entry.getKey() + "], value:[" + entry.getValue() + "].");
        }
    }

    public static void testMethodReflect() throws Exception {
        // 利用反射调用静态方法时,无需指定具体的对象。
        Method method1 = Ztest.class.getDeclaredMethod("reflectStaticMethod", String.class);
        method1.invoke(null, "hello");

        // 利用反射调用非静态方法是,必须指定具体的对象,否则报错。
        Method method2 = Ztest.class.getDeclaredMethod("reflectNonStaticMethod", String.class);
        method2.invoke(null, "world"); // 该句会抛出NullPointerException.
    }

    public static void reflectStaticMethod(String text) {
        System.out.println("===> static.methcod...text: " + text);
    }

    public void reflectNonStaticMethod(String text) {
        System.out.println("===> non.static.method...text: " + text);
    }

    private static void testPayApiAmount() throws Exception {
        String amount = "10000000.09";
        double amountD = Double.valueOf(amount);
        amount = String.valueOf(amountD);
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{0,2})?");
        Matcher matcher = pattern.matcher(amount);
        System.out.println("amountD = " + amountD);
        System.out.println("amount = " + amount);
        if (!matcher.matches()) {
            System.out.println("参数[amount]格式不合法,小数点位数超限");
        } else {
            System.out.println("success");
        }
    }

    private static void testCalRefundFee() throws Exception {
        BigDecimal refundAmount = new BigDecimal("1");
        BigDecimal refundedAmount = new BigDecimal("250");
        BigDecimal tradeAmount = new BigDecimal("300");
        BigDecimal tradeFee = new BigDecimal("10");
        BigDecimal refundedFee = new BigDecimal("8.33");
        BigDecimal shouldRefundFee = BigDecimal.ZERO;
        BigDecimal fee = refundAmount.add(refundedAmount).divide(tradeAmount, 4, RoundingMode.HALF_UP).multiply(tradeFee).subtract(refundedFee).subtract(shouldRefundFee).setScale(2, RoundingMode.HALF_UP);
        print(fee.toString());
    }

    private static void testCollections() throws Exception {
        List<String> list1 = new ArrayList<String>(3);
        list1.add("1");
        list1.add("2");
        list1.add("3");
        List<String> list2 = new ArrayList<String>(list1);
        print("list1: " + JSON.toJSONString(list1));
        print("list2: " + JSON.toJSONString(list2));
        list1.add("AAA");
        print("list1: " + JSON.toJSONString(list1));
        print("list2: " + JSON.toJSONString(list2));
    }

    private static void testMap() throws Exception {
        Map<String, String> map = new HashMap<String, String>(99);
        print("map.size = " + map.size());
        map.put("key1", "value1");
        map.put("key2", null);
        print("map.get = " + map.get("key2"));
    }

    private static void testFinal(final String s) throws Exception {
        print(s);
    }

    private static void testCast() throws Exception {
        float l = 100.567f;
        int i = (int)l;
        print(i);
    }

    private static void testOctal() throws Exception {
        int nine = 017;
        System.out.println(nine);
    }

    private static void testBigDecimal() throws Exception {


        BigDecimal a = new BigDecimal(1.256);
        print(a.setScale(2, BigDecimal.ROUND_DOWN));
        BigDecimal b = new BigDecimal(0.003);
        print(b.setScale(2, BigDecimal.ROUND_DOWN));

        BigDecimal bigDecimal = new BigDecimal(1.987654321);
        String str1 = bigDecimal.toString();
        print(str1);
        String str2 = String.valueOf(bigDecimal.doubleValue());
        print(str2);
        String str3 = String.valueOf(bigDecimal);
        print(str3);

        BigDecimal b1 = new BigDecimal(1.2);
        BigDecimal b2 = new BigDecimal(1.1);
        print(b1.doubleValue());
        print(b1.add(b2).doubleValue());
        print(b1.doubleValue());

        BigDecimal b3 = new BigDecimal("0.01");
        print("scale:" + b3.scale());
        print("scale:" + BigDecimalUtils.reconstruct(b3).scale());

        BigDecimal b4 = BigDecimal.valueOf(0.03);
        print("b4 = " + b4);
    }

    private static void testAlibabaJson() throws Exception {
        List<PersonEntity> personEntityList = new ArrayList<PersonEntity>();
        PersonEntity personEntity = new PersonEntity();
        Map map = new HashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        personEntity.setMap(map);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setMap(personEntity.getMap());

        personEntity.setPersonDTO(personDTO);

        print("   " + JSON.toJSONString(personEntity));
    }

    private static void testSimpleHttpUtils() throws Exception {
        String textHost = "http://o2o.yeepay.com/zgt-api/api/queryOrder";
        String customernumber = "10012438801";
        String data = "CE2EF6D1E257DEDDF5D07DEBA716C3F5E5C31D225ED9B5DB6CFCF99814832925A4702CC5D6FD8BE4A7C139BF4BBCED7F303AF571DB6D5E4B66BABC852419B21E32615C2DBC6D4D9F0F7F3497546430E13E2F965B0FEA2BAA2A42C3D4597108A1CA2229B476E2DA62CC4A454980A913F8B001C335F57D1C80F170DB49D7D95FA6";

        Map<String, String> params = new HashMap<String, String>();
        params.put("customernumber", customernumber);
        params.put("data", data);

        print("   " + JSON.toJSONString(params));
        String reqString = SimpleHttpUtils.httpPost(textHost, params);
        print("   " + JSON.toJSONString(reqString));

    }

    private static void testString() throws Exception {

        String statuses = "INIT";
        List<String> list = Arrays.asList(statuses.split(","));
        print(JSON.toJSONString(list));

        String a = "hello";
        String b = a;
        b.toUpperCase();
        print("   a: " + a);
        print("   b: " + b);

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

        StringBuffer buffer = new StringBuffer();
        print("buffer.size = " + buffer.length());

        StringBuffer defaultMsg = new StringBuffer("清算中心未正常入账邮件监控：<br> \r\n");
        print("defaultMsg.length = " + defaultMsg.length());
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

        System.out.println("###   weakHashMap: " + JSON.toJSONString(weakHashMap));
        System.out.println("###   hashMap: " + JSON.toJSONString(hashMap));

        // remove去掉了HashMap中Entry元素对"a"的强引用
        hashMap.remove(k1);
        // 去掉k1对"a"的强引用
        k1 = null;

        System.gc();
        Thread.sleep(1000); // 等待gc的执行

        System.out.println("###   weakHashMap: " + JSON.toJSONString(weakHashMap));
        System.out.println("###   hashMap: " + JSON.toJSONString(hashMap));
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
