package com.tools.ztest.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/10/2 上午10:58
 */
public class UnSafeTest {
//    public static void main(String[] args) {
//        try {
//            Unsafe unsafe = Unsafe.getUnsafe();
//            User user = new User();
//            long ageOffset = unsafe.objectFieldOffset(User.class.getField("age"));
//            unsafe.putInt(user, ageOffset, 10);
//            System.out.println(user.getAge());
//            System.out.println(unsafe);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        try {
//            User user = new User();
//            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
//            theUnsafeField.setAccessible(true);
//            Unsafe UNSAFE = (Unsafe) theUnsafeField.get(null);
//            System.out.println(UNSAFE);
//            Field filed = user.getClass().getDeclaredField("age");
//            long ageOffset = UNSAFE.objectFieldOffset(filed);
//            UNSAFE.putInt(user, ageOffset, 10);
//            System.out.println(user.getAge());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        try {
//            Constructor<Unsafe> con = Unsafe.class.getDeclaredConstructor();
//            // 用该私有构造方法创建对象
//            // IllegalAccessException:非法的访问异常。
//            // 暴力访问
//            con.setAccessible(true);// 值为true则指示反射的对象在使用时应该取消Java语言访问检查。
//            Unsafe UNSAFE = con.newInstance();
//            System.out.println(UNSAFE);
//
//            User user = new User();
//            Field filed = user.getClass().getDeclaredField("age");
//            long ageOffset = UNSAFE.objectFieldOffset(filed);
//            UNSAFE.putInt(user, ageOffset, 10);
//            System.out.println(user.getAge());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        try {
            Constructor<Unsafe> con = (Constructor<Unsafe>) Class.forName("sun.misc.Unsafe").getDeclaredConstructor();
            con.setAccessible(true);
            User user = new User();
            Unsafe UNSAFE = con.newInstance(null);
            Field filed = user.getClass().getDeclaredField("age");
            long startTime1 = System.currentTimeMillis();
            for(int i=0;i<1000000;i++){
                user.setAge(i);
            }
            System.out.println(System.currentTimeMillis() - startTime1);

            long ageOffset = UNSAFE.objectFieldOffset(filed);
            long startTime2 = System.currentTimeMillis();
            for(int i=0;i<1000000;i++){
                UNSAFE.putInt(user, ageOffset, i);
            }
            System.out.println(System.currentTimeMillis() - startTime1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
