package com.tools.util;

import java.util.Random;
import java.util.UUID;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/31 下午4:39
 */
public class SerialNoUtils {

    private static String getBaseSerialNo(String key) {
        long seed = System.currentTimeMillis();
        Random rd = new Random(seed);
        UUID uuid = UUID.randomUUID();
        StringBuffer sb = new StringBuffer();
        sb.append(uuid.toString()).append(String.valueOf(rd.nextInt())).append(key);
        String idString = sb.toString().replaceAll("-", "");
        return idString.substring((idString.length() - 25 < 0) ? 0 : (idString.length() - 25), idString.length())+"";
    }

    public static void main(String[] args) {
        System.out.println("***     idString: " + getBaseSerialNo("Test"));
    }
}
