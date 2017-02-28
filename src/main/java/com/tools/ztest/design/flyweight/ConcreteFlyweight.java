package com.tools.ztest.design.flyweight;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午2:19
 */
public class ConcreteFlyweight implements Flyweight {
    @Override
    public void operation(int extrinsicState) {
        System.out.println("ConcreteFlyweight: " + extrinsicState);
    }
}
