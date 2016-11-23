package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:00
 */
public interface AbstractUser {
    // 真实应用中建议定义一用户实体类——UserEntity,此处以Object代替
    void insert(Object userEntity);
}
