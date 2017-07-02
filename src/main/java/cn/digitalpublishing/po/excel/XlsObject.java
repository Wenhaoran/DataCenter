package cn.digitalpublishing.po.excel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.po.imp.ImportTimeTask;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 01_数据库连接配置
 * @see design_excel
 */
@SuppressWarnings("serial")
public class XlsObject implements Serializable {
	
	private String id;
	
	private String name;//对象名称
	private String code ;// 文件名  --  可以设置为，扫描 当前目录下的 当前文件。
	
	private String pathType;//目录类型
	private String ftpId;//FTPID
	private FTPConnect ftp;
	private String path;//excel文件路径
	private String pathSuccess;//excel 成功路径
	private String pathError;//excel 错误路径
	
	private String type;//当前对象，是否已经关联db 
	
	private String dbId;//数据源ID
	private DeDBConnect dbconn;//关联表 的 数据源 
	private String dbTable;//关联表名
	private DesignTable table;//关联表
	
	private String mapped;//是否映射完成 0. 否，没有映射完成   1.映射完成，可以进行导入数据
	
	private String roleId;//导入规则Id
	private ImportTimeTask ImportTimeTask;//导入规则
	private String ifRole;//是否已经导入
	
	private Map<String,String> xlsValue;// key== 当前对象的所有 字段，value==每条字段的值
	private List<Map<String,String>> kyList ;//保存xls 每行记录的 List
	private List<String> keyList;//保存 当前文件 行表头 的list
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
	public List<Map<String, String>> getKyList() {
		return kyList;
	}
	public void setKyList(List<Map<String, String>> kyList) {
		this.kyList = kyList;
	}
	public List<String> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}
	public Map<String, String> getXlsValue() {
		return xlsValue;
	}
	public void setXlsValue(Map<String, String> xlsValue) {
		this.xlsValue = xlsValue;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public ImportTimeTask getImportTimeTask() {
		return ImportTimeTask;
	}
	public void setImportTimeTask(ImportTimeTask ImportTimeTask) {
		this.ImportTimeTask = ImportTimeTask;
	}
	public String getIfRole() {
		return ifRole;
	}
	public void setIfRole(String ifRole) {
		this.ifRole = ifRole;
	}
	public FTPConnect getFtp() {
		return ftp;
	}
	public void setFtp(FTPConnect ftp) {
		this.ftp = ftp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPathType() {
		return pathType;
	}
	public void setPathType(String pathType) {
		this.pathType = pathType;
	}
	public String getFtpId() {
		return ftpId;
	}
	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPathSuccess() {
		return pathSuccess;
	}
	public void setPathSuccess(String pathSuccess) {
		this.pathSuccess = pathSuccess;
	}
	public String getPathError() {
		return pathError;
	}
	public void setPathError(String pathError) {
		this.pathError = pathError;
	}
	public String getDbId() {
		return dbId;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public DeDBConnect getDbconn() {
		return dbconn;
	}
	public void setDbconn(DeDBConnect dbconn) {
		this.dbconn = dbconn;
	}
	public String getDbTable() {
		return dbTable;
	}
	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}
	public DesignTable getTable() {
		return table;
	}
	public void setTable(DesignTable table) {
		this.table = table;
	}
	public String getMapped() {
		return mapped;
	}
	public void setMapped(String mapped) {
		this.mapped = mapped;
	}

}