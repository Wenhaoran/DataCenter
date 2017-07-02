package cn.digitalpublishing.po.imp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.design.DeDBConnect;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 当前规则/当前 文件对象，对应的 多个数据源 。 RT == ROLE --> TABLE 
 * 因为，一条xls 文件数据，可能同时插入多个表中 。
 * @see design_ftp
 */
@SuppressWarnings("serial")
public class ImportRTMapped implements Serializable {
	
	private String id;
	
	private String code ;//编号
	private String name;//名称
	private String type;//类型
	
	private String importRoleId;//规则Id roleId
	/*查询用*/private ImportRole role ;//
	
	private String dbId;//数据源ID 
	/*查询用*/private String dbName;//数据源名称
	/*查询用*/private DeDBConnect conn;//数据源连接
	private String dbTable;//源表
	
	private String sql;//当前规则，当前库表 的 插入sql 语句
	private String paramIds;//sql参数
	/*查询用*/private List<String> names;//参数描述
	
	private String insertSort;//插入顺序   关联外键为 0 ，则 ，插入顺序 0 ，外键1，插入顺序 1 . desc 
	
	/*暂时不用*/private String msType;//插入当前表，是否需要外键
	
	private List<Map<String,String>> keyList;//当前表，所需的外键集合  map --> 外键表 rtId + 外键字段
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
	public List<Map<String, String>> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<Map<String, String>> keyList) {
		this.keyList = keyList;
	}
	public String getParamIds() {
		return paramIds;
	}
	public void setParamIds(String paramIds) {
		this.paramIds = paramIds;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public String getInsertSort() {
		return insertSort;
	}
	public void setInsertSort(String insertSort) {
		this.insertSort = insertSort;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public DeDBConnect getConn() {
		return conn;
	}
	public void setConn(DeDBConnect conn) {
		this.conn = conn;
	}
	public String getDbId() {
		return dbId;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public String getDbTable() {
		return dbTable;
	}
	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}
	public String getImportRoleId() {
		return importRoleId;
	}
	public void setImportRoleId(String importRoleId) {
		this.importRoleId = importRoleId;
	}
	public ImportRole getRole() {
		return role;
	}
	public void setRole(ImportRole role) {
		this.role = role;
	}
	public String getMsType() {
		return msType;
	}
	public void setMsType(String msType) {
		this.msType = msType;
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
}