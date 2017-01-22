package com.tools.ztest.data_structure;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/22 下午6:01
 */
public class ArrayQueue<E> {

    private final int DEFAULT_SIZE = 10;

    private E[] elements;

    private int size = 0;

    private int head = 0;

    private int tail = 0;

    public ArrayQueue() {
        elements = (E[]) new Object[DEFAULT_SIZE];
    }

    public E add(E e) {
        if (size == elements.length) {
            // 扩容 size*1.75
            resize();
        }
        elements[tail] = e;
        tail = (tail + 1) % elements.length;
        size++;
        return e;
    }

    private void resize() {
        E[] newElements = (E[]) new Object[(int)(elements.length * 1.75)];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    public E remove() {
        if (size == 0) {
            throw new RuntimeException("ArrayQueue is empty.");
        }
        int i = head;
        head = (head + 1) % elements.length;
        size--;
        return elements[i];
    }

    public String toString() {
        return "---> head: " + head + ", tail: " + tail + ", size: " + size + ", elements: " + JSON.toJSONString(elements);
    }
}
