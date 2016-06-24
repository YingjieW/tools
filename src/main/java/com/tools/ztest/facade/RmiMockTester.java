package com.tools.ztest.facade;

import com.tools.ztest.reflect.enumtype.CommonType;

import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/24 下午2:42
 */
public interface RmiMockTester {

    int getInt();

    Map getMap();

    CommonType getEnum();
}
