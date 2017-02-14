package com.tools.util;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/10/8 下午5:26
 */
final public class MessageUtils {

    private static final String DELIMETER = "{}";
    private static final char ESCAPE_CHAR = '\\';
    private static final int ARG_LENGTH = 10;

    public static String format(String messagePattern) {
        return format(messagePattern, null);
    }

    public static String format(String messagePattern, Object...args) {
        if(messagePattern == null || args == null) {
            return messagePattern;
        }

        StringBuffer buffer = new StringBuffer(messagePattern.length() + args.length*ARG_LENGTH);
        int i = 0, j = 0, k = 0;
        for (; k < args.length; k++) {
            j = messagePattern.indexOf(DELIMETER, i);
            if (j == -1) {
                if (i == 0) {
                    return messagePattern;
                } else {
                    buffer.append(messagePattern.substring(i));
                    return buffer.toString();
                }
            } else {
                if (isEscapedDelimeter(messagePattern,j)) {
                    if (isDoubleEscaped(messagePattern,j)) {
                        // the escape character preceding the delimiter start is itself escaped: "abc x:\\{}".
                        buffer.append(messagePattern.substring(i, j-1)).append(getString(args[k]));
                    } else {
                        k--; // DELIMETER was escaped, thus should not be incremented.
                        buffer.append(messagePattern.substring(i, j-1)).append(DELIMETER);
                    }
                } else {
                    buffer.append(messagePattern.substring(i,j)).append(getString(args[k]));
                }
                i = j + 2;
            }
        }
        buffer.append(messagePattern.substring(i));
        return buffer.toString();
    }

    private static boolean isEscapedDelimeter(String messagePattern, int delimiterStartIndex) {
        if (delimiterStartIndex == 0) {
            return false;
        }
        return messagePattern.charAt(delimiterStartIndex-1) == ESCAPE_CHAR;
    }

    private static boolean isDoubleEscaped(String messagePattern, int delimiterStartIndex) {
        if (delimiterStartIndex < 2) {
            return false;
        }
        return messagePattern.charAt(delimiterStartIndex-2) == ESCAPE_CHAR;
    }

    private static String getString(Object o) {
        return o == null ? "null" : o.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println(MessageUtils.format("Test...."));
        System.out.println(MessageUtils.format("Test:{}", false));
        System.out.println(MessageUtils.format("Test:{}, a:{}, {}", false, 1, "kkkk"));
        System.out.println(MessageUtils.format("Test:\\{}, a:{}, {}", false, 1, "kkkk"));
        System.out.println(MessageUtils.format("Test:\\\\{}, a:{}, {}", false, 1, "kkkk"));
        System.out.println(MessageUtils.format("Test:{}, a:{}, ={}, +{}", false, 1, "kkkk", null));
    }
}
