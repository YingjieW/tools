package com.tools.ztest.test;

import org.fit.cssbox.demo.ImageRenderer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/5 上午11:10
 */
public class TestCssBox {

    public static void main(String[] args) throws Exception {
        String url = "http://kuka.im/2016/06/25/%E7%94%A8java%E5%B0%86html%E8%BD%AC%E5%8C%96%E4%B8%BA%E5%9B%BE%E7%89%87/";
        File file = new File("/Users/YJ/Downloads/testCssBox01.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ImageRenderer imageRenderer = new ImageRenderer();
        boolean result = imageRenderer.renderURL(url, fileOutputStream, ImageRenderer.Type.PNG);
        System.out.println("=====> result: " + result);
    }
}
