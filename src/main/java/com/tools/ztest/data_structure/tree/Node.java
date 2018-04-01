package com.tools.ztest.data_structure.tree;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/4/1 上午10:54
 */
public class Node {
    private int data;

    private Node left;

    private Node right;

    public Node(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
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
