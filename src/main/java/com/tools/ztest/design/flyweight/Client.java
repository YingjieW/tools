package com.tools.ztest.design.flyweight;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午2:28
 */
public class Client {
    public static void main(String[] args) {
        int extrinsicState = 99;
        FlyweightFactory flyweightFactory = new FlyweightFactory();

        Flyweight fx = flyweightFactory.getFlyweight("X");
        fx.operation(extrinsicState--);

        Flyweight fy = flyweightFactory.getFlyweight("Y");
        fy.operation(extrinsicState--);

        Flyweight fz = flyweightFactory.getFlyweight("Z");
        fz.operation(extrinsicState--);

    }
}
