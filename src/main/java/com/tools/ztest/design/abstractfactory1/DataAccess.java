package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午10:55
 */
public class DataAccess {

    private static String DB = "DB2"; // DB2、MySql
    private static final String DOT = ".";

    private static final String USER = "User";
    private static final String PASSWORD = "Password";

    public static User createUser() throws ClassNotFoundException,InstantiationException, IllegalAccessException {
        String className = User.class.getPackage().getName() + DOT + DB + USER;
        Class clazz = Class.forName(className);
        return (User) clazz.newInstance();
    }

    public static Password createPassword() throws ClassNotFoundException,InstantiationException, IllegalAccessException {
        String className = Password.class.getPackage().getName() + DOT + DB + PASSWORD;
        Class clazz = Class.forName(className);
        return (Password) clazz.newInstance();
    }
}
