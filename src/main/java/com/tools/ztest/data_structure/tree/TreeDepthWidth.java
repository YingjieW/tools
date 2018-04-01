package com.tools.ztest.data_structure.tree;

import java.util.LinkedList;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/4/1 下午3:34
 */
public class TreeDepthWidth {

    public static int getMaxDepth(Node node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = getMaxDepth(node.getLeft());
        int rightDepth = getMaxDepth(node.getRight());
        return 1 + Math.max(leftDepth, rightDepth);
    }

    public static int getMinDepth(Node node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = getMinDepth(node.getLeft());
        int rightDepth = getMinDepth(node.getRight());
        return 1 + Math.min(leftDepth, rightDepth);
    }

    public static int getMaxWidth(Node root) {
        if (root == null) {
            return 0;
        }
        LinkedList<Node> nodeList = new LinkedList<>();
        nodeList.add(root);
        int levelSize = nodeList.size();
        int outCount = 0;
        int maxWidth = levelSize;
        while (!nodeList.isEmpty()) {
            Node node = nodeList.pollFirst();
            if (node.getLeft() != null) {
                nodeList.add(node.getLeft());
            }
            if (node.getRight() != null) {
                nodeList.add(node.getRight());
            }
            outCount++;
            if (outCount == levelSize) {
                levelSize = nodeList.size();
                outCount = 0;
                maxWidth = Math.max(maxWidth, levelSize);
            }
        }
        return maxWidth;
    }

    public static void main(String[] args) {
         /*
                            __1__
                          /      \
                     __2__       __3__
                    /     \     /     \
                 __4       5   6       7
                /
               8
         */
        int[] src = { 1, 2, 3, 4, 5, 6, 7, 8 };
        Node root = Tree.createTree(src);
        System.out.println("maxDepth: " + getMaxDepth(root));
        System.out.println("minDepth: " + getMinDepth(root));
        System.out.println("maxWidth: " + getMaxWidth(root));
    }

}

