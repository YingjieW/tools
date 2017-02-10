package com.tools.ztest.data_structure;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/10 下午2:18
 */
public class TestRBTree {
    private static void testAdd() throws Exception {
        RBTree<Integer> tree = new RBTree<Integer>();
        tree.add(12);
//        System.out.println("12: " + tree);
        tree.add(1);
//        System.out.println(" 1: " + tree);
        tree.add(9);
//        System.out.println(" 9: " + tree);
        tree.add(2);
//        System.out.println(" 2: " + tree);
        tree.add(0);
//        System.out.println(" 0: " + tree);
        // 12 1 9 2 0 11 7 19 4 15 18 5 14 13 10 16 6 3 8 17
        tree.add(11);
        tree.add(7);
        tree.add(19);
        tree.add(4);
//        System.out.println(" 4: " + tree);
        tree.add(15);
//        System.out.println("15: " + tree);
        tree.add(18);
//        System.out.println("18: " + tree);
        tree.add(5);
//        System.out.println(" 5: " + tree);
        tree.add(14);
//        System.out.println("14: " + tree);
        tree.add(13);
//        System.out.println("13: " + tree);
        tree.add(10);
//        System.out.println("10: " + tree);
        tree.add(16);
//        System.out.println("16: " + tree);
        tree.add(6);
//        System.out.println(" 6: " + tree);
        tree.add(3);
//        System.out.println(" 3: " + tree);
        tree.add(8);
//        System.out.println(" 8: " + tree);
        tree.add(17);
//        System.out.println("17: " + tree);
        System.out.println(tree);
    }

    public static void main(String[] args) throws Exception {
        testAdd();
    }
}
