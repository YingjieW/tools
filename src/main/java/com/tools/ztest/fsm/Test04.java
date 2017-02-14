package com.tools.ztest.fsm;

import com.tools.util.FileUtils;

import java.io.File;
import java.io.FileReader;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/9 下午2:28
 */
public class Test04 {
    enum State {
        BEFORE, INSIDE, AFTER
    }
    public static void main(String[] args) throws Exception {
        File file = FileUtils.createFile("/Users/YJ/Downloads/hello.txt");
        FileReader fileReader = new FileReader(file);
        int temp = -1;
        State state = State.BEFORE;
        while((temp = fileReader.read()) != -1) {
            if (temp == '\n') {
                System.out.println();
                state = State.BEFORE;
            } else {
                switch (state) {
                    case BEFORE:
                        if (temp != ' ') {
                            System.out.print((char) temp);
                            state = State.INSIDE;
                        }
                        break;
                    case INSIDE:
                        if (temp == ' ') {
                            state = State.AFTER;
                        } else {
                            System.out.print((char) temp);
                        }
                        break;
                    case AFTER:
                        break;
                    default:
                        throw new RuntimeException("Unknown state exception.");
                }
            }
        }
        fileReader.close();
    }
}
