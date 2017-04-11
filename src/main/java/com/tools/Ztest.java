package com.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.tools.util.BeanUtils;
import com.tools.util.SerializeUtils;
import com.tools.ztest.javabeans.PersonDTO;
import com.tools.ztest.javabeans.PersonEntity;
import com.tools.ztest.reflect.enumtype.CommonType;
import com.tools.ztest.test.Animal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
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
        testFixedThreadPool();
    }

    private static void testFixedThreadPool() throws Throwable {
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("0");
        Executors.newFixedThreadPool(3).execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!queue.isEmpty()) {
                        String text = queue.poll();
                        if (text != null) {
                            System.out.println("---> text : " + text);
                        } else {
                            System.out.println("............");
                        }
                    } else {
                        try {
                            Thread.sleep(2 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        System.out.println("...end-pool");

        new Thread(new Runnable() {
            @Override
            public void run() {
                queue.add("1");
                queue.add("2");
            }
        }).start();

        System.out.println("...end-thread");
    }

    private static void testLeftShift() throws Exception {
        long workerIdBits = 5L;
        long dataCenterIdBits = 5L;

        long maxWorkerId = -1L ^ (-1L << workerIdBits);
        long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

        System.out.println(workerIdBits);
        System.out.println(dataCenterIdBits);
        System.out.println(maxWorkerId);
        System.out.println(maxDataCenterId);

        System.out.println((-1 << 5));
        System.out.println(-1 ^ (-1 << 5));
        System.out.println((-1 << 5)*-1 - 1);
    }



    private static void testRoundingMode() throws Exception {
        BigDecimal a = new BigDecimal(2345678);//, 2, RoundingMode.DOWN
        BigDecimal b = new BigDecimal("0.000001");
        System.out.println(a.multiply(b).setScale(2, RoundingMode.DOWN) + "  -  a:[" + a.toString() + "], b:[" + b.toString() + "].");
        BigDecimal c = new BigDecimal("0.00001");
        System.out.println(a.multiply(c).setScale(2, RoundingMode.DOWN) + "  -  a:[" + a.toString() + "], c:[" + c.toString() + "].");
        BigDecimal d = new BigDecimal("0.0001");
        System.out.println(a.multiply(d).setScale(2, RoundingMode.DOWN) + "  -  a:[" + a.toString() + "], d:[" + d.toString() + "].");
        BigDecimal e = new BigDecimal("0.001");
        System.out.println(a.multiply(e).setScale(2, RoundingMode.DOWN) + "  -  a:[" + a.toString() + "], e:[" + e.toString() + "].");
        BigDecimal f = new BigDecimal("0.01");
        System.out.println(a.multiply(f).setScale(2, RoundingMode.DOWN) + "  -  a:[" + a.toString() + "], f:[" + f.toString() + "].");
        BigDecimal g = new BigDecimal("0.1");
        System.out.println(a.multiply(g).setScale(2, RoundingMode.DOWN) + "  -  a:[" + a.toString() + "], g:[" + g.toString() + "].");
    }

    private static void bootStrapTest() throws Exception {
        /**
         * JVM加载class文件的两种方法；
         * 隐式加载， 程序在运行过程中当碰到通过new 等方式生成对象时，隐式调用类装载器加载对应的类到jvm中。
         * 显式加载， 通过class.forname()、this.getClass.getClassLoader().loadClass()等方法显式加载需要的类，
         * 或者我们自己实现的 ClassLoader 的 findlass() 方法。
         */
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
        System.out.println();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        while (loader!=null){
            System.out.println(loader.toString());
            loader = loader.getParent();
        }
        // 因为Bootstrap ClassLoader不是一个普通的Java类，所以ExtClassLoader的parent=null
        System.out.println(loader);
    }

    private static void assertTest() throws Exception {
        int a = 199;
        assert (a < 0) : "a[" + a + "] must be greater than 0.";
        System.out.println(".....");
    }

    private static void integerTest() {
        /** 自动装箱: [-128, 127] */
        Integer a = 1;
        Integer b = 1;
        System.out.println(a==b);

        /** 自动装箱: (~, -128), (127 , ~) */
        Integer c = 201;
        Integer d = 201;
        System.out.println(c==d);

        /** 直接创建Integer对象 */
        Integer e = new Integer(1);
        Integer f = new Integer(1);
        System.out.println(e==f);

        /** 自动装箱等价于调用valueOf方法 */
        Integer g = 127;
        Integer h = Integer.valueOf(127);
        System.out.println(g==h);
    }

    private static void listRemoveTest() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println("list: " + JSON.toJSONString(list));
        /** 集合类在进行遍历时, 会检查modCount, 如果该参数在遍历过程中有变化, 则会抛出ConcurrentModificationException */
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String tmp = iterator.next();
            if ("1".equals(tmp)) {
                //list.remove(tmp);
                iterator.remove();
            }
        }
        System.out.println("list: " + JSON.toJSONString(list));
    }

    private static void arraysTest() {
        String[] strings = {"a", "b"};
        List<String> list = Arrays.asList(strings);
        System.out.println("strings: " + JSON.toJSONString(strings));
        System.out.println("list: " + JSON.toJSONString(list));

        strings[0] = "0";
        System.out.println("strings: " + JSON.toJSONString(strings));
        System.out.println("list: " + JSON.toJSONString(list));
    }

    private static void testNonBlankRegex() throws Exception {
        String regex = ".*\\S.*";
        System.out.println("".length());
        System.out.println("".matches(regex));
        System.out.println("    \t".matches(regex));
        System.out.println("       ".matches(regex));
        System.out.println(" AB C".matches(regex));
        System.out.println(" A C ".matches(regex));
        System.out.println("   C ".matches(regex));
        System.out.println("C".matches(regex));
    }

    private static void testClassLoader() throws Exception {
        System.out.println(Thread.currentThread());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);
        Thread.currentThread().setContextClassLoader(null);
        System.out.println(Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(classLoader);
        System.out.println(Thread.currentThread().getContextClassLoader());
    }

    private static void testMvel() throws Exception {
        Map<String, String> map = new HashMap<String, String>(8);
        map.put("x", "1");
        map.put("y", "3333");
        map.put("z", null);

        System.out.println(MVEL.eval("1==1"));
        System.out.println(MVEL.eval("Integer.valueOf(x)", map));
        System.out.println(MVEL.eval("Integer.valueOf(y)", map));
        System.out.println(MVEL.eval("z", map));
    }

    private static void configData() throws Exception {
//        byte[] bytes = {-84, -19, 0, 5, 116, 0, 9, 122, 111, 111, 107, 101, 101, 112, 101, 114};
        byte[] bytes = {-84, -19, 0, 5, 116, 0, 7, 100, 101, 102, 97, 117, 108, 116};
        System.out.println(new String(bytes, "UTF-8"));
        System.out.println(SerializeUtils.deserialize(bytes));
    }

    public static void testSimplePropertyPreFilter() throws Exception {
        Animal animal = new Animal();
        animal.setAge(999);
        System.out.println(JSON.toJSONString(animal));
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getExcludes().add("age");
        System.out.println(JSON.toJSONString(animal, simplePropertyPreFilter));
    }

    private static void testListAddAll() throws Exception {
        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>(2);
        list1.add("10");
        list1.add("11");
        List<String> list2 = new ArrayList<String>(2);
        list2.add("20");
        list2.add("21");
        boolean result1 = list.addAll(list1);
        boolean result2 = list.addAll(list2);
        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
        System.out.println(JSON.toJSONString(list));
    }

    private static void testAssert() {
        assert 1 == 1;
        System.out.println(System.currentTimeMillis());
        assert 1 != 1 : "dsfs";
        System.out.println(System.currentTimeMillis());
    }

    private static void testOctHex() {
        int a = 011;
        System.out.println("===> a: " + a);
        int b = 0x11;
        System.out.println("===> b: " + b);
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

    private static void testJavaBean() throws Throwable {

        Class clazz = PersonEntity.class;
        PersonEntity personEntity = new PersonEntity("ZhangSan", 13);
        // 如果属性值为null,ToStringBuilder会输出score=<null>; 而JSON则不会输出任何信息。
        System.out.println("===> " + JSON.toJSONString(personEntity));
        System.out.println("===> " + ToStringBuilder.reflectionToString(personEntity));

        PersonDTO personDTO = new PersonDTO("personDTO", 99);

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
//            System.out.println(JSON.toJSONString(method));
        }

        System.out.println("\n------------------------------------\n");
        print("   personEntity: " + JSON.toJSONString(personEntity));
        String propertyName = "name";
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, clazz);
        print("   propertyType: " + propertyDescriptor.getPropertyType().getSimpleName());
        print("   propertyType: " + (propertyDescriptor.getPropertyType() == int.class));

        // 获取属性值
        Method getMethod = propertyDescriptor.getReadMethod();
        print("   invoke getMethod[" + getMethod.getName() + "]: " + getMethod.invoke(personEntity));

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
//            print("      - " + propertyDescriptor1.getName());
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
