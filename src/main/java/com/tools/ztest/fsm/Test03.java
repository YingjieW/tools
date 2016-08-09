package com.tools.ztest.fsm;

import com.tools.utils.FileUtils;

import java.io.File;
import java.io.FileReader;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/9 下午2:05
 */
public class Test03 {
    enum State {
        BEFORE, INSIDE, AFTER
    }
    public static void main(String[] args) throws Exception {
        File file = FileUtils.createFile("/Users/YJ/Downloads/hello.txt");
        FileReader fileReader = new FileReader(file);
        int temp = -1;
        State state = State.BEFORE;
        while((temp = fileReader.read()) != -1) {
            switch (state) {
                case BEFORE:
                    if(temp == '\n') {
                        System.out.println();
                    } else if(temp != ' ') {
                        System.out.print((char)temp);
                        state = State.INSIDE;
                    }
                    break;
                case INSIDE:
                    if(temp == '\n') {
                        System.out.println();
                        state = State.BEFORE;
                    } else if(temp == ' ') {
                        state = State.AFTER;
                    } else {
                        System.out.print((char)temp);
                    }
                    break;
                case AFTER:
                    if(temp == '\n') {
                        System.out.println();
                        state = State.BEFORE;
                    }
                    break;
                default:
                    throw new RuntimeException("Unknown state exception.");
            }
        }
        fileReader.close();
    }
}
