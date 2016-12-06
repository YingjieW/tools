package com.tools.ztest.script;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class TxtProcess {

    public static void main(String[] args) throws Exception{
        String sourcePath = "/Users/YJ/Downloads/testFile.txt";
        File source = new File(sourcePath);
        if(!source.exists()) {
            System.out.println("#####   source: " + sourcePath + " does not exist.");
            return;
        }
        
        // 读取每行的[0,7)
//        List<String> list = FileUtils.readSelectively(source, 0, 7);
//        for(String str : list) {
//            System.out.println(str);
//        }
        
        // 将读取的字段，写入文件，每个字段一行
//        FileUtils.writeByList(list, FileUtils.createFile(FileUtils.createNewPath(sourcePath)), true);
        //copy(source, createFile(createNewPath(sourcePath)));

        BufferedReader reader = new BufferedReader(new FileReader(source));
        String result = null;
        StringBuffer buffer = new StringBuffer();
        while(true) {
            String tmp = reader.readLine();
            if(tmp == null) {
                break;
            }
            buffer.append("'").append(tmp.trim()).append("',");
        }
        reader.close();
        if (StringUtils.isNotBlank(buffer)) {
            result = buffer.substring(0, buffer.lastIndexOf(","));
        }
        System.out.println(result);
    }
}
