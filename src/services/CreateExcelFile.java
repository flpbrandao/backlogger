package services;

import java.io.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CreateExcelFile {
	public static void main(String[] args) {
	
		
	
	}
	public void createExcelFile(String path) {
		try {
			//declare file name to be create   
						String filename = path + "aa.xlsx";
			//creating an instance of XSSFWorkbook class  
						XSSFWorkbook workbook = new XSSFWorkbook();
			//invoking creatSheet() method and passing the name of the sheet to be created   
						XSSFSheet sheet = workbook.createSheet("January");
			//creating the 0th row using the createRow() method  
						XSSFRow rowhead = sheet.createRow((short) 0);
			//creating cell by using the createCell() method and setting the values to the cell by using the setCellValue() method  
						rowhead.createCell(0).setCellValue("S.No.");
						rowhead.createCell(1).setCellValue("Customer Name");
						rowhead.createCell(2).setCellValue("Account Number");
						rowhead.createCell(3).setCellValue("e-mail");
						rowhead.createCell(4).setCellValue("Balance");
			//creating the 1st row  
						XSSFRow row = sheet.createRow((short) 1);
			//inserting data in the first row  
						row.createCell(0).setCellValue("1");
						row.createCell(1).setCellValue("John William");
						row.createCell(2).setCellValue("9999999");
						row.createCell(3).setCellValue("william.john@gmail.com");
						row.createCell(4).setCellValue("700000.00");
			//creating the 2nd row  
						XSSFRow row1 = sheet.createRow((short) 2);
			//inserting data in the second row  
						row1.createCell(0).setCellValue("2");
						row1.createCell(1).setCellValue("Mathew Parker");
						row1.createCell(2).setCellValue("22222222");
						row1.createCell(3).setCellValue("parker.mathew@gmail.com");
						row1.createCell(4).setCellValue("200000.00");
						FileOutputStream fileOut = new FileOutputStream(filename);
						workbook.write(fileOut);
			//closing the Stream  
						fileOut.close();
			//closing the workbook  
						workbook.close();
			//prints the message on the console  
						System.out.println("Excel file has been generated successfully.");
					} catch (Exception e) {
						e.printStackTrace();
					}
	}
}