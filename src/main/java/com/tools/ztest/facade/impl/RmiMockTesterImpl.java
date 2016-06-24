package com.tools.ztest.facade.impl;

import com.tools.ztest.facade.RmiMockTester;
import com.tools.ztest.reflect.enumtype.CommonType;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/24 下午2:42
 */
//@Component("rmiMockTester")
@Service("rmiMockTester")
public class RmiMockTesterImpl implements RmiMockTester {

    @Override
    public int getInt() {
        return 0;
    }

    @Override
    public Map getMap() {
        return null;
    }

    @Override
    public CommonType getEnum() {
        return CommonType.ARRAYLIST;
    }
}
