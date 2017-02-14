package com.tools.ztest.fsm;

import com.tools.util.FileUtils;

import java.io.File;
import java.io.FileReader;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/9 下午2:31
 */
public class Test05 {
    enum State {
        BEFORE, INSIDE, AFTER
    }
    private static State step(State state, int c) {
        if(c == '\n') {
            System.out.println();
            return State.BEFORE;
        } else {
            switch (state) {
                case BEFORE:
                    if(c != ' ') {
                        System.out.print((char)c);
                        return State.INSIDE;
                    }
                    return State.BEFORE;
                case INSIDE:
                    if(c == ' ') {
                        return State.AFTER;
                    } else {
                        System.out.print((char)c);
                    }
                    return State.INSIDE;
                case AFTER:
                    return State.AFTER;
                default:
                    throw new RuntimeException("Unknown State exception.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File file = FileUtils.createFile("/Users/YJ/Downloads/hello.txt");
        FileReader fileReader = new FileReader(file);
        int temp = -1;
        State state = State.BEFORE;
        while((temp = fileReader.read()) != -1) {
            state = step(state, temp);
        }
        fileReader.close();
    }
}
