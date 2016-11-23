package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:08
 */
public class Client {
    public static void main(String[] args) {
        // 当需要切换时,只需要修改这一行代码即可
        // AbstractDatabaseFactory factory = new DB2Factory();
        AbstractDatabaseFactory factory = new MySqlFactory();
        User user = factory.createUser();
        Password password = factory.createPassword();
        user.insert(null);
        password.insert(null);
    }
}
