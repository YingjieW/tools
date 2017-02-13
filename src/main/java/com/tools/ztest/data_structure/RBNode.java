package com.tools.ztest.data_structure;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/10 上午11:01
 */
public class RBNode<T extends Comparable> {

    public T value;

    public boolean color;

    public RBNode<T> left;

    public RBNode<T> right;

    public RBNode<T> parent;

    public RBNode() {}

    public RBNode(T value) {
        this(value, false);
    }

    public RBNode(T value, boolean isRed) {
        this(value, isRed, null, null, null);
    }

    public RBNode(T value, boolean color, RBNode<T> left, RBNode<T> right, RBNode<T> parent) {
        this.value = value;
        this.color = color;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "[value:" + value + ", color:" + (color ? "red" : "black") +
                ", left:" + (left==null ? null : left.value) +
                ", right:" + (right == null ? null : right.value) +
                ", parent:" + (parent == null ? null : parent.value) + "]";
    }
}
