package com.tools.util;

import java.math.BigDecimal;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/10 下午4:19
 */
public class BigDecimalUtils {

    /**
     *  使用BigDecimal(String)构造方法,重新精确构造BigDecimal
     * @param bigDecimal
     * @return
     */
    public static BigDecimal reconstruct(BigDecimal bigDecimal) {
        if(bigDecimal == null) {
            return  null;
        }
        return new BigDecimal(Double.toString(bigDecimal.doubleValue()));
    }
}
