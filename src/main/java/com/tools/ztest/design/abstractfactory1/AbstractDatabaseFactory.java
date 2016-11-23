package com.tools.ztest.design.abstractfactory1;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午9:06
 */
public interface AbstractDatabaseFactory {
    AbstractUser createUser();
    AbstractPassword createPassword();
}
