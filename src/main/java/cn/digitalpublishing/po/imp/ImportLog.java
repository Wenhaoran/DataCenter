package cn.digitalpublishing.po.imp;

import java.io.Serializable;
import java.util.Date;

import cn.digitalpublishing.po.design.DeDBConnect;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 导入日志
 * @see design_import_log
 */
@SuppressWarnings("serial")
public class ImportLog implements Serializable {
	
	private String id;
	
	private String code ;//编号
	private String name;//名称
	private String type;//成功，失败  

	private String roleId;//导入规则Id
	private String fileType;//日志 类型 1.excel xls  2.text txt
	
	private String fileName;//导入文件名
	private ImportRole role;//导入规则
	
	private String objName;//导入对象名称
	private String objId;//导入对象ID
	
	private Object obj;//导入的对象
	private String objPath;//导入对象的 文件路径
	
	private String dbId;//当前对象，导入的链接。
	private DeDBConnect conn;//映射链接
	private String table;//映射表
	
	private String errorInfo;//错误原因
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public ImportRole getRole() {
		return role;
	}
	public void setRole(ImportRole role) {
		this.role = role;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
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
	public String getObjPath() {
		return objPath;
	}
	public void setObjPath(String objPath) {
		this.objPath = objPath;
	}
	public String getDbId() {
		return dbId;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public DeDBConnect getConn() {
		return conn;
	}
	public void setConn(DeDBConnect conn) {
		this.conn = conn;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}