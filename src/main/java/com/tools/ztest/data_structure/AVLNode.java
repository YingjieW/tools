package com.tools.ztest.data_structure;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/4 下午6:04
 */
public class AVLNode<K extends Comparable, V> {

    public K key;

    public V value;

    public int height = 1;

    /** height(left) - height(right) */
    public int balance = 0;

    public AVLNode<K,V> left = null;

    public AVLNode<K,V> right = null;

    public AVLNode<K,V> parent = null;

    public AVLNode(){}

    public AVLNode(K key, V value) {
        this(key, value, null, null, null);
    }

    public AVLNode(K key, V value, AVLNode<K,V> left, AVLNode<K,V> right, AVLNode<K,V> parent) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "[key:" + key + ", value:" + value + ", height:" + height + ", balance:" + balance +
                ", parent:" + (parent==null?null:parent.key) + ", left:" + (left ==null?null:left.key) +
                ", right:" + (right == null ? null : right.key) + "]";
    }
}
