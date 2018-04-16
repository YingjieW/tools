package com.tools.temp;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/4/5 下午5:47
 */
public class FileSourceExample {

    public static void main(String[] args) throws Exception {
        try {
            String filePath = "D:/masterSpring/chapter3/WebRoot/WEB-INF/classes/conf/file1.txt";

            // 使用系统文件路径方式加载文件
            Resource res1 = new FileSystemResource(filePath);

            // 使用类路径方式加载文件
            Resource res2 = new ClassPathResource("conf/file1.txt");

            InputStream ins1 = res1.getInputStream();
            InputStream ins2 = res2.getInputStream();
            System.out.println("res1:"+res1.getFilename());
            System.out.println("res2:"+res2.getFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
