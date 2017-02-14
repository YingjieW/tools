package com.tools.util.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * Excel读取器接口
 * 
 * @author fang.lu
 * @since 2013年7月15日下午3:55:59
 */
public interface ExcelReader {

	/**
	 * Get sheet count
	 */
	Integer getNumberOfSheets();

	/**
	 * Get sheet by name
	 * 
	 * @param sheetname
	 * @return
	 */
	Sheet getSheet(String sheetname);

	/**
	 * Get sheet by index
	 * 
	 * @param index
	 * @return
	 */
	Sheet getSheet(int index);

	/**
	 * Read excel from file path
	 * 
	 * @param path
	 * @throws IOException
	 */
	void read(String path) throws Exception;

	/**
	 * Read excel from file stream
	 * 
	 * @param stream
	 * @throws IOException
	 */
	void read(InputStream stream) throws Exception;

}
