package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:07
 */
public class DB2Factory implements AbstractDatabaseFactory{
    @Override
    public User createUser() {
        return new DB2User();
    }

    @Override
    public Password createPassword() {
        return new DB2Password();
    }
}
