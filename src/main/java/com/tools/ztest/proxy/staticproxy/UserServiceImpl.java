package com.tools.ztest.proxy.staticproxy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/25 下午3:09
 */
public class UserServiceImpl implements UserService {
    @Override
    public void saveToDatabase() {
        System.out.println("-- Save to db...");
    }
}
