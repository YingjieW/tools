package com.tools.ztest.tree;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/20 下午10:17
 */
public class Node<K, V> {

    private K key;

    private V value;

    private Node left;

    private Node right;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
