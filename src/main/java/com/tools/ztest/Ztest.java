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
import com.yeepay.g3.utils.common.httpclient.SimpleHttpUtils;
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
//        testAlibabaJson();
//        testJavaBean();
//        testPropertyCopy();
        testBigDecimal();
        String text = "1607110058jbkraj, 16071100581j5nqe, 1607110058v42yeq, 1607110057cgz3o7, 1607110056svxr5n, 160711005304rnm9, 1607110052jdlwk7, 1607110051l5zyry, 1607110051m2osr5, 1607110048io2mvj, 1607110046u0p6or, 16071100468qf5g5, 160711004312ac7k, 1607110041revhi6, 1607110041v3l52v, 1607110041d9qamm, 16071100373dt6gx, 1607110029bfmrq5, 1607110027uvve5q, 1607110025w1euya, 1607110025kq76x9, 1607110023ivztcn, 1607110021qdxs46, 1607110021eqbsxp, 1607110017eflhow, 1607110017rgdji7, 1607110014lawq3j, 1607110014qw40dh, 1607110013gjr8ro, 16071100121kyi0b, 16071100089w01wf, 1607110005y0d4h7, 1607110003ou7myx, 1607110001xy5o3c, 1607110001mqudl8, 16071023591ftf63, 16071023588rzoqx, 1607102357i8wk4y, 1607102356epnjuw, 1607102356wr67ym, 1607102356he9slx, 1607102355meoj5d, 1607102355d73vq6, 1607102355wv5ozu, 16071023536pbo53, 1607102353yrv4kj, 1607102353a8zpa1, 1607102352zdqu4t, 1607102351u7oh02, 1607102348dldmnz, 16071023440f2xsu, 160710234391lt8q, 1607102343yn6g1o, 1607102342n70r61, 1607102342s7t68q, 1607102341jdqzwl, 16071023417hb6pb, 16071023390460wd, 1607102338qhy6mu, 1607102337jl76wp, 1607102335whnptj, 16071023347g3wpz, 1607102333541i8n, 1607102332acdy6k, 16071023284r3rvr, 16071023271jo0h4, 1607102327c56c6r, 1607102326coow0p, 1607102326iw1dzq, 1607102323gj0gxf, 1607102323k5ggxv, 1607102323qyjqzz, 1607102322g9h65p, 1607102321shwcqn, 160710232147pjkb, 1607102320wd27v8, 16071023196s659v, 1607102318x2j0ak, 1607102318qxiheu, 1607102317vqwbrz, 1607102317ytzuzu, 1607102316o0gz6a, 1607102313m3m73m, 16071023126c8fvk, 1607102311e3q51n, 16071023114bfd7s, 1607102311777gtz, 1607102310vlef66, 16071023101a24x4, 1607102310wirpmo, 1607102310zwdb6g, 16071023093elifs, 160710230998hlaz, 16071023082or8gl, 160710230805t21u, 1607102307bcr261, 16071023072t25zb, 16071023075klge5, 1607102306clhqr6, 1607102305v1bxx3, 1607102305k7npiz, 1607102305erc8dh, 1607102305mv47nm, 1607102304slxvwe, 1607102304v9ap34, 1607102304lutnv6, 1607102303m6kx1j, 1607102303n2vzi2, 16071023016zo47m, 1607102301lpt6wk, 1607102301brirlk, 1607102300yx2edq, 1607102259bqqh7y, 16071022581wc3ai, 1607102258h5jit3, 1607102257bywgxl, 1607102257mzo5ss, 16071022571zoepx, 1607102256pmpdzj, 16071022560v5qb0, 1607102256x4mbqi, 16071022563uw8jd, 1607102255akdoez, 1607102255gxivo7, 1607102255qbiagt, 1607102255m1ty5p, 1607102255hsuxwp, 1607102254lvexhn, 160710225427ue0j, 16071022545gyvub, 1607102253kuq5rv, 1607102253g8drgo, 1607102253ggkly8, 1607102253g1u1z2, 1607102253z4b2ib, 1607102252xa8tzr, 160710225105pr76, 1607102252yvb5nx, 16071022519iawuv, 1607102250ft01vd, 1607102250otzftp, 1607102248se3bt0, 16071022481779ir, 1607102247d2eo3d, 1607102245du2w5w, 1607102245v8bvn3, 16071022441n9s7w, 16071022438defqg, 1607102242f65un8, 1607102241az77nw, 1607102241i7xy1o, 16071022416b922s, 1607102239yvhddj, 1607102238c1qrr8, 1607102236m19jff, 1607102237be97ei, 1607102237z2e3bg, 1607102236hkkkkc, 16071022364h31s4, 16071022358anhu5, 1607102234g14seu, 1607102234q5nk35, 1607102234lphvof, 1607102233muzxoh, 1607102233f505jo, 16071022335lfibf, 1607102233fiodkt, 1607102233r9x7aa, 1607102232g2d4rg, 1607102232up573w, 1607102232xmxf1r, 16071022311i07px, 160710223058bbak, 1607102230p7r28v, 1607102230t1jcrs, 1607102229wzoujo, 1607102229oubybr, 1607102229uxgouk, 1607102228ilxti7, 1607102228mrb51n, 16071022289awarn, 160710222801p1dq, 1607102228pgrjy2, 1607102228hn03v4, 1607102228yo8jtc, 1607102228rix6k1, 16071022275twqw3, 1607102227s5vr7a, 1607102227sylt9k, 16071022270e1zg4, 160710222787lofe, 16071022273bx4rj, 1607102227iwvlwa, 1607102227s4j5nj, 1607102227ua4jno, 1607102226vuokgs, 1607102225uersd4, 16071022260ig1p7, 16071022260u3akb, 16071022250v6s95, 1607102225alj12h, 160710222555egmu, 1607102225wfn1hs, 1607102224pe6t1h, 16071022252n7h8d, 1607102224z1ygbm, 1607102224xi6v3m, 1607102224leyu8h, 16071022246z08jw, 16071022240wbutp, 16071022233f1qsz, 1607102223geojx4, 16071022229lvlbn, 1607102222s476qg, 16071022222i4hjk, 1607102222d4v9k7, 1607102221iphwk3, 1607102220yy0dh9, 1607102221m4f15j, 1607102221hdwzfc, 16071022216wagwy, 1607102220pds8sv, 1607102220z1qidk, 1607102220jz50nk, 160710221972xp08, 1607102219murorc, 16071022199rzajw, 1607102218aask6a, 1607102218nc4h5s, 1607102218fq2a15, 1607102218myyqy4, 160710221845b3ck, 1607102217u1z6ic, 16071022179ctjnt, 1607102217mko0bs, 1607102217348346, 1607102217xh456m, 1607102216uzlqbh, 160710221584qrsj, 1607102215b2htya, 1607102215m309ee, 1607102214mzerg5, 1607102214fiq8ku, 1607102214nsaemv, 1607102214if1dyg, 16071022140k9ycq, 1607102213vl9nlu, 1607102213vah4jz, 1607102212qycgip, 1607102212rcz61i, 1607102211r4blen, 1607102211t0ar60, 1607102210nd30lb, 1607102210g6yg30, 16071022094l9nbb, 1607102209pa1gmw, 1607102209pyomp6, 1607102208dea7xy, 16071022082nlu29, 1607102208d7oxiy, 1607102208qcizk8, 1607102208pua32e, 1607102208ldmvbk, 1607102207qpw5v9, 1607102206tsy97e, 1607102207s7dvsm, 16071022068glg4s, 16071022066zayyi, 16071022062bi0n6, 1607102206cyihso, 1607102205vvf8ln, 1607102206bdwre0, 1607102205y3h94h, 16071022049iqgwv, 1607102204kg9lme, 1607102204h2m0wr, 1607102203x0y1k2, 1607102203ypf4rk, 16071022036w2wyu, 1607102203hozcyy, 16071022021gui17, 1607102202sd53pl, 16071022029yja2l, 160710220188mvzl, 1607102201to2248, 16071022018ebd7q, 1607102200mk3m42, 1607102201n93orw, 1607102200znupxd, 1607102200qpia3o, 1607102159swx87r, 16071021582517d0, 1607102158l3ptmz, 1607102158jzid9x, 16071021581zrvdn, 1607102157u8oagz, 1607102157e5q4ai, 1607102156daupil, 1607102154inwacu, 1607102154tsojki, 16071021548azdhd, 1607102154ro8den, 16071021526ffyj9, 1607102152mmgt88, 1607102152icjtgl, 1607102151w8kvbl, 1607102150ojdere, 1607102150tfv39f, 1607102148c662sx, 1607102148tydo7f, 1607102146x86xpk, 1607102142pcp1mu, 1607102142nwwed5, 1607102136wmgoec, 1607102132i4veq9, 16071021326n8ali, 1607102129rzwfhy, 1607102129ssjmzy, 1607102127h6bg6b, 1607102127hbczn2, 1607102126mqb7oo, 1607102126tm21bn, 1607102125wj05bg, 1607102125c9xkj8, 1607102125l4ici1, 1607102124k48wsl, 1607102123jja3a0, 16071021221p6wm8, 1607102122tphcvp, 16071021213dradf, 1607102121xpfp3n, 1607102120o6wqfb, 1607102120ozjqo7, 16071021193xll46, 160710211966g2bt, 16071021191b2h9b, 16071021199lrd7t, 1607102117ua3h85, 1607102117bpxvl1, 16071021179g3ob9, 1607102117apen6z, 1607102115v9ieyu, 16071021154z8lrz, 1607102115d0inz6, 160710211476r5az, 1607102114ohrkly, 1607102114jbixwf, 1607102114j39pwp, 1607102112okxyx3, 1607102112p9ogzk, 1607102111azz46p, 1607102111dp6pn9, 16071021096484l6, 1607102109dfooom, 16071021095xm5u9, 1607102108p6xz73, 16071021088yt98r, 16071021079cptj6, 16071021075yz4dq, 1607102106dscbfr, 16071021065wsvlq, 1607102105w4h96s, 1607102104ihfi90, 16071021046631aw, 1607102102c4pr4l, 16071021024o37uv, 1607102059mgxwx7, 1607102055wbp6rn, 1607102054l9zosn, 16071020524aswkx, 1607102050enuc7k, 1607102045bw0jps, 1607102040gtp9ip, 1607102040dst4eo, 16071020386pl3n3, 160710203676929v, 160710203504qr0m, 16071020327vr7zo, 1607102030yivma6, 160710202913fg0n, 1607102029ghrrrs, 1607102028pgey6o, 1607102026aekgy0, 160710202517u8cd, 1607102020f6jk4j, 1607102020nbt50w, 16071020185r8r6l, 1607102018vsxsat, 160710201686ryhr, 1607102013xg94c8, 1607102008kqrl4s, 1607102008q4489u, 16071020060mogzm, 1607102004eaw91u";
    }

    private static void testBigDecimal() throws Exception {
        BigDecimal b1 = new BigDecimal(1.2);
        BigDecimal b2 = new BigDecimal(1.1);
        print(b1.doubleValue());
        print(b1.add(b2).doubleValue());
        print(b1.doubleValue());

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
