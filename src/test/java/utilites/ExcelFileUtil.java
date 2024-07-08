package utilites;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {

	XSSFWorkbook wb;
	
	// write constructor for reading excel path
	public ExcelFileUtil(String ExcelPath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(ExcelPath);
		wb = new XSSFWorkbook(fi);
     }
     // method for number of rows in a sheet
	
	public int rowCount(String Sheet)
	{
		return wb.getSheet(Sheet).getLastRowNum();
			
	}
	//method for reading cell data
	public String getCellData(String SheetName,int row,int colum)
	{
		String data= " ";
		if(wb.getSheet(SheetName).getRow(row).getCell(colum).getCellType()==CellType.NUMERIC)
		{
			int celldata = (int) wb.getSheet(SheetName).getRow(row).getCell(colum).getNumericCellValue();
			data=String.valueOf(true);
			
		}
		else
		{
			data = wb.getSheet(SheetName).getRow(row).getCell(colum).getStringCellValue();
		}
		return data;
	}
	//method for writing
	public void setCellData(String SheetName,int row,int colum,String satus,String WriteExcel) throws Throwable
	{
		XSSFSheet ws = wb.getSheet(SheetName);
		//get row
		XSSFRow rowNum = ws.getRow(row);
		
		XSSFCell cell = rowNum.createCell(colum);
		cell.setCellValue(satus);
		if(satus.equalsIgnoreCase("Pass"))
		{
			XSSFCellStyle Style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(false);
			Style.setFont(font);
			rowNum.getCell(colum).setCellStyle(Style);
		}
		else if(satus.equalsIgnoreCase("Fail"))
		{
			XSSFCellStyle Style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(false);
			Style.setFont(font);
			rowNum.getCell(colum).setCellStyle(Style);
		}
		else if(satus.equalsIgnoreCase("Blocked"))
		{
			XSSFCellStyle Style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(false);
			Style.setFont(font);
			rowNum.getCell(colum).setCellStyle(Style);
		}
		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);
	}

}
