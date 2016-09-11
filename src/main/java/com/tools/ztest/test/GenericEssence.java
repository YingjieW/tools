package com.tools.ztest.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Descripe: http://www.jianshu.com/p/1a60d55a94cd
 *
 * @author yingjie.wang
 * @since 16/9/9 下午11:36
 */
public class GenericEssence {

    public static void main(String[] args) {

        // 没有泛型
        List list1 = new ArrayList();
        // 有泛型
        List<String> list2 = new ArrayList<String>();

        // 1.首先观察正常添加元素方式，在编译器检查泛型,
        //   这个时候如果list2添加int类型会报错
        list2.add("hello");
        // 报错！list2有泛型限制，只能添加String，添加int报错
        // list2.add(20);
        // 此时list2长度为1
        System.out.println("list2的长度是：" + list2.size());


        // 2.然后通过反射添加元素方式，在运行期动态加载类，首先得到list1和list2的类类型相同，
        //   然后再通过方法反射绕过编译器来调用add方法，看能否插入int型的元素
        Class c1 = list1.getClass();
        Class c2 = list2.getClass();
        // 结果：true，说明类类型完全相同
        System.out.println(c1 == c2);

        // 验证：我们可以通过方法的反射来给list2添加元素，这样可以绕过编译检查
        try {
            // 通过方法反射得到add方法
            Method m = c2.getMethod("add", Object.class);
            // 给list2添加一个int型的，上面显示在编译器是会报错的
            m.invoke(list2, 20);
            // 结果：2，说明list2长度增加了，并没有泛型检查
            System.out.println("list2的长度是：" + list2.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * 综上可以看出，在编译器的时候，泛型会限制集合内元素类型保持一致，但是编译器结束进入
         * 运行期以后，泛型就不再起作用了，即使是不同类型的元素也可以插入集合。
         */
    }
}
