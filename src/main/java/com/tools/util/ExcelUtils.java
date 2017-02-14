package com.tools.util;

import com.tools.util.excel.Excel2007Reader;
import com.tools.util.excel.Excel97Reader;
import com.tools.util.excel.ExcelReader;
import com.tools.util.excel.ExportParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Excel 操作类
 * @author：yingjie.wang
 * @since：2016年3月29日 下午2:55:25 
 * @version:
 */
@SuppressWarnings("deprecation")
public class ExcelUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);
    
    private static HSSFCellStyle bodyStyle;
    
    private static HSSFCellStyle headStyle;
    
    private static HSSFFont bodyFont;
    
    private static HSSFFont headFont;
    
    /**
     * 生成ExcelReader
     * @param path
     * @return
     * @throws Exception
     */
    public static ExcelReader parse(String path) throws Exception{
        ExcelReader reader = null;
        if (path.endsWith(".xls")) {
            reader = new Excel97Reader(path);
        } else if (path.endsWith(".xlsx")) {
            reader = new Excel2007Reader(path);
        } else {
            throw new RuntimeException("Unsupport file type.");
        }
        return reader;
    }
    
    
    /**
     * 一般下载用这个
     * 1.保存临时文件到服务器
     * 2.下载到客户端
     * 
     * @param list      : Excel数据源 每一个ExportParams对象代表excel中一个sheet
     * @param objClass  : Excel数据源中的数据类型
     * @param title     : 新建Sheet的名称
     * @param strTitle  : Sheet各列的标题（第一行各列的名称）
     * @param strBody   : Sheet各列的取值方法名（各列的值在objClass中get方法名称）
     * @param response
     */
    @SuppressWarnings("rawtypes")
    public static void exportAndDownloadExcel(List list, Class objClass, String title, String strTitle, String strBody, HttpServletResponse response) {
        exportAndDownloadExcel(list, objClass, title, strTitle, strBody, null, response);
    }
    
    /**
     * 商户分账分润方失败记录页面下载用
     * 1.保存临时文件到服务器
     * 2.下载到客户端
     * 
     * @param list      : Excel数据源 每一个ExportParams对象代表excel中一个sheet
     * @param objClass  : Excel数据源中的数据类型
     * @param title     : 新建Sheet的名称
     * @param strTitle  : Sheet各列的标题（第一行各列的名称）
     * @param strBody   : Sheet各列的取值方法名（各列的值在objClass中get方法名称）
     * @param headRow   : Excel 表头信息，有值代表导出excel表头需要增加一行，一般下载传空就行。
     *                    增加此参数原因是上传时是从第三行开始解析数据。
     *                    商户分账分润方失败记录页面下载excel编辑后直接上传会丢失第二行数据。只有这3个页面用到
     * @param response
     */
    @SuppressWarnings("rawtypes")
    public static void exportAndDownloadExcel(List list, Class objClass, String title, String strTitle, String strBody, String headRow,
            HttpServletResponse response) {
        List<ExportParams> exportParamLists = new ArrayList<ExportParams>();
        ExportParams exportParam = new ExportParams(list, objClass, title, strTitle, strBody, headRow);
        exportParamLists.add(exportParam);
        exportAndDownloadExcel(exportParamLists, title, response);
    }
    
    /**
     * 导出多个sheet用
     * 1.保存临时文件到服务器
     * 2.下载到客户端
     * 
     * @param objList   :Excel数据源 每一个ExportParams对象代表excel中一个sheet
     * @param title     :excel文件名称
     * ExportParams中的
     *            headRow   : Excel 表头信息，有值代表导出excel表头需要增加一行，一般下载传空就行。
     *            增加此参数原因是上传时是从第三行开始解析数据。商户分账分润方失败记录页面下载excel编辑后直接上传会丢失第二行数据。只有这3个页面用到
     * @param response
     */
    public static void exportAndDownloadExcel(List<ExportParams> objList, String title, HttpServletResponse response) {
        LOGGER.info("begin create downloadExcel filePath");
        String upload_file_directory = "excelUtil_exportAndDownloadExcel";
        String rootPath = upload_file_directory + "tmp/";
        String filePath = rootPath + UUID.randomUUID().toString() + ".xls";
        checkFolder(rootPath);

        // 初始化工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        setbodyStyle(workbook);
        setHeadStyle(workbook);
        // 写入多个sheet
        for (int i = 0; i < objList.size(); i++) {
            ExportParams params = (ExportParams) objList.get(i);
            workbook = createWorkbook(workbook, params.getList(), params.getObjClass(), params.getTitle(), params.getStrTitle(), params.getStrBody(),
                    params.getHeadRow());
        }
        // 保存Excel文件
        LOGGER.info("downloadExcel filePath：" + filePath);
        saveFile(workbook, filePath);
        // 下载文件
        downLoadData(response, filePath, title);
    }
    
    /**
     * 创建工作簿
     * 
     * @param workbook
     * @param objList   : Excel数据源
     * @param objClass  : Excel数据源中的数据类型
     * @param title     : 新建Sheet的名称
     * @param strTitle  : Sheet各列的标题（第一行各列的名称）
     * @param strBody   : Sheet各列的取值方法名（各列的值在objClass中get方法名称）
     */
    @SuppressWarnings("rawtypes")
    public static HSSFWorkbook createWorkbook(HSSFWorkbook workbook, List objList, Class objClass,
            String title, String strTitle, String strBody, String headString) {
        // 创建Excel工作簿的第一个Sheet页
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认的列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 设置默认的行高像素点
        sheet.setDefaultRowHeightInPoints(16);
        if (StringUtils.isBlank(headString)) {
            // 创建Sheet页的文件头（第一行）
            createTitle(sheet, strTitle, workbook);
            // 创建Sheet页的文件体（后续行）
            if (objClass != Map.class) {
                createBody(objList, objClass, sheet, strBody, workbook);
            } else {
                createBody4Map(objList, sheet, strBody, workbook);
            }
        } else {
            // 创建Sheet页的文件头（第一二行）
            createTitle(sheet, strTitle, workbook, headString);
            // 创建Sheet页的文件体（后续行） ---从第三行开始
            createBody(objList, objClass, sheet, strBody, workbook, 2);
        }
        return workbook;
    }
    
    /**
     * 创建Excel当前sheet页的头信息
     *
     * @param sheet     :   Excel工作簿的一个sheet
     * @param strTitle  :   sheet头信息列表(sheet第一行各列值)
     */
    private static void createTitle(HSSFSheet sheet, String strTitle ,HSSFWorkbook workBook){
        HSSFRow row = sheet.createRow(  0); // 创建该页的一行
        HSSFCell cell = null;

        String[] strArray = strTitle.split(",");

        for(int i =   0; i < strArray.length; i++) {
            cell = row.createCell(i); // 创建该行的一列
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(headStyle);

            //自适应宽度
            if((sheet.getColumnWidth(i)) < cell.getStringCellValue().getBytes().length * 400)
            {
                sheet.setColumnWidth(i, cell.getStringCellValue().getBytes().length * 400);
            }
        }
    }
    
    /**
     * 创建Excel当前sheet页的体信息
     *
     * @param objList   :   Excel数据源
     * @param objClass  :   Excel数据源中的数据类型
     * @param sheet     :   Excel工作簿的sheet页
     * @param strBody   :   Sheet各列的取值方法名（各列的值在objClass中get方法名称）
     */
    @SuppressWarnings("rawtypes")
    private static void createBody(List objList, Class objClass, HSSFSheet sheet, String strBody, HSSFWorkbook workBook){
        
        String[] targetMethod = strBody.split(",");
        Method[] ms = objClass.getMethods();

        // 循环objList对象列表（生成sheet的行）
        for(int objIndex =   0; objIndex < objList.size(); objIndex++){
            Object obj = objList.get(objIndex);
            HSSFRow row = sheet.createRow(objIndex +   1);
            // 循环strBody目标方法数组（生成sheet的列）
            for(int strIndex =   0; strIndex < targetMethod.length; strIndex++) {
                String targetMethodName = targetMethod[strIndex];
                // 循环ms方法数组，找到目标方法（strBody中指定的方法）并调用
                for(int i =   0; i < ms.length; i++) {
                    Method srcMethod = ms[i];
                    int len = targetMethodName.indexOf(".") <   0 ? targetMethodName.length() : targetMethodName.indexOf(".");
                    if (srcMethod.getName().equals(targetMethodName.substring(  0, len))) {
                        HSSFCell cell = row.createCell(strIndex);
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellStyle(bodyStyle);
                        try {
                            // 如果方法返回一个引用类型的值
                            if (targetMethodName.contains(".")) {
                                cell.setCellValue(referenceInvoke(targetMethodName, obj));
                                // 如果方法返回一个普通属性
                            } else {
                                Object object = srcMethod.invoke(obj);

                                if(object == null)
                                    cell.setCellValue("");
                                else
                                    cell.setCellValue((object).toString());
                                //自适应宽度
                                if((sheet.getColumnWidth(strIndex)) < cell.getStringCellValue().getBytes().length * 400)
                                {
                                    sheet.setColumnWidth(strIndex, cell.getStringCellValue().getBytes().length * 400);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    /**
     * 创建Excel当前sheet页的体信息，Excel数据源中的数据类型为Map
     * 暂不支持多级引用
     * @param objList   :   Excel数据源
     * @param sheet     :   Excel工作簿的sheet页
     * @param strBody   :   Sheet各列对应至数据源Map中的键
     */
    @SuppressWarnings("rawtypes")
    private static void createBody4Map(List objList, HSSFSheet sheet, String strBody, HSSFWorkbook workBook){
        String[] targetKeys = strBody.split(",");

        // 循环objList对象列表（生成sheet的行）
        for(int objIndex = 0; objIndex < objList.size(); objIndex++){
            Map obj = (Map)objList.get(objIndex);
            HSSFRow row = sheet.createRow(objIndex +   1);
            // 循环strBody属性数组（生成sheet的列）
            for(int strIndex = 0; strIndex < targetKeys.length; strIndex++) {
                String targetKey = targetKeys[strIndex];
                HSSFCell cell = row.createCell(strIndex);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(bodyStyle);
                Object value = obj.get(targetKey);
                if(value == null)
                    cell.setCellValue("");
                else
                    cell.setCellValue((value).toString());
                //自适应宽度
                if((sheet.getColumnWidth(strIndex)) < cell.getStringCellValue().getBytes().length * 400)
                {
                    sheet.setColumnWidth(strIndex, cell.getStringCellValue().getBytes().length * 400);
                }
            }
        }
    }
    
    /**
     * 创建Excel当前sheet页的头信息
     * 
     * @param sheet     : Excel工作簿的一个sheet
     * @param strTitle  : sheet头信息列表(sheet第一行各列值)
     */
    private static void createTitle(HSSFSheet sheet, String strTitle,
            HSSFWorkbook workBook, String headString) {
        HSSFRow row1 = sheet.createRow(0);// 创建该页的一行
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell1.setCellValue(headString);
        cell1.setCellStyle(headStyle);
        HSSFRow row = sheet.createRow(1); // 创建该页的二行
        HSSFCell cell = null;

        String[] strArray = strTitle.split(",");

        for (int i = 0; i < strArray.length; i++) {
            cell = row.createCell(i); // 创建该行的一列
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(headStyle);

            // 自适应宽度
            if ((sheet.getColumnWidth(i)) < cell.getStringCellValue()
                    .getBytes().length * 400) {
                sheet.setColumnWidth(i,
                        cell.getStringCellValue().getBytes().length * 400);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, strArray.length - 1));
    }
    
    /**
     * 创建Excel当前sheet页的体信息，Excel数据源中的数据类型为Map
     * @param objList   :   Excel数据源
     * @param sheet     :   Excel工作簿的sheet页
     * @param strBody   :   Sheet各列对应至数据源Map中的键
     */
    @SuppressWarnings("rawtypes")
    private static void createBody(List objList, Class objClass, HSSFSheet sheet, String strBody, HSSFWorkbook workBook,int index){
        String[] targetMethod = strBody.split(",");
        Method[] ms = objClass.getMethods();

        // 循环objList对象列表（生成sheet的行）
        for(int objIndex =   0; objIndex < objList.size(); objIndex++){
            Object obj = objList.get(objIndex);
            HSSFRow row = sheet.createRow(objIndex +   index);
            // 循环strBody目标方法数组（生成sheet的列）
            for(int strIndex =   0; strIndex < targetMethod.length; strIndex++) {
                String targetMethodName = targetMethod[strIndex];
                // 循环ms方法数组，找到目标方法（strBody中指定的方法）并调用
                for(int i =   0; i < ms.length; i++) {
                    Method srcMethod = ms[i];
                    int len = targetMethodName.indexOf(".") <   0 ? targetMethodName.length() : targetMethodName.indexOf(".");
                    if (srcMethod.getName().equals(targetMethodName.substring(  0, len))) {
                        HSSFCell cell = row.createCell(strIndex);
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellStyle(bodyStyle);
                        try {
                            // 如果方法返回一个引用类型的值
                            if (targetMethodName.contains(".")) {
                                cell.setCellValue(referenceInvoke(targetMethodName, obj));
                                // 如果方法返回一个普通属性
                            } else {
                                Object object = srcMethod.invoke(obj);

                                if(object == null)
                                    cell.setCellValue("");
                                else
                                    cell.setCellValue((object).toString());
                                //自适应宽度
                                if((sheet.getColumnWidth(strIndex)) < cell.getStringCellValue().getBytes().length * 400)
                                {
                                    sheet.setColumnWidth(strIndex, cell.getStringCellValue().getBytes().length * 400);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 方法返回的是一个对象的引用（如：getHomeplace.getName类型的方法序列）
     *      按方法序列逐层调用直到最后放回基本类型的值
     *
     * @param targetMethod  :   obj对象所包含的方法列
     * @param obj           :   待处理的对象
     * @return
     */
    private static String referenceInvoke(String targetMethod, Object obj) {
        // 截取方法序列的第一个方法(即截取属于obj对象的方法：getHomeplace())
        String refMethod = targetMethod.substring(0, targetMethod.indexOf("."));
        // 获得后续方法序列(getName())
        targetMethod = targetMethod.substring(targetMethod.indexOf(".") + 1);
        try {
            // 获得第一个方法的执行结果(即obj方法执行的结果：obj.getHomeplace())
            obj = obj.getClass().getMethod(refMethod).invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果方法序列没到最后一节
        if (targetMethod.contains(".")) {
            return referenceInvoke(targetMethod, obj);
            // 如果方法序列到达最后一节
        } else {
            try {
                if(obj == null)
                    return "";
                // 通过obj对象获得该方法链的最后一个方法并调用
                Method tarMethod = obj.getClass().getMethod(targetMethod);
                return tarMethod.invoke(obj).toString();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * 新建目录
     * 
     * @param newfolder
     */
    private static void checkFolder(String newfolder) {
        try {
            File file = new File(newfolder);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 设置表格样式
     * @param workBook
     * @return
     */
    public static HSSFCellStyle setbodyStyle(HSSFWorkbook workBook) {
        bodyStyle = workBook.createCellStyle();
        bodyStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        bodyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成字体
        if(bodyFont == null) {
            bodyFont = workBook.createFont();
        }
        bodyFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样样式
        bodyStyle.setFont(bodyFont);
        return bodyStyle;
    }
    
    /**
     * 设置标题样式
     * @param workBook
     * @return
     */
    public static HSSFCellStyle setHeadStyle(HSSFWorkbook workBook) {
        headStyle = workBook.createCellStyle();

        headStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成字体
        if(headFont == null) {
            headFont = workBook.createFont();
        }
        headFont.setColor(HSSFColor.VIOLET.index);
        headFont.setFontHeightInPoints((short) 12);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样样式
        headStyle.setFont(headFont);
        return headStyle;
    }
    
    /**
     * 保存Excel文件
     * 
     * @param workbook:     Excel工作簿
     * @param outputPath:   Excel文件保存路径
     */
    private static void saveFile(HSSFWorkbook workbook, String outputPath) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(outputPath));
            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 下载设置
     * @param response
     * @param path
     * @param uploadedFileName
     */
    public static void downLoadData(HttpServletResponse response,
                                    String path, String uploadedFileName) {
        try
        {
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(URLEncoder
                    .encode(uploadedFileName, "utf-8")));
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        response.setHeader("Content-Type", "application/octet-stream");

        File file = new File(path);
        BufferedInputStream bis = null;// 读excel
        BufferedOutputStream bos = null;// 输出

        try {
            // 读取excel文件
            bis = new BufferedInputStream(new FileInputStream(file));
            // 写入response的输出流中
            bos = new java.io.BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];/* 设置缓存 */
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
