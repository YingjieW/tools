package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:03
 */
public class DB2Password implements Password {
    @Override
    public void insert(Object passwordEntity) {
        System.out.println("Insert passwordEntity into db2.");
    }
}
