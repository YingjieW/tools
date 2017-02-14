package com.tools.util;

import org.apache.commons.lang.StringUtils;

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
    public static String subStringByByte(String text, int offset, String charset) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(text) || offset <= 0 || offset > text.length()) {
            return text;
        }
        if (StringUtils.isBlank(charset)) {
            charset = DEFAULT_CHARSET;
        }
        int counter = 0;
        StringBuffer stringBuffer = new StringBuffer();
        int charLength = 0;
        for(int i = 0; i< text.length(); i++) {
            charLength = getCharLength(text.charAt(i), charset);
            counter = counter + charLength;
            if(counter > offset) {
                break;
            }
            stringBuffer.append(text.charAt(i));
        }
        return stringBuffer.toString();
    }

    public static int getCharLength(char c, String charset) throws UnsupportedEncodingException{
        return String.valueOf(c).getBytes(charset).length;
    }

    public static void main(String[] args) throws Exception{
        String text = "测试12345";
        int offset = 4;
        String charset = System.getProperty("file.encoding");
        System.out.println(charset);
        System.out.println(subStringByByte(text, offset, null));
        System.out.println(text.substring(0, 7));
    }
}
