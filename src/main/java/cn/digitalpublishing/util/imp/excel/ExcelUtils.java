package cn.digitalpublishing.util.imp.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel文件操作工具类，包括读、写、合并等功能
 * 
 * @author : 龙轩
 * @group : tgb8
 * @Version : 1.00
 * @Date : 2014-10-29 上午12:40:44
 */
public class ExcelUtils {
	
	private static ExcelUtils xlsUtils;
	private List<String> xlsColumn;
	private File file;
	
	public ExcelUtils(){}
	
	public ExcelUtils(File file) {
		this.file = file;
	}
	
	private static synchronized ExcelUtils get(File file) {
		if (xlsUtils == null) {
			xlsUtils = new ExcelUtils();
			xlsUtils.file = file;
		}
		return xlsUtils;
	}
	
	public static List<String> getXlsColumnList(File file){
		return get(file).getXlsColumn();
	}

	//**************上面的代码，暂时没用*************************
	
	public static boolean validate(File file, Set<String> columnSet) throws Exception{

		List<Row> rowList = getRow(file);

		//获取 当前excel 字段属性。
		Row row = rowList.get(0);
		
		Set<String> xlsSet = new HashSet<String>();
		List<String> xlsList = new ArrayList<String>();
		for (int j = 0; j < row.getLastCellNum(); j++) {  
            String value = getCellValue(row.getCell(j));  
            xlsSet.add(value);
            xlsList.add(value);
        }
		
		boolean isFullEqual = true;
		if(xlsList.size() == columnSet.size()){
			for (int i = 0; i < xlsList.size(); i++) {
				if (!columnSet.contains(xlsList.get(i))) {
					isFullEqual = false;
				}
			}
		}else{
			isFullEqual = false;
		}
		return isFullEqual;
	}

	public static List<Map<String,String>> dataMapList(File file)throws Exception{
		List<Map<String,String>> dataMapList = new ArrayList<>();
		List<String> xlsColumnList = xlsColumnList(file);
		List<List<String>> xlsDataList = xlsDataList(file);
		for(List<String> xlsData : xlsDataList){
			Map<String,String> xlsDataMap = new HashMap<>();
			for(int i = 0;i<xlsColumnList.size();i++){
				xlsDataMap.put(xlsColumnList.get(i), xlsData.get(i));
			}
			dataMapList.add(xlsDataMap);
		}
		
		return dataMapList;
	}
	
	public static List<String> xlsColumnList(File file) throws Exception{
		List<String> xlsColumnList = new ArrayList<String>();
		List<Row> rowList = getRow(file);
		Row row = rowList.get(0);
		for (int j = 0; j < row.getLastCellNum(); j++) {  
            String value = getCellValue(row.getCell(j));  
            xlsColumnList.add(value);
        }
		
		return xlsColumnList;
	}
	
	public static List<List<String>> xlsDataList(File file) throws Exception{
		List<List<String>> xlsDataList = new ArrayList<List<String>>();
		
		List<Row> rowList = getRow(file);
		int size = rowList.size();
		for(int i = 1;i<size;i++){
			Row row = rowList.get(i);
			List<String> oneRowList = new ArrayList<String>();
			for (int j = 0; j < row.getLastCellNum(); j++) {  
				Cell cell = row.getCell(j);
	            String value = getCellValue(cell);  
	            oneRowList.add(value);
	        }
			xlsDataList.add(oneRowList);
		}
		return xlsDataList;
	}
	
	
	public static List<Row> getRow(File file)throws Exception{ 
		String fileName = file.getName();
		// 获取扩展名
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

		List<Row> rowList = new ArrayList<Row>();
		if ("xls".equals(ext)) { // 使用xls方式写入
			rowList = readExcel_xls(file);
		} else if ("xlsx".equals(ext)) { // 使用xlsx方式写入
			rowList = readExcel_xlsx(file);
		}
		
		return rowList;
	}
	
	public static List<Row> readExcel_xls(File file) throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));  
		List<Row> xlsColumn  = readExcel(wb);
		return xlsColumn;
	}

	public static List<Row> readExcel_xlsx(File file) throws Exception{
		XSSFWorkbook wb = new XSSFWorkbook( new FileInputStream(file));  
		List<Row> xlsColumn = readExcel(wb);
		return xlsColumn;
	}
	
	public static List<Row> readExcel(Workbook wb)throws Exception{
		List<Row> xlsColumn = new ArrayList<Row>();
		Sheet sheet = wb.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();  
		Row row = null;  
		for(int i = 0;i<lastRowNum+1;i++){
			row = sheet.getRow(i);
			xlsColumn.add(row);
		}
		return xlsColumn;
	}

	/*** 
     * 读取单元格的值 
     *  
     * @Title: getCellValue 
     * @Date : 2014-9-11 上午10:52:07 
     * @param cell 
     * @return 
     */  
    private static String getCellValue(Cell cell) {  
        Object result = "";  
        if (cell != null) {  
            switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_STRING:  
                result = cell.getStringCellValue();  
                break;  
            case Cell.CELL_TYPE_NUMERIC:  
                result = cell.getNumericCellValue();  
                break;  
            case Cell.CELL_TYPE_BOOLEAN:  
                result = cell.getBooleanCellValue();  
                break;  
            case Cell.CELL_TYPE_FORMULA:  
                result = cell.getCellFormula();  
                break;  
            case Cell.CELL_TYPE_ERROR:  
                result = cell.getErrorCellValue();  
                break;  
            case Cell.CELL_TYPE_BLANK:  
                break;  
            default:  
                break;  
            }  
        }  
        return result.toString();  
    }  
	
	public List<String> getXlsColumn() {
		return xlsColumn;
	}

}
