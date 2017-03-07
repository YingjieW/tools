package com.tools.ztest.test;

import com.alibaba.fastjson.JSON;
import com.tools.util.ReflectUtils;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/4 下午3:14
 */
abstract class BaseDao<T> {
    private Class<T> clz = ReflectUtils.getGenericClassType(getClass());
    public void printClzInfo() {
        System.out.println(JSON.toJSONString(clz));
    }
}
