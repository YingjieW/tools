package com.tools.ztest.fsm;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/8 下午4:47
 */
public class Test01 {

    // 输入一个大于0的十进制, 然后判断该十进制对应的二进制串中0的个数是奇数还是偶数?
    public static void main(String[] args) throws Exception {
        // 十进制转成二进制字符串
        String binaryString = Integer.toBinaryString(14);
        System.out.println("binaryString: " + binaryString);
        // 初始状态: EVEN
        IntegerStateEnum state = IntegerStateEnum.EVEN;
        for(int i = 0; i < binaryString.length(); i++) {
            // 也可以把check()方法放在循环中,而不是放在枚举中
            if(IntegerStateEnum.EVEN.equals(state)) {
                state = IntegerStateEnum.EVEN.check(binaryString.charAt(i));
            } else {
                state = IntegerStateEnum.ODD.check(binaryString.charAt(i));
            }
        }
        System.out.println("state: " + state);
    }

}
