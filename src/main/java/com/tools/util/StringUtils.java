package com.tools.util;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/14 上午11:08
 */
public class StringUtils {

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(cs.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static String trim(String text) {
        return text == null ? "" : text.trim();
    }

    /**
     * 数组转换为字符串,中间使用joinMark链接
     * @param obj
     * @param joinMark
     * @return
     */
    public static String transArr2Str(Object obj, String joinMark) {
        if (!obj.getClass().isArray()) {
            throw new IllegalArgumentException("obj must be a array.");
        }
        if (obj == null) {
            return "";
        }
        Object[] arr = (Object[]) obj;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i < arr.length -1) {
                sb.append(arr[i].toString()).append(joinMark);
            } else {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 要将原有字符创截取到多长
     *
     * @param source
     * @param howLong
     * @return
     */
    public static String subString(String source, int howLong, String charType) {
        if (source == null) {
            return "";
        }
        if (!checkLength(source, howLong, charType)) {
            try {
                byte[] bytes = source.getBytes(charType);
                byte[] newBytes = new byte[howLong];
                // 做字节截取
                System.arraycopy(bytes, 0, newBytes, 0, howLong);
                return new String(newBytes);
            } catch (Throwable e) {
                // 当非法字符类型，除以3，以utf-8处理
                return (howLong / 3) > source.length() ? source : source.substring(0, howLong / 3);
            }
        }
        return source;
    }

    private static boolean checkLength(String str, int length, String charType) {
        try {
            if (null == str) {
                return true;
            }
            if (str.getBytes(charType).length > length) {
                return false;
            }
        } catch (Throwable e) {
            return false;
        }
        return true;
    }
}
