package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/6 上午10:52
 */
public class SlidingWindowDemo {

    public static long timeStamp = System.currentTimeMillis();
    // 最大请求数
    public static long limit = 500;
    // 小窗口数量
    public static int size = 6;
    // 小窗口时间间隔
    public static int smallWindowInterval = 10;
    // 大窗口时间间隔
    public static int largeWindowInterval = size * smallWindowInterval;
    // (0, size-1)用于存储各个小窗口的请求数量, size用于存储总数
    public static int[] counter = new int[size];
    // 总请求数
    public static int totalRequest = 0;

    public boolean grant() {
        long now = System.currentTimeMillis();
        int span = (int)(now - timeStamp)/smallWindowInterval;
        if(span < size)
        // 时间跨度在一个大时间窗口内,
        // 计算其应该落在哪个小窗口,然后小窗口及总请求数加1
        {
            counter[span]++;
            totalRequest++;
            return limit > totalRequest;
        } else if(span < size * 2)
        // 时间跨度超出一个大窗口时间,但小于两个大窗口的时间
        // 1. counter[0, index+1)间的元素置为0
        // 2. 循环左移index+1位
        // 3. counter[size-1 - index]加1
        // 4. 重新计算总请求数
        // 5. 重新定位时间timeStamp
        {
            int index = span % size;
            emptyArr(counter, index + 1);
            leftRotate(counter, index + 1);
            counter[size-1 - index]++;
            totalRequest = getArraySum(counter);
            timeStamp = timeStamp + (index+1)*smallWindowInterval;
            return limit > totalRequest;
        } else
        // 时间跨度超出了两个大窗口时间(index + 1 >= size *2)
        // 则清空counter,totalRequest值零,重新开始计数
        {
            emptyArr(counter, counter.length);
            counter[0]++;
            totalRequest = 1;
            timeStamp = now;
            return true;
        }
    }

    /**
     * [0, index)间元素置为0
     */
    public void emptyArr(int[] arr, int index) {
        if(index >= arr.length) {
            throw new RuntimeException("index[" + index + "] must be less than arr.length[" + arr.length + "].");
        }
        for(int i = 0; i < index; i++) {
            arr[i] = 0;
        }
    }

    public int getArraySum(int[] arr) {
        int sum = 0;
        for(int a : arr) {
            sum = sum + a;
        }
        return sum;
    }

    /**
     * 循环左移num位
     */
    public void leftRotate(int[] arr, int num) {
        reverse(arr, 0, num-1);
        reverse(arr, num, arr.length-1);
        reverse(arr, 0, arr.length-1);
    }

    /**
     * 数组翻转
     */
    public void reverse(int[] arr, int from, int to) {
        if(from < 0 || to >= arr.length || from >= to) {
            throw new IllegalArgumentException("Illegal argument: from[" + from + "], to[" + to + "].");
        }
        int temp;
        while(from < to) {
            temp = arr[from];
            arr[from] = arr[to];
            arr[to] = temp;
            from++;
            to--;
        }
    }

    /**
     * 循环右移num位,时间复杂度O(n)
     * @param arr
     * @param num
     */
    public void rightRotate(int[] arr, int num) {
        reverse(arr, 0, arr.length-num-1);
        reverse(arr, arr.length-num, arr.length-1);
        reverse(arr, 0, arr.length-1);
    }



    public static void main(String[] args) {
        int[] arr = {0,0,0,3,4,5,6,7,8,9};
        print(arr);
        SlidingWindowDemo slidingWindowDemo = new SlidingWindowDemo();
//        slidingWindowDemo.rightRotate(arr, 3);
        slidingWindowDemo.leftRotate(arr, 3);
        print(arr);
    }

    public static void print(int[] arr) {
        for(int i : arr) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }
}
