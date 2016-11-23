package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:04
 */
public class MySqlUser implements User {
    @Override
    public void insert(Object userEntity) {
        System.out.println("Insert userEntity into mysql.");
    }
}
