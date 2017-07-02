package cn.digitalpublishing.service.base.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.digitalpublishing.mapper.design.DeDBConnectMapper;
import cn.digitalpublishing.mapper.design.FtpConnectMapper;
import cn.digitalpublishing.mapper.excel.XlsColumnMapper;
import cn.digitalpublishing.mapper.excel.XlsObjectMapper;
import cn.digitalpublishing.mapper.imp.ImportLogMapper;
import cn.digitalpublishing.mapper.imp.ImportRTMappedMapper;
import cn.digitalpublishing.mapper.imp.ImportRoleDetailMapper;
import cn.digitalpublishing.mapper.imp.ImportRoleDetailMatchMapper;
import cn.digitalpublishing.mapper.imp.ImportRoleMapper;
import cn.digitalpublishing.mapper.txt.TxtColumnMapper;
import cn.digitalpublishing.mapper.txt.TxtObjectMapper;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.po.excel.XlsColumn;
import cn.digitalpublishing.po.excel.XlsObject;
import cn.digitalpublishing.po.imp.ImportLog;
import cn.digitalpublishing.po.imp.ImportRTMapped;
import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.po.imp.ImportRoleDetail;
import cn.digitalpublishing.po.imp.ImportRoleDetailMatch;
import cn.digitalpublishing.po.txt.TxtColumn;
import cn.digitalpublishing.po.txt.TxtObject;
import cn.digitalpublishing.service.base.BaseService;
import cn.digitalpublishing.util.imp.FileUtils;
import cn.digitalpublishing.util.imp.FtpUtil;
import cn.digitalpublishing.util.imp.excel.ExcelUtils;
import cn.digitalpublishing.util.imp.text.TextUtils;
import cn.digitalpublishing.util.sql.DBUtil;

@Service("baseService")
public class BaseServiceImpl implements BaseService {
	
	private Map<String,String> keyMap = new HashMap<String,String>();
	
	@Resource(name="txtColumnMapper")
    private TxtColumnMapper txtColumnMapper;
	
	@Resource(name="txtObjectMapper")
    private TxtObjectMapper txtObjectMapper;
	
	@Resource(name="importRoleMapper")
    private ImportRoleMapper importRoleMapper;
	
	@Resource(name="importRoleDetailMapper")
    private ImportRoleDetailMapper importRoleDetailMapper;
	
	@Resource(name="importRTMappedMapper")
    private ImportRTMappedMapper importRTMappedMapper;
	
	@Resource(name="xlsObjectMapper")
    private XlsObjectMapper xlsObjectMapper;
	
	@Resource(name="xlsColumnMapper")
    private XlsColumnMapper xlsColumnMapper;
	
	@Resource(name="deDBConnectMapper")
    private DeDBConnectMapper deDBConnectMapper;
	
	@Resource(name="ftpConnectMapper")
    private FtpConnectMapper ftpConnectMapper;
	
	@Resource(name="importLogMapper")
    private ImportLogMapper importLogMapper;
	
	@Resource(name="importRoleDetailMatchMapper")
    private ImportRoleDetailMatchMapper importRoleDetailMatchMapper;

	@Override
	public void checkFileAndSave(ImportRole role,ImportLog log) throws Exception{
		String type = role.getType();
		//获取 当前规则 所有的数据源
		List<ImportRTMapped> rtList = importRTMappedMapper.findListByRoleId(role.getId());
		//获取当前 规则 的所有 映射 子规则
		List<ImportRoleDetail> detailList = importRoleDetailMapper.findListByRoleId(role.getId());
		
		List<ImportRoleDetail> fileDetail = new ArrayList<ImportRoleDetail>();
		List<ImportRoleDetail> tableDetail = new ArrayList<ImportRoleDetail>();
		List<ImportRoleDetail> matchDetail = new ArrayList<ImportRoleDetail>();
		
		//判断 当前规则类型，属于xls 
		if(type.equals("xls")){
			//获取xls 对象属性
			XlsObject xls = xlsObjectMapper.findById(role.getObjId());
			log.setObjName(xls.getName());
			log.setObjPath(xls.getPath());
			//获取对象所有 字段集合
			List<XlsColumn> columnList = xlsColumnMapper.getColumnByXlsId(xls.getId());
			
			for(ImportRoleDetail detail:detailList){
				if(detail.getMappedType().equals("0")){
					XlsColumn column = xlsColumnMapper.findById(detail.getColumnId());
					detail.setColumn(column);
					detail.setColumnName(column.getCode());
					fileDetail.add(detail);
				}else if(detail.getMappedType().equals("1")){
					detail.setColumnName(detail.getColumnId());
					tableDetail.add(detail);
				}else if(detail.getMappedType().equals("2")){
					XlsColumn column = xlsColumnMapper.findById(detail.getColumnId());
					detail.setColumn(column);
					detail.setColumnName(column.getCode());
					matchDetail.add(detail);
				}
			}
			List<File> xlsFileList = checkExcelFile(xls,columnList);
			
			for(File file : xlsFileList){
				log.setFileName(file.getName());
				//从 excel 中，获取的每一行记录
				List<Map<String,String>> dataMapList = ExcelUtils.dataMapList(file);
				for(int i =0;i<dataMapList.size();i++){
					Map<String,String> xlsData = dataMapList.get(i);
					try {
						//xlsData == excel 文件的 一行记录
						Map<String,String> fileDetailValue = getFileMapped(xlsData,fileDetail);//文件映射 <detailId : xls值>
						Map<String,String> tableDetailValue = getTableMapped(tableDetail);
						Map<String,String> matchDetailValue = getMatchMapped(xlsData,matchDetail);
						
						insertRow(fileDetailValue,tableDetailValue,matchDetailValue,rtList,log);
						
					} catch (Exception e) {
						log.setType("失败");
						log.setErrorInfo("文件："+file.getName()+"，第"+i+"行保存失败。");
						importLogMapper.insert(log);
						e.printStackTrace();
					}
				}
				if("1".equals(xls.getPathType())){
					FTPConnect ftp = ftpConnectMapper.findById(xls.getFtpId());
					FtpUtil.remove(ftp.getAddress(),0,ftp.getUsername(),ftp.getPassword(),file.toString(),xls.getPath(),xls.getPathSuccess());
				}else{
					FileUtils.remove(file.toString(),xls.getPathSuccess());
				}
			}
			role.setObjColumnList(columnList);
			role.setObj(xls);
		}else if(("txt").equals(type)){
			TxtObject txt = txtObjectMapper.findById(role.getObjId());
			log.setObjName(txt.getName());
			log.setObjPath(txt.getPath());
			
			List<TxtColumn> columnList = txtColumnMapper.getColumnByTxtId(txt.getId());
			for(ImportRoleDetail detail:detailList){
				if(detail.getMappedType().equals("0")){
					TxtColumn column = txtColumnMapper.findById(detail.getColumnId());
					detail.setColumn(column);
					detail.setColumnName(column.getCode());
					fileDetail.add(detail);
				}else if(detail.getMappedType().equals("1")){
					detail.setColumnName(detail.getColumnId());
					tableDetail.add(detail);
				}else if(detail.getMappedType().equals("2")){
					TxtColumn column = txtColumnMapper.findById(detail.getColumnId());
					detail.setColumn(column);
					detail.setColumnName(column.getCode());
					matchDetail.add(detail);
				}
			}
			List<File> txtFileList = checkTextFile(txt,columnList);
			for(File file : txtFileList){
				log.setFileName(file.getName());
				List<Map<String,String>> dataMapList = TextUtils.dataMapList(file,txt.getType());
				for(Map<String,String> xlsData : dataMapList){
					//xlsData == excel 文件的 一行记录
					Map<String,String> fileDetailValue = getFileMapped(xlsData,fileDetail);//文件映射 <detailId : xls值>
					Map<String,String> tableDetailValue = getTableMapped(tableDetail);
					Map<String,String> matchDetailValue = getMatchMapped(xlsData,matchDetail);
					
					insertRow(fileDetailValue,tableDetailValue,matchDetailValue,rtList,log);
				}
				if("1".equals(txt.getPathType())){
					FTPConnect ftp = ftpConnectMapper.findById(txt.getFtpId());
					FtpUtil.remove(ftp.getAddress(),0,ftp.getUsername(),ftp.getPassword(),file.toString(),txt.getPath(),txt.getPathSuccess());
				}else{
					FileUtils.remove(file.toString(),txt.getPathSuccess());
				}
			}
			role.setObjColumnList(columnList);
			role.setObj(txt);
		}
	}
	
	private Map<String, String> getMatchMapped(Map<String,String> xlsData,List<ImportRoleDetail> matchDetail) throws Exception{
		Map<String,String> detailValue = new HashMap<String, String>();
		for(ImportRoleDetail detail:matchDetail){
			String matchValue = xlsData.get(detail.getColumnName());
			ImportRoleDetailMatch match =  importRoleDetailMatchMapper.findByDetailId(detail.getId());
			String key = match.getDbId()+match.getTable()+match.getName()+matchValue;
			String value = "";
			if(keyMap.containsKey(key)&&!Strings.isNullOrEmpty(keyMap.get(key))){
				value = keyMap.get(key);
			}else{
				DeDBConnect conn = deDBConnectMapper.findById(match.getDbId());
				value = DBUtil.getValueByMatch(conn,match.getTable(),match.getName(),match.getCode(),matchValue);
				if(Strings.isNullOrEmpty(value)){
					throw new Exception("匹配规则查询失败，请重新设置匹配规则");
				}
				keyMap.put(key, value);
			}
			detailValue.put(detail.getId(), value);
		}
		return detailValue;
	}

	private void insertRow(Map<String, String> fileDetailValue,Map<String, String> tableDetailValue,Map<String, String> matchDetailValue,  List<ImportRTMapped> rtList,ImportLog log)throws Exception{
		Collections.sort(rtList,new Comparator<ImportRTMapped>(){  
			@Override
			public int compare(ImportRTMapped rt1, ImportRTMapped rt2) {
				return rt1.getInsertSort().compareTo(rt2.getInsertSort());
			}
        });
		for(ImportRTMapped rt:rtList){
			log.setDbId(rt.getDbId());
			log.setTable(rt.getDbTable());
			DeDBConnect conn = deDBConnectMapper.findById(rt.getDbId());
			rt.setConn(conn);
			//ids 是，当前 数据源 的参数集合
			String[] ids = rt.getParamIds().split(",");
			String[] keys = new String[ids.length+1] ;
			String uuid = UUID.randomUUID().toString().replace("-", "");
			keys[0] = uuid;
			if(rt.getInsertSort().equals("0")){
				for(int i = 0;i<ids.length;i++){
					if(!ids[i].equals("")){
						if(fileDetailValue.containsKey(ids[i])){
							keys[i+1]=fileDetailValue.get(ids[i]);
						}else if(matchDetailValue.containsKey(ids[i])){
							keys[i+1]=matchDetailValue.get(ids[i]);
						}
					}
				}
			}else{
				for(int i = 0;i<ids.length;i++){
					if(!ids[i].equals("")){
						if(fileDetailValue.containsKey(ids[i])){
							keys[i+1]=fileDetailValue.get(ids[i]);
						}else if(tableDetailValue.containsKey(ids[i])){
							keys[i+1]=tableDetailValue.get(ids[i]);
						}else if(matchDetailValue.containsKey(ids[i])){
							keys[i+1]=matchDetailValue.get(ids[i]);
						}
					}
				}
			}
			insertRT(rt,keys);
			List<ImportRoleDetail> keyDetail = importRoleDetailMapper.findListByKeyRTId(rt.getId());
			Map<String,String> returnValue = getReturnKey(rt,uuid,keyDetail);
			Set<String> set = returnValue.keySet();
			for(String str : set){
				tableDetailValue.put(str, returnValue.get(str));
			}
		}
	}

	private Map<String,String> getReturnKey(ImportRTMapped rt,String returnId, List<ImportRoleDetail> keyDetail)throws Exception{
		Map<String,String> valueReturn = DBUtil.executeSqlgetValueReturn(rt.getConn(),rt.getDbTable(),returnId,keyDetail);
		return valueReturn;
	}

	private void insertRT(ImportRTMapped rt, String[] keys) throws Exception{
		DBUtil.executeSql(rt.getConn(),rt.getSql(),keys);
	}

	public Map<String,String> getTableMapped(List<ImportRoleDetail> detailList){
		Map<String,String> tableDetailValue = new HashMap<String, String>();
		for(ImportRoleDetail detail: detailList){
			tableDetailValue.put(detail.getId(), "");
		}
		return tableDetailValue;
	}
	
	/**
	 * 
	 * @param xlsData xls每行记录
	 * @param detailList 文件映射数据库字段 的 规则 集合
	 * @return
	 */
	public Map<String,String> getFileMapped(Map<String,String> xlsData,List<ImportRoleDetail> detailList){
		//xlsData 等于，excel 文件的，每一行记录  key:文件字段名 ，value:文件字段值。
		Map<String,String> detailValue = new HashMap<String, String>();
		for(ImportRoleDetail detail:detailList){
			detailValue.put(detail.getId(), xlsData.get(detail.getColumnName()));
		}
		return detailValue;
	}
	
	public synchronized List<File> checkExcelFile(XlsObject xls,List<XlsColumn> columnList )throws Exception{
		// pathType == 1 ,ftp 服务器。  == 2 服务器本地路径。
		String pathType = xls.getPathType(); 
		String filePath = xls.getPath();
		String errorPath = xls.getPathError();
		//excel 对象中的，字段集合
		Set<String> columnSet = new HashSet<String>(); 
		for(XlsColumn column : columnList){
			columnSet.add(column.getCode());
		}
		List<File> xlsFileList = new ArrayList<File>();
			if("1".equals(pathType)){//操作ftp 路径的文件
				FTPConnect ftp = ftpConnectMapper.findById(xls.getFtpId());
				Boolean ifFTP = FtpUtil.testFTPConnect(ftp.getAddress(),ftp.getUsername(),ftp.getPassword());
				if(!ifFTP){
					throw new Exception("FTP链接失败。");
				}
				List<File> fileList= FtpUtil.getFileList(ftp.getAddress(),0,ftp.getUsername(),ftp.getPassword(),filePath);

				if(fileList.size()==0){
					throw new Exception("当前目录下，没有xls,xlsx 文件，请验证路径是否填写正确。");
				}
				
				//循环所有文件
				for(File file:fileList){
					//验证，当前文件，是否匹配 excel 对象规则
					if(ExcelUtils.validate(file,columnSet)){
						//文件验证成功 ，先执行插入操作。。
						xlsFileList.add(file);
					}else{
						String fileName = file.toString();
						//文件验证不成功，直接 移动 文件，到 失败路径
						FtpUtil.remove(ftp.getAddress(),0,ftp.getUsername(),ftp.getPassword(),fileName,filePath,errorPath);
					}
				}
			}else if("2".equals(pathType)){//操作服务器本地文件
				List<File> fileList= FileUtils.getExcelFileList(filePath);
				//当前目录下，没有excel 文件，保存 导入日志后，抛出 路径错误异常
				if(fileList.size()==0){
					throw new Exception("当前目录下，没有xls,xlsx 文件，请验证路径是否填写正确。");
				}
				//循环所有文件
				for(File file:fileList){
					//验证，当前文件，是否匹配 excel 对象规则
					if(ExcelUtils.validate(file,columnSet)){
						//文件验证成功 ，先执行插入操作。。
						xlsFileList.add(file);
					}else{
						String fileName = file.toString();
						//文件验证不成功，直接 移动 文件，到 失败路径
						FileUtils.remove(fileName,errorPath);
					}
				}
			}
		return xlsFileList;
	}
	
	
	
	public synchronized List<File> checkTextFile(TxtObject txt,List<TxtColumn> columnList )throws Exception{
		// pathType == 1 ,ftp 服务器。  == 2 服务器本地路径。
		String pathType = txt.getPathType(); 
		String filePath = txt.getPath();
		String errorPath = txt.getPathError();
		//excel 对象中的，字段集合
		Set<String> columnSet = new HashSet<String>(); 
		for(TxtColumn column : columnList){
			columnSet.add(column.getCode());
		}
		List<File> txtFileList = new ArrayList<File>();
			if("1".equals(pathType)){//操作ftp 路径的文件
				FTPConnect ftp = ftpConnectMapper.findById(txt.getFtpId());
				Boolean ifFTP = FtpUtil.testFTPConnect(ftp.getAddress(),ftp.getUsername(),ftp.getPassword());
				if(!ifFTP){
					throw new Exception("FTP链接失败。");
				}
				List<File> fileList= FtpUtil.getFileList(ftp.getAddress(),0,ftp.getUsername(),ftp.getPassword(),filePath);

				if(fileList.size()==0){
					throw new Exception("当前目录下，没有txt,txt 文件，请验证路径是否填写正确。");
				}
				//循环所有文件
				for(File file:fileList){
					//验证，当前文件，是否匹配 excel 对象规则
					if(TextUtils.validate(file,columnSet,txt.getType())){
						//文件验证成功 ，先执行插入操作。。
						txtFileList.add(file);
					}else{
						String fileName = file.toString();
						//文件验证不成功，直接 移动 文件，到 失败路径
						FtpUtil.remove(ftp.getAddress(),0,ftp.getUsername(),ftp.getPassword(),fileName,filePath,errorPath);
					}
				}
			}else if("2".equals(pathType)){//操作服务器本地文件
				List<File> fileList= FileUtils.getTextFileList(filePath);
				//当前目录下，没有excel 文件，保存 导入日志后，抛出 路径错误异常
				if(fileList.size()==0){
					throw new Exception("当前目录下，没有txt,txt 文件，请验证路径是否填写正确。");
				}
				//循环所有文件
				for(File file:fileList){
					//验证，当前文件，是否匹配 excel 对象规则
					if(TextUtils.validate(file,columnSet,txt.getType())){
						//文件验证成功 ，先执行插入操作。。
						txtFileList.add(file);
					}else{
						String fileName = file.toString();
						//文件验证不成功，直接 移动 文件，到 失败路径
						FileUtils.remove(fileName,errorPath);
					}
				}
			}
		return txtFileList;
	}
	
}
