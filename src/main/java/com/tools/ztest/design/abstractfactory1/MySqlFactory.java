package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:07
 */
public class MySqlFactory implements AbstractDatabaseFactory{
    @Override
    public User createUser() {
        return new MySqlUser();
    }

    @Override
    public Password createPassword() {
        return new MySqlPassword();
    }
}
