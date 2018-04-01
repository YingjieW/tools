package com.tools.ztest.data_structure.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/4/1 上午11:24
 */
public class LevelTraverseBinaryTree {
    /**
     * 编程之美 分层遍历二叉树
     * 之前已经用队列实现过二叉树的层次遍历，但这次要求输出换行，因此要标记什么时候要换行：
     * 用inCount记录某层有多少个元素，outCount记录当前输出了多少个元素；
     * 当inCount==outCount时，就说明某层元素已经完全输出，此时应该换行(outCount清零)
     * 此外，观察发现，当第K层元素全部出队（并已将各自左右孩子入队）后，
     * 队列里面刚好存放了第K+1层的所有元素，不多不少，所以有：inCount = queue.size();
     *
     * 书上的扩展问题也很有意思（从下往上分层输出），既然是反过来输出，第一反应是利用栈
     * 但同时又要记录何时换行（每行有多少个元素），只好用ArrayList模拟一个“伪栈”：
     * 1、第一步操作和“从上往下分层输出”是类似的：从root开始遍历，并将所有元素放入“队列”（ArrayList），用-1表示一层的结束
     * 2、输出。不是从队头开始，而是从队尾开始，依次输出
     * 分析到这里，这里面的ArrayList定义为“双向队列”更合适
     */

    public static void traverseByLevelFromTop(Node node) {
        if (node == null) {
            return;
        }
        LinkedList<Node> nodeList = new LinkedList<>();
        nodeList.add(node);
        int levelSize = 1;
        int outCount = 0;
        while (!nodeList.isEmpty()) {
            node = nodeList.pollFirst();
            System.out.print(node.getData() + " ");
            outCount++;
            if (node.getLeft() != null) {
                nodeList.add(node.getLeft());
            }
            if (node.getRight() != null) {
                nodeList.add(node.getRight());
            }
            if (levelSize == outCount) {
                System.out.println();
                levelSize = nodeList.size();
                outCount = 0;
            }
        }
    }

    public static void traverseByLevelFromBottom(Node node) {
        if (node == null) {
            return;
        }
        Node dummy = new Node(-1);
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(node);
        nodeList.add(dummy);
        int size = nodeList.size();
        int i = 0;
        while (i < size) {
            Node currentNode = nodeList.get(i);
            if (currentNode.getRight() != null) {
                nodeList.add(currentNode.getRight());
            }
            if (currentNode.getLeft() != null) {
                nodeList.add(currentNode.getLeft());
            }
            i++;
            if (i == size) {
                //已经遍历到最底层的最后一个元素，结束循环
                if (currentNode != dummy && currentNode.getLeft() == null && currentNode.getRight() == null) {
                    break;
                }
                size = nodeList.size();
                nodeList.add(dummy);
            }
        }

        //从后往前遍历，相当于“栈”
        for (int j = nodeList.size() - 2; j >= 0; j--) {
            if (nodeList.get(j) == dummy) {
                System.out.println();
                continue;
            }
            System.out.print(nodeList.get(j).getData() + " ");
        }
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
        LevelTraverseBinaryTree.traverseByLevelFromTop(root);
        System.out.println("-------------");
        LevelTraverseBinaryTree.traverseByLevelFromBottom(root);
    }
}
