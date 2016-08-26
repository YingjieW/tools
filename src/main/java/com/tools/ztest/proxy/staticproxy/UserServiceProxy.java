package com.tools.ztest.proxy.staticproxy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/25 下午3:10
 */
public class UserServiceProxy implements UserService {

    private UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void saveToDatabase() {
        System.out.println("-- Connect to db...");
        userService.saveToDatabase();
        System.out.println("-- Close connection...");
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceProxy(new UserServiceImpl());
        userService.saveToDatabase();
    }
}
