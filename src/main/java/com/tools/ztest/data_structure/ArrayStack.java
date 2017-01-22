package com.tools.ztest.data_structure;

import com.alibaba.fastjson.JSON;

/**
 * Descripe: 单线程可用、多线程不安全
 *
 * @author yingjie.wang
 * @since 17/1/22 下午4:29
 */
public class ArrayStack<E> {

    private final int DEFAULT_SIZE = 10;

    private E[] elements;

    private int top = -1;

    private int size = 0;

    public ArrayStack() {
        elements = (E[]) new Object[DEFAULT_SIZE];
    }

    public E push(E e) {
        if (size == elements.length) {
            // 扩容: size*1.75
            resize();
        }
        elements[++top] = e;
        size++;
        return e;
    }

    public E pop() {
        if (size == 0) {
            throw new RuntimeException("ArrayStack is empty.");
        }
        size--;
        return (E) elements[top--];
    }

    private void resize() {
        E[] newElements = (E[]) new Object[(int)(elements.length * 1.75)];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public String toString() {
        return "---> top: " + top + ", size: " + size + ", elements: " + JSON.toJSONString(elements);
    }
}
