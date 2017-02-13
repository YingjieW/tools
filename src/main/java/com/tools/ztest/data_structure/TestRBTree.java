package com.tools.ztest.data_structure;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/10 下午2:18
 */
public class TestRBTree {
    private static void testAdd() throws Exception {
        Integer[] integers = {12, 1, 9, 2, 0, 11, 7, 19, 4, 15, 18, 5, 14, 13, 10, 16, 6, 3, 8, 17};
        RBTree<Integer> tree = new RBTree<Integer>();
        for (Integer i : integers) {
            tree.add(i);
        }
        System.out.println(tree);
    }

    public static void main(String[] args) throws Exception {
        testAdd();
    }
}
