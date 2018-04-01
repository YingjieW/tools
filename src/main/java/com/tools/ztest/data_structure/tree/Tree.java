package com.tools.ztest.data_structure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/4/1 上午10:55
 */
public class Tree {

    public static Node createTree(int[] datas) {
        if (datas == null) {
            return null;
        }
        List<Node> nodeList = new ArrayList<>();
        for (int data : datas) {
            Node node = new Node(data);
            nodeList.add(node);
        }
        // 关键就是如何找到父节点
        int lastRootIndex = (datas.length - 1) / 2;
        for (int i = 0; i <= lastRootIndex; i++) {
            Node parent = nodeList.get(i);
            int leftIndex = 2*i + 1;
            int rightIndex = 2*i + 2;
            parent.setLeft(leftIndex < nodeList.size() ? nodeList.get(leftIndex) : null);
            parent.setRight(rightIndex < nodeList.size() ? nodeList.get(rightIndex) : null);
        }
        return nodeList.get(0);
    }
}
