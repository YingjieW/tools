package com.tools.ztest.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/5 下午12:10
 */
public class PhantomReferenceTest {

    public static void main(String[] args) throws Exception {
        // 创建引用队列
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        // 创建一个强引用
        String str = new String("Hello world.");
        // 创建一个虚引用
        PhantomReference phantomReference = new PhantomReference(str, referenceQueue);
        // 去掉强引用,这样该对象只剩虚引用,才能够被垃圾回收
        str = null;
        // 垃圾回收,for循环提高垃圾回收触发
        for(int i = 0; i < 100; i++) {
            System.gc();
        }
        // 下次使用时,获取该虚引用
        String strTest = (String) phantomReference.get();
        // 如果虚引用被回收,则重新获取(或进行你所需要的操作)
        if(strTest == null) {
            strTest = new String("Reborn");
        }
        // 被回收的软引用会被放进引用队列,对已回收的对象进行需要的操作
        Reference reference = null;
        while((reference = referenceQueue.poll()) != null) {
            System.out.println(strTest);
        }
        // 控制台输出: Reborn
    }
}
