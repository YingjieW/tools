package com.tools.util.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Excel97Reader implements ExcelReader {

	private Integer numberOfSheets;

	private HSSFWorkbook excel;

	public Integer getNumberOfSheets() {
		return numberOfSheets;
	}

	public HSSFSheet getSheet(String sheetname) {
		return excel.getSheet(sheetname);
	}

	public HSSFSheet getSheet(int index) {
		return excel.getSheetAt(index);
	}

	public Excel97Reader(String path) throws Exception {
		read(path);
	}

	public Excel97Reader(InputStream stream) throws Exception {
		read(stream);
	}

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

	public void read(InputStream stream) throws Exception {
		if (stream == null)
			throw new IllegalArgumentException(
					"Input stream must not be null !");

		excel = new HSSFWorkbook(stream);
		numberOfSheets = excel.getNumberOfSheets();
		stream.close();
	}

}
