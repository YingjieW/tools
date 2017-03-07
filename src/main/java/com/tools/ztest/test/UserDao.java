package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/4 下午3:16
 */
public class UserDao extends BaseDao<String> {
    public static void main(String[] args) throws Exception {
        BaseDao userDao = new UserDao();
        userDao.printClzInfo();
    }
}
