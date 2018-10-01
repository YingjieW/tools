//package com.tools.ztest.script;
//
//import com.csvreader.CsvReader;
//import com.tools.util.FileUtils;
//import org.springframework.util.CollectionUtils;
//
//import java.io.File;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * csv 文件脚本
// * @author：yingjie.wang
// * @since：2016年3月29日 下午4:35:34
// * @version:
// */
//public class CsvProcess {
//
//    private static final Charset CHARSET = Charset.forName("GBK");
//    private static int START_LOCATION = (int)'A';
//
//    public static void main(String[] args) throws Exception {
//
//        System.out.println("*******   CsvProcess start!");
//
//        // 目标列，请修改该参数 - targetColumn
//        char targetColumn = 'A';
//
//        int column = (int)targetColumn - START_LOCATION;
//        // 目标文件
//        String path = "/Users/YJ/Downloads/1460434910653-yingjie.wang.csv";
//
//        ArrayList<String[]> csvList = new ArrayList<String[]>();
//        CsvReader reader = new CsvReader(path,',',CHARSET);
//        reader.readHeaders();
//        while(reader.readRecord()){
//            csvList.add(reader.getValues());
//        }
//        reader.close();
//
//        if(CollectionUtils.isEmpty(csvList)) {
//            System.out.println("!!!!!   csvList is empty!");
//            return;
//        }
//
//        List<String> list = new ArrayList<String>();
//        for(int row = 0; row < csvList.size(); row++) {
//            if(column >= csvList.get(row).length) {
//                continue;
//            }
//            StringBuilder sb = new StringBuilder();
//            String target = csvList.get(row)[column];
//            sb.append("'").append(target).append("', ");
//            list.add(sb.toString());
//        }
//
//        String last = list.get(list.size()-1);
//        String sub = last.substring(0, last.lastIndexOf(","));
//        list.remove(list.size()-1);
//        list.add(sub);
//
//        String targetPath = "/Users/YJ/Downloads/csvProcess_" + System.currentTimeMillis() + ".txt";
//        File targetFile = FileUtils.createFile(targetPath);
//        FileUtils.writeByListTo1Line(list, targetFile);
//
//        System.out.println("*******   targetPath: " + targetPath);
//        System.out.println("*******   CsvProcess Successfully!");
//    }
//
//    // 读文件示例
//    public static void readSelectively(String path) throws Exception{
//        ArrayList<String[]> csvList = new ArrayList<String[]>();
//        CsvReader reader = new CsvReader(path,',',CHARSET);
//        // 跳过表头   如果需要表头的话，不要写这句。
//        reader.readHeaders();
//        while(reader.readRecord()){
//            csvList.add(reader.getValues());
//        }
//        reader.close();
//
//        for(int row=0;row<csvList.size();row++){
//            for(int column = 0; column < csvList.get(row).length; column++) {
//                String  cell = csvList.get(row)[column]; //取得第row行第column列的数据
//                System.out.print(cell + "    ");
//            }
//            System.out.println();
//        }
//    }
//}
