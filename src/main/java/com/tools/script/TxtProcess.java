package com.tools.script;

import com.tools.utils.FileUtils;

import java.io.File;
import java.util.List;


public class TxtProcess {

    public static void main(String[] args) throws Exception{
        String sourcePath = "/Users/YJ/Documents/test/testFile.txt";
        File source = new File(sourcePath);
        if(!source.exists()) {
            System.out.println("#####   source: " + sourcePath + " does not exist.");
            return;
        }
        
        // 读取每行的[0,7)
        List<String> list = FileUtils.readSelectively(source, 0, 7);
//        for(String str : list) {
//            System.out.println(str);
//        }
        
        // 将读取的字段，写入文件，每个字段一行
        FileUtils.writeByList(list, FileUtils.createFile(FileUtils.createNewPath(sourcePath)), true);
        //copy(source, createFile(createNewPath(sourcePath)));
    }
}
