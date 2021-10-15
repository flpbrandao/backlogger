package services;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entities.Ticket;

public class CreateExcelFile {

	SimpleDateFormat todayDate = new SimpleDateFormat("dd-MM-yyyy");

	public static void main(String[] args) {

	}

	public void createExcelFile(String path, Date currentDate, List<Ticket> finalList) {
		
		try {
			// declare file name to be create
			String filename = path + "-FINAL REPORT.xlsx";
			// creating an instance of XSSFWorkbook class
			XSSFWorkbook workbook = new XSSFWorkbook();
			// invoking creatSheet() method and passing the name of the sheet to be created
			XSSFSheet sheet = workbook.createSheet("Tickets");
			// creating the 0th row using the createRow() method
			XSSFRow rowhead = sheet.createRow((short) 0);
			// creating cell by using the createCell() method and setting the values to the
			// cell by using the setCellValue() method
			rowhead.createCell(0).setCellValue("Number");
			rowhead.createCell(1).setCellValue("Assigned To");
			rowhead.createCell(2).setCellValue("Assignment Group");
			rowhead.createCell(3).setCellValue("Status");
			rowhead.createCell(4).setCellValue("Created on");
			rowhead.createCell(5).setCellValue("Updated on");
			int i = 1;
			for (Ticket t : finalList) {

				// creating the 1st row
				XSSFRow row = sheet.createRow((short) i);
				// inserting data in the first row

				row.createCell(0).setCellValue(t.getNumber());
				row.createCell(1).setCellValue(t.getAssigned_to());
				row.createCell(2).setCellValue(t.getAssignment_group());
				row.createCell(3).setCellValue(t.getStatus());
				row.createCell(4).setCellValue(todayDate.format(t.getCreatedOn()));
				row.createCell(5).setCellValue(todayDate.format(t.getUpdatedOn()));
				i++;
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			// closing the Stream
			fileOut.close();
			// closing the workbook
			workbook.close();
			// prints the message on the console
			System.out.println("Excel file has been generated successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}