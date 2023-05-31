package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelData {

	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static XSSFRow row;
	static XSSFCell cell;

	public static String getCellData(String sheetName, String colName, int rowNo) {
		FileInputStream fis = null;
		String Excelpath = System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\excel1.xlsx";
		try {
			fis = new FileInputStream(Excelpath);
			workbook = new XSSFWorkbook(fis);
			int colNum = 0;
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0); // 1st row
			for (Cell colCell : row) {
				if (colCell.getStringCellValue().trim().equals(colName)) {
					colNum = colCell.getColumnIndex();
					break;
				}
			}
			row = sheet.getRow(rowNo);
			cell = row.getCell(colNum);
			CellType cellType = cell.getCellType();
			switch (cellType) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					DateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
					Date date = cell.getDateCellValue();
					return dt.format(date);
				} else {
					return String.valueOf(cell.getNumericCellValue());
				}
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case BLANK:
				return "";
			default:
				return "Unknown cell type";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "Data not available";
		} catch (IOException e) {
			e.printStackTrace();
			return "IO exception occurred";
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("resource")
	public static void write_CellData(String sheetName, int rNum, int cNum, String Data) {
		String Excelpath = System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\excel1.xlsx";
		try {
			FileInputStream fis = new FileInputStream(Excelpath);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetName); // sheet name in which you want to write
			Row row = sheet.getRow(rNum);// sheet row in which you want to write
			if (row == null) {
				row = sheet.createRow(rNum);
			}
			// cell/column where you want to write
			Cell cell = row.getCell(cNum);
			if (cell == null) {
				cell = row.createCell(cNum);
			}

			// Set the cell or passing the data which you want to write
			cell.setCellValue(Data);

			// Save the changes
			try (FileOutputStream outputStream = new FileOutputStream(Excelpath)) {
				workbook.write(outputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
