package com.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.tools.util.BeanUtils;
import com.tools.util.SerializeUtils;
import com.tools.util.ThreadSafeDateUtils;
import com.tools.ztest.javabeans.PersonDTO;
import com.tools.ztest.javabeans.PersonEntity;
import com.tools.ztest.reflect.enumtype.CommonType;
import com.tools.ztest.test.Animal;
import com.yeepay.g3.utils.common.DateUtils;
import com.yeepay.g3.utils.common.OrderedProperties;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ztest {

    public Ztest() {
        System.out.println("default constructor...");
    };

    public Ztest(String str) {
        System.out.println("custom constuctor...");
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
        testCollectionsSort();
    }

    public static void testCollectionsSort() throws Exception {
        String[] alphabets = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int bound = alphabets.length;
        Random random = new Random(System.currentTimeMillis());
        List<String> list = new ArrayList<>();
        int length = 0;
        StringBuilder sb = null;
        for (int l = 0; l < 20000; l++) {
            length = random.nextInt(bound) + 1;
            sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(alphabets[random.nextInt(bound)]);
            }
            list.add(sb.toString());
        }
        long start = System.currentTimeMillis();
        Collections.sort(list);
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 从实际测试来看，classLoader.getResources(name)方法会搜索所有路径，包括导入的jar包
     * 而classLoader.getResouce(name)方法，先找父级，若存在则返回，否则，查找自身（同委托代理相同
     * @throws Exception
     */
   public static void testGetResources() throws Exception {
//       String propPath = "runtimecfg/udm-client.properties";
       String propPath = "config-util-conf/zkConfig.properties";
       Properties prop = PropertiesLoaderUtils.loadAllProperties(propPath, Thread.currentThread().getContextClassLoader());
       System.out.println(JSON.toJSONString(prop));
       System.out.println("-----");

       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
       System.out.println(classLoader.getResource(propPath).getPath());
       Map<String, String> result = loadProps(propPath, classLoader);
       System.out.println(JSON.toJSONString(result));

       ClassLoader classLoader1 = classLoader.getParent();
       Map<String, String> result1 = loadProps(propPath, classLoader1);
       System.out.println(JSON.toJSONString(result1));

       System.out.println("**********");
       Enumeration<URL> urls = classLoader.getResources(propPath);
       System.out.println(urls.getClass().getName());
       while (urls.hasMoreElements()) {
           URL url = urls.nextElement();
           System.out.println("__" + JSON.toJSONString(url));
       }
       System.out.println("\n" + JSON.toJSONString(urls));
   }

    public static Map<String,String> loadProps(String uri, ClassLoader classLoader){
        OrderedProperties props = new OrderedProperties();
        Map result = new LinkedHashMap();
        InputStream is = classLoader.getResourceAsStream(uri);
        if (is == null) {
            return null;
        }
        try {
            props.loadMap(is, result);
        } catch (Exception e) {
            throw new RuntimeException("load resource fail, uri:"+uri+" errorMsg:"+e.getMessage(), e);
        }finally{
            if(is !=null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    private static void testReflectConstuctor() throws Exception {
        Constructor constructor = Ztest.class.getDeclaredConstructor(null);
        System.out.println(JSON.toJSONString(constructor));
        constructor.newInstance();
        constructor = Ztest.class.getDeclaredConstructor(String.class);
        System.out.println(JSON.toJSONString(constructor));
        constructor.newInstance("test");
    }

    private static void testClassName() throws Exception {
        String className = Ztest.class.getName();
        String shortClassName = ClassUtils.getShortName(className);
        String deClassName = Introspector.decapitalize(shortClassName);
        System.out.println(className);
        System.out.println(shortClassName);
        System.out.println(deClassName);
    }

    private static void testDate() throws Exception {
        long time1 = 1490284800000l;
        java.sql.Date date = new java.sql.Date(time1);
        System.out.println(date.toString());
        Date date1 = new Date(time1);
        System.out.println(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(sdf.format((Date) date));

        long time2 = 1490263664009l;
        Timestamp timestamp = new Timestamp(time2);
        System.out.println(timestamp.toString());
        Date date2 = new Date(time2);
        System.out.println(date2.toString());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
        System.out.println(sdf1.format((Date) timestamp));
    }

    private static void testFileChannel() throws Exception {
        String filePath = "/Users/YJ/Downloads/test/test.txt";
        File file = new File(filePath);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = raf.getChannel();
        System.out.println(fileChannel.size());
        fileChannel.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = reader.readLine();
        System.out.println(line);
        System.out.println(line.getBytes("utf-8").length);
        System.out.println(file.length());
        String testLine = "123456\n";
        System.out.println(testLine.getBytes("utf-8").length);
    }

    private static void testListStream() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(2); list.add(3); list.add(1); list.add(4);
        System.out.println(list.stream().mapToInt(Integer::intValue).sum());
    }

    private static void testConcatenateList() throws Exception {
        String item = "`2017-06-19 17:41:21,`11170619173918806008,`cTrx1497865318103,`1111111110000216,`TRADE,`0.10,`0.05,`,`,`,`WECHAT_SCAN,`,`HELLOWORLD\n";
        List<String> itemList = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            itemList.add(item);
        }
        long startTime1 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (String str : itemList) {
            sb.append(str);
        }
        System.out.println("---> 1.cost: " + (System.currentTimeMillis() - startTime1));
        long startTime2 = System.currentTimeMillis();
        itemList.stream().reduce("", String::concat);
        System.out.println("---> 2.cost: " + (System.currentTimeMillis() - startTime2));
        long startTime3 = System.currentTimeMillis();
        itemList.parallelStream().reduce("", String::concat);
        System.out.println("---> 3.cost: " + (System.currentTimeMillis() - startTime3));
    }

//        System.out.println(String.format("%,d", 12345679));

    private static void testSystemProperty() throws Exception {
        System.out.println(TimeZone.getDefault().getID());
        final Properties p = System.getProperties();
        final Enumeration e = p.keys();
        while (e.hasMoreElements()) {
            final String prt = (String) e.nextElement();
            final String prtvalue = System.getProperty(prt);
            System.out.println(prt + ":" + prtvalue);
        }
    }

    private static void test(Object... obj) throws Exception {
        System.out.println(obj.getClass());
        System.out.println(obj.length);
    }

    private static void testTreeMap() throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 31);
        map.put("c", 23);
        map.put("b", 12);
        map.put("d", 14);

        Collection<Integer> collection = map.values();
        System.out.println(JSON.toJSONString(collection));
//        Integer[] integers = collection.toArray(new Integer[collection.size()]);
//        System.out.println(JSON.toJSONString(integers));
//        Arrays.sort(integers);
//        System.out.println(JSON.toJSONString(integers));
    }

    private static void testGetMonthStart() {
        Date date = new Date();
        System.out.println(ThreadSafeDateUtils.formatDateTimeMillis(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(ThreadSafeDateUtils.formatDateTimeMillis(calendar.getTime()));
    }

    private static void testBlockingQueue() throws Exception {
        Consumer consumer = new Consumer();
        Producer producer = new Producer(consumer);
        producer.start();
        consumer.start();
    }

    private static final BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(5);
    private static class Producer extends Thread {

        private final Thread consumer;

        public Producer(Thread consumer) {
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                queue.put("1");
                queue.put("2");
                queue.put("3");
                queue.put("4");
                queue.put("5");
                System.out.println("queue: " + queue.toString());
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        String data = i + "_" + j;
                        System.out.println(data + ":" + System.currentTimeMillis());
                        queue.put(data);
                        consumer.interrupt();
                        System.out.println(data + ":" + System.currentTimeMillis());
                    }
                    System.out.println("queue: " + queue.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                Object object = queue.peek();
                if (object == null) {
                    System.out.println("queue is empty: " + System.currentTimeMillis());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("InterruptedException : " + System.currentTimeMillis());
                    }
                    continue;
                }
                object = queue.poll();
                System.out.println("___object : " + object.toString());
            }
        }
    }

    private static void testDateLoop() throws Exception {
        Date startDate = ThreadSafeDateUtils.parseDate("2017-07-05");
        Date endDate = ThreadSafeDateUtils.parseDate("2017-07-05");
        while (startDate.before(endDate)) {
            System.out.println(ThreadSafeDateUtils.formatDateTimeMillis(startDate));
            startDate = DateUtils.addDay(startDate, 1);
        }
        System.out.println(">>>  " + ThreadSafeDateUtils.formatDateTimeMillis(endDate));
    }

    private static void testMonth() throws Exception {
        String monthStr = "2017-06";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date monthDate = dateFormat.parse(monthStr);
        System.out.println(ThreadSafeDateUtils.formatDateTimeMillis(monthDate));

        String dateStr = ThreadSafeDateUtils.formatDate(new Date());
        Date date = ThreadSafeDateUtils.parseDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(ThreadSafeDateUtils.formatDateTimeMillis(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(ThreadSafeDateUtils.formatDateTimeMillis(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date end = calendar.getTime();
        System.out.println(ThreadSafeDateUtils.formatDateTimeMillis(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date start = calendar.getTime();
        System.out.println(ThreadSafeDateUtils.formatDateTimeMillis(calendar.getTime()));
        System.out.println(ThreadSafeDateUtils.daysOfTwo(start, end)+1);
    }

    private static void testInterrupt() throws Exception {
        try {
            Thread01 thread01 = new Thread01("Thead01");
            thread01.start();
            Thread.sleep(3*100);
            System.out.println("Sleep over.");
            thread01.interrupt();
//            Thread.sleep(3*10);
            System.out.println("thread01.isAlive : " + thread01.isAlive());
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            while (thread01.isAlive()) {
                endTime = System.currentTimeMillis();
            }
            System.out.println("thread01.aliveTime : " + (endTime - startTime));
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("------Over......");
    }

    private static class Thread01 extends Thread {
        public Thread01(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
//                Thread.sleep(6*10*1000);
                for (int i = 0; i < 50000; i++) {
                    System.out.println("this : " + this.getName());
                    System.out.println("CurrentThread(103) : " + Thread.currentThread().getName());
                    if (this.interrupted()) {
                        System.out.println("CurrentThread(105) : " + Thread.currentThread().getName());
                        System.out.println("Should be stopped and exit.");
                        throw new InterruptedException("testing.....");
                    }
                    System.out.println("i = " + i);
                }
                System.out.println("this line cannot be executed. cause thread throws exception");
            } catch (InterruptedException e) {
                System.out.println("CurrentThread(113) : " + Thread.currentThread().getName());
                System.out.println("interrupt flag : " + this.isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println("interrupt flag : " + this.isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println("118_____over.......");
            }
        }
    }

    private static void testBomb() throws Exception {
//        String sourcePath = "/Users/YJ/shared/z_tmp/test.csv";
//        String targetPath = "/Users/YJ/shared/z_tmp/testCopy.csv";
//        File sourceFile = new File(sourcePath);
//        File targetFile = new File(targetPath);
//        BufferedInputStream is = new BufferedInputStream(new FileInputStream(sourceFile));
//        BufferedOutputStream  os = new BufferedOutputStream(new FileOutputStream(targetFile));
//        byte[] uft8bom={(byte)0xef,(byte)0xbb,(byte)0xbf};
//        os.write(uft8bom);
//        byte[] tmp = new byte[1024];
//        while (true) {
//            int i = is.read(tmp);
//            if (i == -1) {
//                break;
//            }
//            os.write(tmp, 0, i);
//        }
//        is.close();
//        os.close();


        String targetPath = "/Users/YJ/shared/z_tmp/testCopy1.csv";
        File targetFile = new File(targetPath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        BufferedOutputStream  os = new BufferedOutputStream(new FileOutputStream(targetFile));
        OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
        BufferedWriter bw = new BufferedWriter(osw);
        byte[] uft8bom={(byte)0xef,(byte)0xbb,(byte)0xbf};
        bw.write(new String(uft8bom));
        bw.write("no data today");
        bw.flush();

        bw.close();
        os.close();
        osw.close();
    }

    private static void testReadFile() throws Exception {
//        File file = new File("/Users/YJ/shared/z_tmp/BA12390_refund_20170531.csv");
        File file = new File("/Users/YJ/shared/z_tmp/testCopy1.csv");
        InputStream inputStream = new FileInputStream(file);
        LineIterator lineIterator = IOUtils.lineIterator(inputStream, "utf-8");
        while (lineIterator.hasNext()) {
            System.out.println(lineIterator.next());
        }
    }

    private static void testHashBasedTable() throws Exception {
        Table<Integer, String, Integer> table = HashBasedTable.create();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                table.put(i, String.valueOf(j), i+j);
            }
        }
        // {0={3=3, 2=2, 1=1, 0=0}, 1={3=4, 2=3, 1=2, 0=1}, 2={3=5, 2=4, 1=3, 0=2}}
        System.out.println(table);
        // 2
        System.out.println(table.get(0, "2"));
        // 5
        System.out.println(table.get(2, "3"));
        table.put(2, "3", 23);
        // 23
        System.out.println(table.get(2, "3"));
        // {0=3, 1=4, 2=23}
        System.out.println(table.column("3"));
        // {}
        System.out.println(table.column("4"));
        // true
        System.out.println(table.containsColumn("3"));
        // false
        System.out.println(table.containsColumn("4"));
        // {3=23, 2=4, 1=3, 0=2}
        System.out.println(table.row(2));
        // {}
        System.out.println(table.row(9));
        // true
        System.out.println(table.containsRow(2));
        // false
        System.out.println(table.containsRow(9));
        // true
        System.out.println(table.contains(2, "1"));
        // false
        System.out.println(table.contains(22, "1"));
        // {3={0=3, 1=4, 2=23}, 2={0=2, 1=3, 2=4}, 1={0=1, 1=2, 2=3}, 0={0=0, 1=1, 2=2}}
        System.out.println(table.columnMap());
        // {0={3=3, 2=2, 1=1, 0=0}, 1={3=4, 2=3, 1=2, 0=1}, 2={3=23, 2=4, 1=3, 0=2}}
        System.out.println(table.rowMap());
    }

    private static void testIO() throws Exception {
        String path = "/Users/YJ/Documents/generator/test/testIO.txt";
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        try {
            byte[] bytes = "Hello world\n".getBytes();
            fos.write(bytes);
            if (bytes.length > 1) {
                throw new Exception("testing........");
            }
            fos.write(bytes);
        } catch (Exception e) {
            throw e;
        } finally {
            fos.close();
        }
    }

    private static void testFixedThreadPool() throws Throwable {
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(20);
        queue.add("0");
        Executors.newFixedThreadPool(5).execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (!queue.isEmpty()) {
                            String text = queue.poll();
                            if (text != null) {
                                System.out.println("---> thread:[" + (Thread.currentThread().getName()) + "]ms, text : " + text);
                                Thread.sleep(5*100);
                            } else {
                                System.out.println("............");
                            }
                        } else {
                            Thread.sleep(2 * 1000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println("...end-pool");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 20; i++) {
                    queue.add(String.valueOf(i));
                }
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
        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println("list: " + JSON.toJSONString(list));
//        for (int)
        /** 集合类在进行遍历时, 会检查modCount, 如果该参数在遍历过程中有变化, 则会抛出ConcurrentModificationException */
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            String tmp = iterator.next();
//            if ("1".equals(tmp)) {
//                //list.remove(tmp);
//                iterator.remove();
//            }
//        }
        System.out.println("list: " + JSON.toJSONString(list));
    }

    private static void configData() throws Exception {
//        byte[] bytes = {-84, -19, 0, 5, 116, 0, 9, 122, 111, 111, 107, 101, 101, 112, 101, 114};
        byte[] bytes = {-84, -19, 0, 5, 116, 0, 7, 100, 101, 102, 97, 117, 108, 116};
        System.out.println(new String(bytes, "UTF-8"));
        System.out.println(String.valueOf(SerializeUtils.deserialize(bytes)));
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

    @Override
    public String toString() {return null;}
}
