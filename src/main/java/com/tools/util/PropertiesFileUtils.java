package com.tools.util;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/25 下午4:23
 */
public class PropertiesFileUtils {

    private static final String BASE_CHARSET = "ISO-8859-1";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String PROPERTIES_FILE_SUFFIX = ".properties";

    public static Map<String, String> loadProps(String url) throws UnsupportedEncodingException, MissingResourceException {
        if(StringUtils.isBlank(url)) {
            throw new RuntimeException("url must be specified");
        }
        url = removePropertiesFileSuffix(url);
        return loadProps(url, DEFAULT_CHARSET);
    }

    public static Map<String, String> loadProps(String url, String charset) throws UnsupportedEncodingException, MissingResourceException{
        if(StringUtils.isBlank(url)) {
            throw new RuntimeException("url must be specified");
        }
        if(StringUtils.isBlank(charset)) {
            throw new RuntimeException("charset must be specified");
        }
        url = removePropertiesFileSuffix(url);
        Map<String, String> result = new HashMap<String, String>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(url);
        Set<String> keySet = resourceBundle.keySet();
        for(String key : keySet) {
            String value = resourceBundle.getString(key);
            String keyConverted = new String(key.getBytes(BASE_CHARSET), charset);
            String valueConverted = new String(value.getBytes(BASE_CHARSET), charset);
            result.put(keyConverted, valueConverted);
        }
        return result;
    }

    private static String removePropertiesFileSuffix(String url) {
        if(url.lastIndexOf(PROPERTIES_FILE_SUFFIX) != -1) {
            url = url.substring(0, url.lastIndexOf(PROPERTIES_FILE_SUFFIX));
        }
        return url;
    }
}
