package com.tools.util;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 
 * @author：yingjie.wang
 * @since：2016年2月21日 下午11:38:10 
 * @version:
 */
public class ImageUtils {
   
    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
    
    // 计算图片压缩次数
    private static int countCompress;
    
    /**
     * 图片压缩方法，将图片大小压缩至targetSize以下；
     * 使用了第三方开源工具：Thumbnailator；
     * Thumbnailator比较吃内存，请酌情调整服务器的堆内存大小，建议：-Xms512M -Xmx1024M
     * @param source
     * @param targetSize
     * @return
     * @throws IOException
     */
    public static void compress(File source, File target, int targetSize) throws IOException{
        float scale = 0.5f;
        compress(source, target, targetSize, scale);
    }
    
    /**
     * 图片压缩方法，将图片大小压缩至targetSize以下；
     * 使用了第三方开源工具：Thumbnailator；
     * Thumbnailator比较吃内存，请酌情调整服务器的堆内存大小，建议：-Xms512M -Xmx1024M
     * @param source
     * @param targetSize
     * @param scale
     * @return
     * @throws IOException
     */
    public static void compress(File source, File target, int targetSize, float scale) throws IOException{
        if(source == null || !source.exists() || target == null || !target.exists() || targetSize <= 0 || scale >= 1) {
            throw new RuntimeException("Param Error Exception.");
        }
        
        // 如果图片size小于targetSize则不进行压缩
        long sourceSize = source.length()/1024;
        if(sourceSize < targetSize) {
            target = source;
            return;
        }
        
        // 获取原图片的width,height;
        BufferedImage bufferedImage =ImageIO.read(new FileInputStream(source));
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        logger.info("##### - 压缩开始");
        countCompress = 0;
        doCompress(source, target, width, height, targetSize, scale);
        logger.info("##### - 压缩结束");
        logger.info("##### - 压缩次数：" + countCompress);
        logger.info("#####   scale = " + scale);
        logger.info("#####   source_size = " + source.length()/1024 + " KB");
        logger.info("#####   compressed  = " + target.length()/1024 + " KB");
    }
    
    /**
     * 压缩图片，直至小于targetSize；
     * 使用了第三方开源工具：Thumbnailator；
     * Thumbnailator比较吃内存，请酌情调整服务器的堆内存大小，建议：-Xms512M -Xmx1024M
     * @param source
     * @param target
     * @param width
     * @param height
     * @param targetSize
     * @param scale
     * @throws IOException
     */
    private static void doCompress(File source, File target, int width, int height, int targetSize, float scale) throws IOException{
        if(source == null || !source.exists() || target == null || 
                !target.exists() || width <= 0 || height <= 0 || scale >= 1) {
            throw new RuntimeException("Param Error Exception");
        }
        if(source.length()/1024 < targetSize) {
            target = source;
            return;
        }
        Thumbnails.of(source).size(width, height).toFile(target);
        countCompress++;
        logger.info("##### * doCompress - size: " + target.length()/1024 + " KB");
        if(target.length()/1024 < targetSize) {
            return;
        }
        doCompress(source, target, (int)(width*scale), height, targetSize, scale);
    }
    
}
