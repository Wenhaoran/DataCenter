package com.design;

import java.io.File;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyTask implements Runnable {

	public ArrayList<Workbook> list;

	public String path;

	private Statement stat;

	public MyTask(ArrayList<Workbook> list, String path, Statement stat) {
		this.stat = stat;
		this.list = list;
		this.path = path;
	}

	@Override
	public void run() {
		try {
			File file1 = new File(path);
			/*
			 * if(!file1.getName().endsWith(".xlsx")||file1.getName().startsWith(
			 * "~")) continue;
			 */
			// System.out.println(file1.getName());
			// InputStream instream = new FileInputStream(file1);
			OPCPackage open = OPCPackage.open(path);
			Workbook readwb = new XSSFWorkbook(open);
			operate(readwb, stat);
			System.out.println(file1.getName() + "插入完成");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getCellFormatValue(XSSFCell cell) {
		String value = "";
//		DecimalFormat df = new DecimalFormat("0");// 格式化 number String
		// 字符
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 格式化日期字符串
		// DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字

		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {

			case XSSFCell.CELL_TYPE_STRING:
				// System.out.println(i + "行" + j + " 列 is String type");
				value = cell.getStringCellValue();
				/*
				 * if(value.equals("")) return "int(11)";
				 */
				return value;
			case XSSFCell.CELL_TYPE_NUMERIC:
				// System.out.println(i + "行" + j
				// + " 列 is Number type ; DateFormt:"
				// + cell.getCellStyle().getDataFormatString());
				/*
				 * if ("@".equals(cell.getCellStyle().getDataFormatString())) {
				 * value = df.format(cell.getNumericCellValue());
				 * 
				 * } else if ("General".equals(cell.getCellStyle()
				 * .getDataFormatString())) { value =
				 * (int)cell.getNumericCellValue()+""; } else { value =
				 * sdf.format(HSSFDateUtil.getJavaDate(cell
				 * .getNumericCellValue())); }
				 */
				if (DateUtil.isCellDateFormatted(cell))
					value = sdf.format(cell.getNumericCellValue());
				else {
					value = ((int) cell.getNumericCellValue()) + "";

				}
				/*
				 * if(value.equals("")) return "int(11)";
				 */
				return value;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				// System.out.println(i + "行" + j + " 列 is Boolean type");
				value = cell.getBooleanCellValue() + "";
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				// System.out.println(i + "行" + j + " 列 is Blank type");
				value = "";
				// System.out.println(value);
				return "";
			case XSSFCell.CELL_TYPE_FORMULA:
				try {
					value = String.valueOf((int) cell.getNumericCellValue());
				} catch (IllegalStateException e) {
					value = String.valueOf(cell.getRichStringCellValue());
				}
				return value;
			default:
				// System.out.println(i + "行" + j + " 列 is default type");
				value = cell.toString();
				return "";
			}
			if (value == null || "".equals(value)) {
				return "";
			}
			return value;

		} else
			return "";
	}

	public void operate(Workbook readwb, Statement stat) throws Exception {
		synchronized (list) {
			Sheet readsheet = readwb.getSheetAt(0);
			// 获取Sheet表中所包含的总列数
			int rsColumns = readsheet.getRow(1).getLastCellNum();
			// 获取Sheet表中所包含的总行数
			int rsRows = readsheet.getLastRowNum();
			// String table =
			// file1.getName().substring(file1.getName().indexOf(".")+1,
			// file1.getName().indexOf(".xlsx")).toLowerCase();
			String table = readsheet.getSheetName().trim();
			String dropString = "drop table if exists " + table;
			stat.execute(dropString);
			String createString = "CREATE TABLE `";
			String key = null;

			// System.out.println(table);
			createString += (table + "` (");
			// 先循环行
			/*
			 * 第0行 cs 代表客户端或者后端用 第一行代表字段 第二行代表类型 第三行前端用的 第四行以后代表具体数据 需要插入数据库中
			 */
			Row row = null;
			Row row1 = null;
			Row ro = null;
			// System.out.println("开始创建"+rsColumns);
			// 服务器需要加载的列
			ArrayList<Integer> server = new ArrayList<Integer>();
			for (int i = 0; i < rsColumns; i++) {
				ro = readsheet.getRow(0);
				row = readsheet.getRow(1);
				row1 = readsheet.getRow(2);
				// System.out.println(getCellFormatValue((XSSFCell)ro.getCell(i)));
				if (!(getCellFormatValue((XSSFCell) ro.getCell(i)).equals("cs")) 
						&& !(getCellFormatValue((XSSFCell) ro.getCell(i)) .equals("s")) 
						&& !(getCellFormatValue((XSSFCell) ro.getCell(i)).equals("1.00"))
						&& !(getCellFormatValue((XSSFCell) ro.getCell(i)).equals("1")))
					continue;
				else if (getCellFormatValue((XSSFCell) ro.getCell(i)).equals("1.00")|| getCellFormatValue((XSSFCell) ro.getCell(i)).equals("1"))
					key = getCellFormatValue((XSSFCell) row.getCell(i));
				server.add(Integer.valueOf(i));
				if (row1 != null) {
					if (i == 0) {
						createString += "`" + getCellFormatValue((XSSFCell) row.getCell(i)) + "` " + getCellFormatValue((XSSFCell) row1.getCell(i)) + " NOT NULL DEFAULT '0' " + ",";
					} else {
						String nn = getCellFormatValue((XSSFCell) row .getCell(i));
						String yy = getCellFormatValue((XSSFCell) row1 .getCell(i));
						if (!nn.equals("")) {
							if (yy.equals(""))
								yy = "int(11)";
							createString += "`" + nn + "` " + yy + " DEFAULT NULL " + ",";
						}
					}
				} else {
					// 说明类型
					if (i == 0) {
						createString += "`" + getCellFormatValue((XSSFCell) row.getCell(i)) + "` int(11)   NOT NULL DEFAULT '0' " + ",";
					} else {
						createString += "`" + getCellFormatValue((XSSFCell) row.getCell(i)) + "` int(11)  DEFAULT NULL " + ",";
					}
				}

			}
			createString += "PRIMARY KEY (`" + key + "`)";
			createString += ") ;";
			stat.execute(createString);

			// System.out.println(table+"创建成功");
			// System.out.println("开始插入"+rsColumns);

			for (int i = 5; i <= rsRows; i++) {
				String insertString = "INSERT INTO " + table + " VALUES ( ";
				row = readsheet.getRow(i);
				if (getCellFormatValue((XSSFCell) row.getCell(0)).equals(""))
					continue;
				for (Integer j : server) {
					String context = getCellFormatValue((XSSFCell) row
							.getCell(j));
					if (!context.equals("")) {

						if (j != server.get(server.size() - 1))insertString += "\'"+ getCellFormatValue((XSSFCell) row.getCell(j)) + "\',";
						else
							insertString += "\'"+ getCellFormatValue((XSSFCell) row.getCell(j)) + "\');";
					} else {
						if (j != server.get(server.size() - 1))
							insertString += "''" + ",";
						else
							insertString += "''" + ");";
					}
				}
				stat.execute(insertString);
			}
		}
	}
}
