package cn.digitalpublishing.po.design;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @since 1.0
 * @author Liu Ben
 * @see 01_ 对象设计 树
 * @see SYS_RESOURCE
 */
@SuppressWarnings("serial")
public class DataObject implements Serializable {
	
	private String id;//资源ID
	private String name;//对象名称
	private String code;//对象编号
	private String description;//描述
	private Integer leaf;//叶子节点 1-不是 2-是（只有叶子节点是数据库表）
	private Integer type;//类型 1-目录 2-对象
	private String pId;// 上级资源ID
	
	
	private String tableName;//数据库表名称
	
	private String dbId;//当前对象所属数据库Id
	private String dbName;//所属数据库 名称（数据库别名）
	
	private DeDBConnect dbconn;//对应数据库连接
	
	private List<DataObject> child ;//子节点
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;	// 状态为 0 ，则 说明，当前叶子节点（对象）并没有创建表。  
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDbId() {
		return dbId;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public DeDBConnect getDbconn() {
		return dbconn;
	}
	public void setDbconn(DeDBConnect dbconn) {
		this.dbconn = dbconn;
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
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public List<DataObject> getChild() {
		return child;
	}
	public void setChild(List<DataObject> child) {
		this.child = child;
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