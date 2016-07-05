package com.tools.ztest.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/5 上午9:47
 */
public class SoftReferenceTest {

    public static void main(String[] args) throws Exception {
        // 创建引用队列
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        // 创建一个强引用
        String str = new String("Hello world.");
        // 创建一个软引用
        SoftReference softReference = new SoftReference(str, referenceQueue);
        // 下次使用时,获取该软引用
        String strTest = (String) softReference.get();
        // 如果软引用被回收,则重新获取(或进行你所需要的操作)
        if(strTest == null) {
            strTest = new String("Reborn");
        }
        // 被回收的软引用会被放进引用队列,对已回收的对象进行需要的操作
        Reference reference = null;
        while((reference = referenceQueue.poll()) != null) {
            // TODO
            System.out.println("--");
        }
    }
}
