package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/2 下午11:31
 */
public class TestException {

    public TestException() {
    }

    @SuppressWarnings("finally")
    boolean testEx() throws Exception {
        boolean ret = true;
        try {
            ret = testEx1();
        } catch (Exception e) {
            System.out.println("testEx, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx, finally; return value=" + ret);
            return ret;
        }
    }

    @SuppressWarnings("finally")
    boolean testEx1() throws Exception {
        boolean ret = true;
        try {
            ret = testEx2();
            if (!ret) {
                return false;
            }
            System.out.println("testEx1, at the end of try");
            return ret;
        } catch (Exception e) {
            System.out.println("testEx1, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx1, finally; return value=" + ret);
            return ret;
        }
    }

    @SuppressWarnings("finally")
    boolean testEx2() throws Exception {
        boolean ret = true;
        try {
            int b = 12;
            for (int i = 2; i >= -2; i--) {
                int c = b / i;
                System.out.println("i=" + i);
            }
            return true;
        } catch (Exception e) {
            System.out.println("testEx2, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx2, finally; return value=" + ret);
//            return ret;
        }
    }

    public static void main(String[] args) {

        TestException testException = new TestException();
        try {
            testException.testEx();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
