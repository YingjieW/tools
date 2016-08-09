package com.tools.ztest.fsm;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/8 下午5:02
 */
public enum IntegerStateEnum {
    ODD {
        public IntegerStateEnum check(char c) {
            return c == '1' ? ODD : EVEN;
        }
    },
    EVEN {
        public IntegerStateEnum check(char c) {
            return c == '0' ? ODD : EVEN;
        }
    };

    IntegerStateEnum check(char c) {
        return null;
    }
}
