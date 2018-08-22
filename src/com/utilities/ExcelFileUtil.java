package com.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.microsoft.schemas.office.visio.x2012.main.CellType;



public class ExcelFileUtil 
{
	
	
	  Workbook wb;
	  
	  public ExcelFileUtil() throws EncryptedDocumentException, InvalidFormatException, IOException 
	  {
		  FileInputStream fis=new FileInputStream(".//Testinput//Testdata.xlsx");
		  wb=WorkbookFactory.create(fis);
	  }
	  
	   public int rowCount(String sheetname) 
	   {
		 return  wb.getSheet(sheetname).getLastRowNum(); 
	   }
	
	      public int colCount(String sheetname, int rownum) 
	      {
	    	  return wb.getSheet(sheetname).getRow(rownum).getLastCellNum();
	      }
	      
	      public String getData(String sheetname,int row, int colnum) 
	      {
	    	  String data ="";
	    	  if (wb.getSheet(sheetname).getRow(row).getCell(colnum).getCellType()==Cell.CELL_TYPE_NUMERIC) 
	    	  {
	    		  int celldata=(int) wb.getSheet(sheetname).getRow(row).getCell(colnum).getNumericCellValue();
	    		  data =String.valueOf(celldata);
	   
			}else 
			{
				data=wb.getSheet(sheetname).getRow(row).getCell(colnum).getStringCellValue();
			}
	    	  return data; 
	      }
	      
	      public void setData( String sheetname, int row, int colnum, String str) throws IOException 
	      {
             Sheet sh=wb.getSheet(sheetname);
             Row rownum=sh.getRow(row);
             Cell cell=rownum.createCell(colnum);
                  cell.setCellValue(str);
                  
                  if (str.equalsIgnoreCase("PASS"))
                  {
                	  CellStyle style=wb.createCellStyle();
                	  Font font =wb.createFont();
                	  font.setColor(IndexedColors.GREEN.getIndex());
                	  font.setBoldweight(font.BOLDWEIGHT_BOLD);
                	  style.setFont(font);
                	  rownum.getCell(colnum).setCellStyle(style);	
				}
                  else if(str.equalsIgnoreCase("FAIL"))
                  {
                	  CellStyle style=wb.createCellStyle();
                	  Font font=wb.createFont();
                	  font.setColor(IndexedColors.RED.getIndex());
                	  font.setBoldweight(font.BOLDWEIGHT_BOLD);
                	  style.setFont(font);
                	  rownum.getCell(colnum).setCellStyle(style);
                  }else 
                  {
                	  if(str.equalsIgnoreCase("Not Executed"))
                	  {
                       CellStyle style=wb.createCellStyle();
                       Font font=wb.createFont();
                       font.setColor(IndexedColors.BLUE.getIndex());
                       font.setBoldweight(font.BOLDWEIGHT_BOLD);
                       style.setFont(font);
                       rownum.getCell(colnum).setCellStyle(style);  
                  }  
                	  
                	  FileOutputStream fos=new FileOutputStream("");
                	  wb.close();
                	  fos.close();
                  }  
	      }
}
