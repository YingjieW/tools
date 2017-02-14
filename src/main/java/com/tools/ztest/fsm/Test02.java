package com.tools.ztest.fsm;

import com.tools.util.FileUtils;

import java.io.File;
import java.io.FileReader;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/9 上午11:47
 */
public class Test02 {
    public static void main(String[] args) throws Exception {
        File file = FileUtils.createFile("/Users/YJ/Downloads/hello.txt");
        FileReader fileReader = new FileReader(file);
        int temp = -1;
        while((temp = fileReader.read()) != -1) {
            while(temp == ' ') {
                temp = fileReader.read();
            }
            while(temp != ' ' && temp != -1 && temp !='\n') {
                System.out.print((char)temp);
                temp = fileReader.read();
            }
            System.out.println();
            while(temp != -1 && temp != '\n') {
                temp = fileReader.read();
            }
        }
        fileReader.close();
    }
}
