package cn.digitalpublishing.po.design;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 1.0
 * @author Liu Ben
 * @see 01_ 对象 字段
 * @see SYS_RESOURCE
 */
@SuppressWarnings("serial")
public class DataField implements Serializable {
	
	private String id;//字段ID
	
	private String code;//字段 值
	private String name;//字段 说明
	private String type;//字段数据类型
	private String size ;//字段长度
	private Integer must;//是否必需  1-是，2-不是
	private String def;//默认值
	private String description;//描述
	
	private String refTable;//其他表
	private String refField;//其他表字段
	
	private String tableName;//当前字段所属表 名称
	private String tableId;//当前字段所属表 ID 
	private DataObject dataObj;//当前字段 对应所属表
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public DataObject getDataObj() {
		return dataObj;
	}
	public void setDataObj(DataObject dataObj) {
		this.dataObj = dataObj;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getMust() {
		return must;
	}
	public void setMust(Integer must) {
		this.must = must;
	}
	public String getRefTable() {
		return refTable;
	}
	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}
	public String getRefField() {
		return refField;
	}
	public void setRefField(String refField) {
		this.refField = refField;
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