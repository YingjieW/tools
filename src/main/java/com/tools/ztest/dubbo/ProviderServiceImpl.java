package com.tools.ztest.dubbo;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/9 下午10:07
 */
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String sayHello(String name) {
        return "Hello ~ " + name + " ~ world.";
    }
}
