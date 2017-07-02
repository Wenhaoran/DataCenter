package cn.digitalpublishing.util.imp.text;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @FileName：FileUtils.java
 * @Description：文件操作的工具类
 * @author: NQY
 * @Version: 1.0
 * @Createtime: 2008-12-12
 */

public class TextUtils {

	public static List<Map<String, String>> dataMapList(File file,String type) throws Exception{
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		List<Map<String,String>> dataMapList = new ArrayList<>();
		
		List<String> txtColumnList = txtColumnList(file,type);
		List<List<String>> txtDataList = txtDataList(file,type);
		for(List<String> txtData : txtDataList){
			Map<String,String> txtDataMap = new HashMap<>();
			for(int i = 0;i<txtColumnList.size();i++){
				txtDataMap.put(txtColumnList.get(i), txtData.get(i));
			}
			dataMapList.add(txtDataMap);
		}
		
		reader.close();
		in.close();
		return dataMapList;
	}

	private static List<List<String>> txtDataList(File file,String type)throws Exception {
		List<List<String>> txtDataList = new ArrayList<List<String>>();
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		reader.readLine();
		
		int lineNum = getTotalLines(file);
		
		for(int i = 1;i<lineNum;i++){
			String line = reader.readLine();
			String fgf = split(type);
			String[] txtArray = line.split(fgf);
			List<String> dateLine = new ArrayList<String>();
			for(String str : txtArray){
				dateLine.add(str);
			}
			txtDataList.add(dateLine);
		}
		reader.close();
		in.close();
		return txtDataList;
	}

	private static List<String> txtColumnList(File file,String type)throws Exception {
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String firstLine = reader.readLine();
		String fgf = split(type);
		String[] txtArray = firstLine.split(fgf);
		Set<String> txtSet = new HashSet<String>();
		List<String> txtList = new ArrayList<String>();
		
		for (String str : txtArray) {  
			txtSet.add(str);
			txtList.add(str);
        }
		
		reader.close();
		in.close();
		return txtList;
	}
	
	private static String split(String type) {
		if("|".equals(type)){
			return "\\u007C";
		}else if("|".equals(type)){
			
		}
		return null;
	}

	public static boolean validate(File file, Set<String> columnSet,String type) throws Exception{
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String firstLine = reader.readLine();
		String fgf = split(type);
		String[] txtArray = firstLine.split(fgf);
		Set<String> txtSet = new HashSet<String>();
		List<String> txtList = new ArrayList<String>();
		
		for (String str : txtArray) {  
			txtSet.add(str);
			txtList.add(str);
        }
		
		boolean isFullEqual = true;
		if(txtList.size() == columnSet.size()){
			for (int i = 0; i < txtList.size(); i++) {
				if (!columnSet.contains(txtList.get(i))) {
					isFullEqual = false;
				}
			}
		}else{
			isFullEqual = false;
		}
		
		reader.close();
		in.close();
		return isFullEqual;
	}

	// 文件内容的总行数。
	static int getTotalLines(File file) throws IOException {
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String s = reader.readLine();
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
		}
		reader.close();
		in.close();
		return lines;
	}

}