package com.tools.util;

import open.udm.client.exception.FileEmptyException;
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


    /**
     * 获取文件总行数
     * @param file
     * @return
     * @throws IOException
     */
    public static int getTotalLines(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        lineNumberReader.skip(Long.MAX_VALUE);
        int lines = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        // 有些文件最后一行没有'\r'or'\n',故需要额外再处理一下
        try {
            char lastChar = readLastChar(file);
            if (lastChar != '\r' && lastChar != '\n') {
                lines++;
            }
        } catch (FileEmptyException e) {
            // 文件内容为空时会抛出该异常
            return 0;
        } catch (IOException e) {
            throw e;
        }
        return lines;
    }

    /**
     * 按批次分割
     * @param totalLineNum
     * @param batchSize
     * @return
     */
    public static int[][] splitByLine(int totalLineNum, int batchSize) {
        int[][] arr = null;
        if (totalLineNum <= 0 || batchSize <= 0) {
            return arr;
        }
        int batchNum = totalLineNum / batchSize;
        int left = 0;
        int right = 0;
        if (batchNum == 0) {
            arr = new int[1][2];
            right = totalLineNum - 1;
            arr[0][0] = left;
            arr[0][1] = right;
        } else {
            arr = new int[batchNum+1][2];
            for (int i = 0; i < batchNum; i++) {
                left = i * batchSize;
                right = (i + 1) * batchSize - 1;
                arr[i][0] = left;
                arr[i][1] = right;
            }
            if (right + 1 < totalLineNum) {
                left = right + 1;
                right = totalLineNum - 1;
                arr[batchNum][0] = left;
                arr[batchNum][1] = right;
            } else {
                arr[batchNum][0] = -1;
                arr[batchNum][1] = -1;
            }
        }
        return arr;
    }

    public static char readLastChar(File file) throws FileEmptyException, IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        long length = randomAccessFile.length();
        if (length == 0) {
            throw new FileEmptyException("file empty exception");
        }
        long start = randomAccessFile.getFilePointer();
        long nextEnd = start + length - 1;
        randomAccessFile.seek(nextEnd);
        return (char)randomAccessFile.read();
    }


    public static void main(String[] args) throws Exception {
//        String filePath = "/Users/YJ/Documents/generator/1051100010014250_0828.csv";
//        String filePath = "/Users/YJ/Documents/generator/1111111010000208_trade_20170706_20170706-2.csv";
//        String filePath = "/Users/YJ/Documents/generator/20170907.txt";
//        String filePath = "/Users/YJ/Documents/generator/test.txt";
        String filePath = "/Users/YJ/Documents/generator/test01.txt";
        File file = new File(filePath);
        int totalLineNum = getTotalLines(file);
        System.out.println("totalLineNum: " + totalLineNum);

        BufferedReader bufferedReader= new BufferedReader(new FileReader(file));
        LineNumberReader numberReader = new LineNumberReader(bufferedReader);
        String tempStr = null;
        int line = 0;
        while ((tempStr = numberReader.readLine()) != null) {
            line++;
//            System.out.println(tempStr);
        }
        System.out.println("line : " + line);

        Reader reader = new FileReader(filePath);
        int tempChar;
        int charCount = 0;
        while ((tempChar = reader.read()) != -1) {
            charCount++;
//            System.out.print("_" + (char) tempChar);
            if ((char) tempChar == '\r') {
                System.out.println("\\r");
            } else if ((char) tempChar == '\n') {
                System.out.println("\\n");
            } else {
                System.out.print((char) tempChar);
            }
        }
        System.out.println("\ncharCount : " + charCount);
        System.out.println();

        char lastChar = readLastChar(file);
        if (lastChar == '\r') {
            System.out.println("'\\r'");
        } else if (lastChar == '\n') {
            System.out.println("'\\n'");
        } else {
            System.out.print("'" + lastChar + "'");
        }
        System.out.println();

        CloseUtils.close(bufferedReader, numberReader, reader);

//        System.out.println("1051100110018352".getBytes().length);
    }
}
