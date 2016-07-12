package com.tools.utils;

import com.yeepay.g3.utils.common.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/12 下午3:34
 */
public class CutStringUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 按字节数截取text
     * @param text
     * @param offset
     * @return
     */
    public static String subString(String text, int offset) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(text) || offset <= 0 || offset > text.length()) {
            return text;
        }
        int counter = 0;
        StringBuffer stringBuffer = new StringBuffer();
        int charLength = 0;
        for(int i = 0; i< text.length(); i++) {
            charLength = getCharLength(text.charAt(i));
            counter = counter + charLength;
            if(counter > offset) {
                break;
            }
            stringBuffer.append(text.charAt(i));
        }
        return stringBuffer.toString();
    }

    public static int getCharLength(char c) throws UnsupportedEncodingException{
        return String.valueOf(c).getBytes(DEFAULT_CHARSET).length;
    }

    public static void main(String[] args) throws Exception{
        String text = "测试12345";
        int offset = 7;
        System.out.println(subString(text, offset));
        System.out.println(text.substring(0, 11));
    }
}
