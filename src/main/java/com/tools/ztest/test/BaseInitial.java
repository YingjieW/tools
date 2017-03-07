package com.tools.ztest.test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/4 下午2:54
 */
public abstract class BaseInitial {
    // 模板方法
    public final void dataInitial() throws Exception {
        // 获得所有的public方法
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            // 判断是否为数据初始化方法
            if (isInitDataMethod(method)) {
                method.invoke(this);
            }
        }
    }

    // 判断是否是数据初始化方法, 按照约定的规则进行校验
    private boolean isInitDataMethod(Method method) {
        if (method == null) {
            return false;
        }
        return method.getName().startsWith("init")  // init开始
                && Modifier.isPublic(method.getModifiers())  // 公开方法
                && method.getReturnType().equals("void")  // 无返回值 void
                && !method.isVarArgs()  // 输出参数为空
                && !Modifier.isAbstract(method.getModifiers());  // 不能是抽象方法
    }
}
