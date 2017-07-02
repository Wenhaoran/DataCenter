/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.digitalpublishing.po.design;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务表字段Entity
 * @author ThinkGem
 * @version 2013-10-15
 */
@SuppressWarnings("serial")
public class DesignTableColumn implements Serializable  {
	
	private String id;//id
	
	private String tableId;//所属表ID
	private DesignTable genTable;	// 归属表
	private String name; 		// 列名
	private String type; 		// 类型
	private String comments;	// 描述
	private String javaType;	// JAVA类型
	private String javaField;	// JAVA字段名
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public DesignTable getGenTable() {
		return genTable;
	}
	public void setGenTable(DesignTable genTable) {
		this.genTable = genTable;
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
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getJavaField() {
		return javaField;
	}
	public void setJavaField(String javaField) {
		this.javaField = javaField;
	}

	
}


