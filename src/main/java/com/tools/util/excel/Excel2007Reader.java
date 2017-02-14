package com.tools.util.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Excel读取工具类(xlsx格式)
 * 
 * @author fang.lu
 * @since 2013年7月15日下午2:47:59
 */
public class Excel2007Reader implements ExcelReader {

	private Integer numberOfSheets;

	private XSSFWorkbook excel;

	public Integer getNumberOfSheets() {
		return numberOfSheets;
	}

	public XSSFSheet getSheet(String sheetname) {
		return excel.getSheet(sheetname);
	}

	public XSSFSheet getSheet(int index) {
		return excel.getSheetAt(index);
	}

	public Excel2007Reader(String path) throws Exception {
		read(path);
	}

	public Excel2007Reader(InputStream stream) throws Exception {
		read(stream);
	}

	/**
     * 初始化类的时候已经调用过此方法,无需单独调用
     */
	public void read(String path) throws Exception {
		if (StringUtils.isBlank(path))
			throw new IllegalArgumentException(
					"File path must not be null or empty !");

		File file = new File(path);
		if (!file.exists() || !file.isFile())
			throw new FileNotFoundException("Can not found file: " + path);

		InputStream stream = new FileInputStream(file);
		read(stream);
	}

	/**
     * 初始化类的时候已经调用过此方法,无需单独调用
     */
	public void read(InputStream stream) throws Exception {
		if (stream == null)
			throw new IllegalArgumentException(
					"Input stream must not be null !");

		excel = new XSSFWorkbook(stream);
		numberOfSheets = excel.getNumberOfSheets();
		stream.close();
	}

}
