package com.tools.ztest.sort;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/10/1 下午3:13
 */
public class Lcs {

    public static void main(String[] args) {
        String str1 = "01234";
        String str2 = "a0bcd";
        System.out.println(lcs(str1, str2));
    }

    private static int lcs(String str1, String str2) {
        int[][] arr = new int[str1.length()+1][str2.length()+1];
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    arr[i][j] = arr[i-1][j-1] + 1;
                } else {
                    arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
                }
            }
        }
        return arr[str1.length()][str2.length()];
    }
}
