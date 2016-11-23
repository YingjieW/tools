package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:05
 */
public class MySqlPassword implements AbstractPassword{
    @Override
    public void insert(Object passwordEntity) {
        System.out.println("Insert passwordEntity into mysql.");
    }
}
