package com.tools.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作类型
 * @author：YJ    
 * @since：2016年2月24日 下午3:49:20 
 * @version:
 */
public class FileUtils {
   
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    
    private static final String LINE_SEPARATOR_UNIX = "\n";
    private static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    
    /**
     * write bytes to file.
     * @param target
     * @param bytes
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeByBytes(File target, byte[] bytes) throws FileNotFoundException, IOException{
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(target));
        stream.write(bytes);
        stream.close();
    }
    
    /**
     * Base64 encoder.
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String writeToBase64(InputStream inputStream) throws IOException {
        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);
        inputStream.close();
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String result = encoder.encode(data);
        return result;
    }
    
    
    /**
     * txt文件：读取每行数据，并截取[beginIndex,endIndex)，将结果以链表返回；
     * 
     * @param beginIndex
     * @param endIndex
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static List<String> readSelectively(File source, int beginIndex, int endIndex) throws IOException,IllegalArgumentException{
        if(source == null || source.length() == 0 || beginIndex < 0 || endIndex <= beginIndex) {
            throw new IllegalArgumentException("Param Error Exception.");
        }

        logger.info("#####   source : " + source.getAbsolutePath());
        logger.info("#####   beginIndex : " + beginIndex);
        logger.info("#####   endIndex : " + endIndex);
        
        List<String> result = new ArrayList<String>(0);
        BufferedReader reader = new BufferedReader(new FileReader(source));
        while(true) {
            String tmp = reader.readLine();
            // 读完则退出循环；如果endIndex大于该行长度，则跳过该行，继续向下执行。
            if(tmp == null) {
                break;
            } else if(tmp.length() < endIndex) {
                continue;
            }
            result.add(tmp.substring(beginIndex, endIndex));
        }
        reader.close();
        return result;
    }
    
    /**
     * txt文件：写入文件，链表的每个节点都作为一行写入
     * 
     * @param list
     * @param target
     * @throws IOException
     */
    public static void writeByList(List<String> list, File target, boolean isWindows) throws IOException{
        if(list == null || list.size() == 0) {
            return;
        }
        String lineSeparator = "";
        if(isWindows) {
            lineSeparator = LINE_SEPARATOR_WINDOWS;
        } else {
            lineSeparator = LINE_SEPARATOR_UNIX;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(target));
        for(String str : list) {
            writer.write(str);
            writer.write(lineSeparator);
        }
        writer.close();
    }
    
    /**
     * 写入文件：链表中的左右数据以一行数据写入文件。
     * @param list
     * @param target
     * @throws IOException
     */
    public static void writeByListTo1Line(List<String> list, File target) throws IOException{
        if(list == null || list.size() == 0) {
            return;
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(target));
        for(String str : list) {
            writer.write(str);
        }
        writer.close();
    }
    
    
    /**
     * 检查path是否存在
     * @param path
     * @return
     */
    public static boolean checkPath(String path) {
        File file = new File(path);
        return file.exists();
    }
    
    /**
     * 复制文件
     * @param source
     * @param target
     * @throws IOException
     */
    public static void copy(File source, File target) throws IOException{
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream  os = new BufferedOutputStream(new FileOutputStream(target));
        byte[] tmp = new byte[1024];
        while (true) {
            int i = is.read(tmp);
            if (i == -1) {
                break;
            }
            os.write(tmp, 0, i);
        }
        is.close();
        os.close();
    }
    
    /**
     * 新建文件路径
     * @param path
     * @return
     */
    public static String createNewPath(String path) {
        if(path == null || !path.contains(".")) {
            return null;
        }
        String result = path.substring(0, path.lastIndexOf(".")) + "_" 
                        + String.valueOf(System.currentTimeMillis()) 
                        + path.substring(path.lastIndexOf("."));
        return result;
    }
    
    /**
     * 新建file
     * @param path
     * @return
     * @throws IOException
     */
    public static File createFile(String path) throws IOException{
        return createFile(path, false);
    }

    /**
     * 新建file
     * @param path
     * @param deleteOldFile
     * @return
     * @throws IOException
     */
    public static File createFile(String path, boolean deleteOldFile) throws IOException {
        File file   = new File(path);
        file.getParentFile().mkdirs();
        if(deleteOldFile) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

}
