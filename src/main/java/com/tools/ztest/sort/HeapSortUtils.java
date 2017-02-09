package com.tools.ztest.sort;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/3 下午2:07
 */
public class HeapSortUtils {

    public static <E extends Comparable> E[] sort(E[] arr) {
        if (arr == null || arr.length == 1) {
            return arr;
        }
        buildMaxHeapify(arr);
        maxHeapSort(arr);
        return arr;
    }

    private static <E extends Comparable> void buildMaxHeapify(E[] arr) {
        /** 有子节点的才需要创建最大堆, 从最后一个的父节点开始 */
        int startIndex = (arr.length-1)/2;
        /** 从startIndex开始创建最大堆, 每次创建的堆都是其父节点下的最大堆,
         * for循环执行完毕后, arr[0]即为最大值 */
        for (int i = startIndex; i >= 0; i--) {
            maxHeapify(arr, arr.length, i);
        }
    }

    /**
     * 创建一个最大堆
     * @param arr
     * @param heapSize
     *          需要创建最大堆的大小，一般在sort的时候用到，因为最多值放在末尾，末尾就不再归入最大堆了
     * @param parent
     *          当前需要创建最大堆的位置
     * @param <E>
     */
    private static <E extends  Comparable> void maxHeapify(E[] arr, int heapSize, int parent) {
        int leftChild = parent*2 + 1;
        int rightChild = parent*2 + 2;

        int largest = parent;
        if (leftChild < heapSize && arr[leftChild].compareTo(arr[largest]) > 0) {
            largest = leftChild;
        }
        if (rightChild < heapSize && arr[rightChild].compareTo(arr[largest]) > 0) {
            largest = rightChild;
        }

        /** 当parent不是最大值时, 将最大值(其子节点)与parent互换, 此时, 该子节点也需要重新调整 */
        if (largest != parent) {
            E temp = arr[parent];
            arr[parent] = arr[largest];
            arr[largest] = temp;
            maxHeapify(arr, heapSize, largest);
        }
    }

    private static <E extends Comparable> void maxHeapSort(E[] arr) {
        /** 头尾互换, 然后调整堆 */
        E temp = null;
        for (int i = arr.length - 1; i > 0; i--) {
            temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            maxHeapify(arr, i, 0);
        }
    }

    public static void main(String[] args) throws Exception {
        Integer[] datas = {0, 2, 7, 3, 4, 9, 8, 1, 5, 6};
        System.out.println("---> " + JSON.toJSONString(datas));
        Integer[] result = HeapSortUtils.sort(datas);
        System.out.println("---> " + JSON.toJSONString(datas));
    }


    /** 将非null的元素copy至另一个数组中 */
    private static <E extends Comparable> E[] getNonNullElements(E[] arr) {
        int left = 0;
        int right = arr.length - 1;
        /** 左边非null, 右边null*/
        E temp = null;
        while (left < right) {
            if (arr[right] == null) {
                right--;
                continue;
            }
            if (arr[left] == null) {
                temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                right--;
                left++;
            } else {
                left++;
            }
        }
        if (right == 0 && arr[0] == null) {
            return null;
        }
        /** E[] newArr = (E[]) (new Object[right+1]); 运行是错误,不能进行强转 */
        E[] newArr = (E[]) (new Object[right+1]);
        System.arraycopy(arr, 0, newArr, 0, right+1);
        return newArr;
    }
}
