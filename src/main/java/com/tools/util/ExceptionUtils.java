package com.tools.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @author：yingjie.wang
 * @since：2016年3月21日 下午6:34:03 
 * @version:
 */
public class ExceptionUtils {

    public static String exception2Str(Throwable t) {
        if(t == null)
            return null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return t.getMessage();
        }
    }
}
