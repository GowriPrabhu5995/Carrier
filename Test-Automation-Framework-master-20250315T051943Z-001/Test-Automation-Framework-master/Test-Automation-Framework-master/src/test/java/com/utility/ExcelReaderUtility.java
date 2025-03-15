package com.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ui.pojo.User;

public class ExcelReaderUtility {

	public static Iterator<User> readExcelFile(String fileName){

		File xlsxFile = new File(System.getProperty("user.dir") + "//TestData//"+ fileName);

		XSSFWorkbook xssfWorkbook = null;
		Row row;
		Cell emailAddress;
		Cell password;
		User user;
		List<User> userList = null;
		Iterator<Row> rowIterator;
		XSSFSheet xssfSheet;
		try {
			xssfWorkbook = new XSSFWorkbook(xlsxFile);
			userList= new ArrayList<User>();
			xssfSheet= xssfWorkbook.getSheet("LoginTestData");
			rowIterator  = xssfSheet.iterator();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				emailAddress = row.getCell(0);
				password = row.getCell(1);
				user = new User(emailAddress.toString(), password.toString());
				userList.add(user);
				xssfWorkbook.close();
			}
		
		} catch (InvalidFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return userList.iterator();
	}

}
