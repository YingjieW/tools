package com.tools.ztest.data_structure;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/8 下午2:42
 */
public class TestAVLTree {

    public static void main(String[] args) throws Exception {
        /** 左左-简单模式 */
//        Integer[] integers = {3, 2, 1};
        /** 左左-复杂模式 */
//        Integer[] integers = {5, 3, 6, 2, 4, 1};
        /** 右右-简单模式 */
//        Integer[] integers = {1, 2, 3};
        /** 右右-复杂模式 */
//        Integer[] integers = {2, 1, 4, 3, 5, 6};
        /** 左右-简单模式 */
//        Integer[] integers = {3, 1, 2};
        /** 左右-复杂模式(同左左模式,仅右转一次) */
//        Integer[] integers = {5, 3, 6, 1, 4, 2};
        /** 右左-简单模式 */
//        Integer[] integers = {1, 3, 2};
        /** 右左-复杂模式(同右右模式,仅左转一次) */
//        Integer[] integers = {2, 1, 4, 3, 6, 5};

        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        AVLTree<Integer, Integer> tree = new AVLTree<Integer, Integer>();
        for (Integer i : integers) {
            tree.add(i, i);
        }
        System.out.println(tree);
    }
}
