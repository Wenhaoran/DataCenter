/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.digitalpublishing.po.design;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 业务方案表Entity
 * @author ThinkGem
 * @version 2013-10-15
 */

@SuppressWarnings("serial")
public class DesignTable implements Serializable  {
	
	private String id;//id
	
	private String tableName;//表名称
	private String name; 	// 名称
	private String comments;		// 描述
	private String className;		// 实体类名称
	
	/*类型  
	 * type == 1  选择表 并 配置基本信息  结束，需要 选择列。
	 * type == 2  选择列 结束，需要 生成方案配置。
	 * type == 3  方案配置 结束。可以导出 代码*/
	private String type;
	
	private String dbId;//当前对象所属数据库Id
	
	private String parentTable;		// 关联父表
	private String parentTableFk;		// 关联父表外键
	private List<DesignTableColumn> columnList;	// 表列
//	private String nameLike; 	// 按名称模糊查询
	private List<String> pkList; // 当前表主键列表
	private String pId;//父表ID
	private DesignTable parent;	// 父表对象
	
	private List<DesignTable> childList ;	// 子表列表
	
	private String[] names;//表字段集合
	
	private String dbName;//所属数据库 名称（数据库别名）
	
	private DeDBConnect dbconn;//对应数据库连接
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getParentTable() {
		return parentTable;
	}
	public void setParentTable(String parentTable) {
		this.parentTable = parentTable;
	}
	public String getParentTableFk() {
		return parentTableFk;
	}
	public void setParentTableFk(String parentTableFk) {
		this.parentTableFk = parentTableFk;
	}
	public List<DesignTableColumn> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<DesignTableColumn> columnList) {
		this.columnList = columnList;
	}
	public List<String> getPkList() {
		return pkList;
	}
	public void setPkList(List<String> pkList) {
		this.pkList = pkList;
	}
	public DesignTable getParent() {
		return parent;
	}
	public void setParent(DesignTable parent) {
		this.parent = parent;
	}
	public List<DesignTable> getChildList() {
		return childList;
	}
	public void setChildList(List<DesignTable> childList) {
		this.childList = childList;
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


