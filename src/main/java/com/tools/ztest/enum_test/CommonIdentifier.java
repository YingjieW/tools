package com.tools.ztest.enum_test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/1 下午10:13
 */
public enum CommonIdentifier implements Identifier {
    // 权限级别
    READER, AUTHOR, ADMIN {
        @Override
        public boolean identify() {
            return true;
        }
    };

    @Override
    public boolean identify() {
        return false;
    }
}
