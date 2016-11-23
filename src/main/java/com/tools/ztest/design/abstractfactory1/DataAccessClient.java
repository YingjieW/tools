package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午11:03
 */
public class DataAccessClient {
    public static void main(String[] args) throws Exception {
        User user = DataAccess.createUser();
        Password password = DataAccess.createPassword();
        user.insert(null);
        password.insert(null);
    }
}
