package com.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Table.Cell;

public class ExcelIO {

	 public static List<String> readExcelEliminate(String filePath,int cellno) throws IOException {
		    List<String> data = new ArrayList<>();

		    try (FileInputStream fis = new FileInputStream(filePath);
		         Workbook workbook = new XSSFWorkbook(fis)) {
		        	org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0); // Assuming you are working with the first sheet
		        	for (Row row : sheet) {
		        		data.add(row.getCell(cellno).toString());
		            }	          	   
		    }
		    return data;
		}
		
	
	
	
	   private static void writeExcel(String filePath, List<List<String>> data) throws IOException {
	        try (Workbook workbook = new XSSFWorkbook();
	             FileOutputStream fos = new FileOutputStream(filePath)) {

	            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet();

	            int rowNum = 0;
	            for (List<String> rowData : data) {
	                Row row = sheet.createRow(rowNum++);

	                int cellNum = 0;
	                for (String cellData : rowData) {
	                    Cell cell = (Cell) row.createCell(cellNum++);
	                    ((org.apache.poi.ss.usermodel.Cell) cell).setCellValue(cellData);
	                }
	            }

	            workbook.write(fos);
	        }
	    }
}
