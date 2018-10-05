package com.tools.ztest.test;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DNSCache {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) throws Exception {
        Date d = new Date();
        InetAddress.getByName("www.baidu.com");
        try {
            InetAddress.getByName("nowhere.example.com");
        } catch (UnknownHostException e) {

        }

        System.out.println("current time:" + sdf.format(d));
        String addressCache = "addressCache";
        System.out.println("\n" + addressCache);
        printDNSCache(addressCache);

        String negativeCache = "negativeCache";
        System.out.println("\n" + negativeCache);
        printDNSCache(negativeCache);
    }

    private static void printDNSCache(String cacheName) throws Exception {
        Class<InetAddress> klass = InetAddress.class;
        Field acf = klass.getDeclaredField(cacheName);
        acf.setAccessible(true);

        Object addressCache = acf.get(null);
        Class cacheKlass = addressCache.getClass();

        Field cf = cacheKlass.getDeclaredField("cache");
        cf.setAccessible(true);
        Map<String, Object> cache = (Map<String, Object>) cf.get(addressCache);

        for (Map.Entry<String, Object> hi : cache.entrySet()) {
            Object cacheEntry = hi.getValue();
            Class cacheEntryKlass = cacheEntry.getClass();
            Field expf = cacheEntryKlass.getDeclaredField("expiration");
            expf.setAccessible(true);
            long expires = (Long) expf.get(cacheEntry);

            Field af = cacheEntryKlass.getDeclaredField("addresses");
            af.setAccessible(true);
            InetAddress[] addresses = (InetAddress[]) af.get(cacheEntry);
            List<String> ads = new ArrayList<String>(addresses.length);
            for (InetAddress address : addresses) {
                ads.add(address.getHostAddress());
            }

            System.out.println(hi.getKey() + " | expireTime: ["+ sdf.format(new Date(expires)) +"] | " + ads);
        }
    }
}
