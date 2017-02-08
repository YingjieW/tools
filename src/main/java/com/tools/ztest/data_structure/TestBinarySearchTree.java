package com.tools.ztest.data_structure;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/21 下午10:01
 */
public class TestBinarySearchTree {

    public static void main(String[] args) throws Exception {
        Integer[] integers = {6, 2, 1, 4, 3, 5, 9, 7, 8, 10};
        BinarySearchTree tree = new BinarySearchTree();
        for (Integer i : integers) {
            tree.put(i, i);
        }

        tree.remove(6);
        tree.remove(9);

        tree.preorderRecursively();
        tree.preorderNonrecursively();

        tree.inorderRecursively();
        tree.inorderNonrecursively();

        tree.postorderRecursively();
        tree.postorderNonrecursively();

        System.out.println(tree.get(7));
    }
}
