package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:02
 */
public class DB2User implements AbstractUser{
    @Override
    public void insert(Object userEntity) {
        System.out.println("Insert userEntity into db2.");
    }
}
