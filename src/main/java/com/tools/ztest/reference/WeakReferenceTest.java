package com.tools.ztest.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/5 上午10:08
 */
public class WeakReferenceTest {

    public static void main(String[] args) throws Exception {
        // 创建引用队列
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        // 创建一个强引用
        String str = new String("Hello world.");
        // 创建一个弱引用
        WeakReference weakReference = new WeakReference(str, referenceQueue);
        // 去掉强引用,这样该对象只剩弱引用,才能够被垃圾回收
        str = null;
        // 垃圾回收,for循环提高垃圾回收触发
        for(int i = 0; i < 100; i++) {
            System.gc();
        }
        // 下次使用时,获取该弱引用
        String strTest = (String) weakReference.get();
        // 如果弱引用被回收,则重新获取(或进行你所需要的操作)
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
