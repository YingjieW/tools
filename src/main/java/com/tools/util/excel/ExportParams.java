package com.tools.util.excel;

import java.util.List;

/**
 * 导出参数类
 * @author：yingjie.wang
 * @since：2016年3月29日 下午3:38:40 
 * @version:
 */
public class ExportParams {

    /**
     * 要导出的数据--
     */
    private List list;

    /**
     * sheet 名称
     */
    private String title;

    /**
     * 列名
     */
    private String strTitle;

    /**
     * 列填充的值
     */
    private String strBody;

    /**
     * list中的对象
     */
    private Class objClass;

    /**
     * Excel 表头信息，有值代表导出excel表头需要增加一行，一般下载传空就行。
     * 增加此参数原因是上传时是从第三行开始解析数据。商户分账分润方失败记录页面下会丢失第二行数据。
     * 所以导出时需要增加一行。目前只有这3个页面用到
     */
    private String headRow;

    /**
     * 创建一个新的实例 ExportParams.
     * 
     */
    public ExportParams() {
        super();
    }

    /**
     * 创建一个新的实例 ExportParams.
     * 
     * @param list
     *            要导出的数据
     * @param objClass
     *            list中的对象
     * @param title
     *            sheet 名称
     * @param strTitle
     *            列名
     * @param strBody
     *            列填充的值
     */
    public ExportParams(List list, Class objClass, String title, String strTitle, String strBody, String headRow) {
        super();
        this.list = list;
        this.title = title;
        this.strTitle = strTitle;
        this.strBody = strBody;
        this.objClass = objClass;
        this.headRow = headRow;
    }
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrBody() {
        return strBody;
    }

    public void setStrBody(String strBody) {
        this.strBody = strBody;
    }

    public Class getObjClass() {
        return objClass;
    }

    public void setObjClass(Class objClass) {
        this.objClass = objClass;
    }

    public String getHeadRow() {
        return headRow;
    }

    public void setHeadRow(String headRow) {
        this.headRow = headRow;
    }

}
