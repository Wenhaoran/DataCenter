package cn.digitalpublishing.po.imp;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 导入规则 
 * @see design_ftp
 */
@SuppressWarnings("serial")
public class ImportRoleDetail implements Serializable {
	
	private String id;
	
	private String code ;//编号
	private String name;//名称
	private String type;//类型
	
	//所属规则
	private String roleId;//导入规则id 
	/*查询用*/private ImportRole importRole;//导入规则
	
	//映射类型  
	//文件映射--》文件字段，保存到数据库字段
	//主从映射--》主表字段，保存到子表
	//匹配映射--》文件字段，匹配某表某字段，然后，保存需要的字段，到 子表。
	private String mappedType;//0.文件映射   1.主从映射   2.匹配映射
	

	//数据来源 
	private String objId;//对象ID 1.xls对象ID， 2.txt对象ID ， 3.主表importRT ID 
	/*查询用*/private String objName;// 文件对象名称  or 表名称 
	/*查询用*/private Object obj;//文件对象
	private String columnId;//字段ID 1.xls字段ID    2.txt字段ID    3.主表字段
	/*查询用*/private Object column;//对象字段
	/*查询用*/private String columnName;//对象字段
	
	//输入到
	private String importRTId;//文件对象 源表 ID
	/*查询用*/private String tableName;//表名称 
	/*查询用*/private ImportRTMapped rtmapped;//文件对象 源表
	private String tableColumn;//字段名称
	private String columnType;//字段类型
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
	// 二次修改 累加字段 。  匹配数据字典表 保存
	private String matchId;// 当前字段，所需要匹配的资源ID 
	private String matchName;//当前字段，所 需要匹配的字段。
	private String matchCode;//当前字段匹配成功后，需要保存使用的字段。
	
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public ImportRole getImportRole() {
		return importRole;
	}
	public void setImportRole(ImportRole importRole) {
		this.importRole = importRole;
	}
	public String getMappedType() {
		return mappedType;
	}
	public void setMappedType(String mappedType) {
		this.mappedType = mappedType;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public Object getColumn() {
		return column;
	}
	public void setColumn(Object column) {
		this.column = column;
	}
	public String getImportRTId() {
		return importRTId;
	}
	public void setImportRTId(String importRTId) {
		this.importRTId = importRTId;
	}
	public ImportRTMapped getRtmapped() {
		return rtmapped;
	}
	public void setRtmapped(ImportRTMapped rtmapped) {
		this.rtmapped = rtmapped;
	}
	public String getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
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