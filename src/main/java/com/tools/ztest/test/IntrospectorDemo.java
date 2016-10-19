package com.tools.ztest.test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/10/14 下午10:19
 */
public class IntrospectorDemo {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        IntrospectorDemo introspectorDemo = new IntrospectorDemo();
        introspectorDemo.setName("name_init");

        BeanInfo beanInfo = Introspector.getBeanInfo(introspectorDemo.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if ("name".equalsIgnoreCase(propertyDescriptor.getName())) {
                System.out.println("---> " + propertyDescriptor.getReadMethod().invoke(introspectorDemo, null));
                propertyDescriptor.getWriteMethod().invoke(introspectorDemo, "name_rewrite");
                System.out.println("===> " + propertyDescriptor.getReadMethod().invoke(introspectorDemo, null));
            }
        }
    }
}
