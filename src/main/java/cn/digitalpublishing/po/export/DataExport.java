package cn.digitalpublishing.po.export;

import java.io.Serializable;
import java.util.Date;

import cn.digitalpublishing.po.design.DeDBConnect;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 01_数据导出
 * @see design_export_data
 */
@SuppressWarnings("serial")
public class DataExport implements Serializable {
	
	private String id;
	
	private String name;//对象名称
	private String code ;// 文件名  --  可以设置为，扫描 当前目录下的 当前文件。
	private String type;//当前对象，是否已经关联db 
	
	private String dbId;//数据源ID
	private String connName;//数据源名称
	private DeDBConnect conn;//数据连接
	private String sql;//sql语句 
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
	
	public String getConnName() {
		return connName;
	}
	public void setConnName(String connName) {
		this.connName = connName;
	}
	public DeDBConnect getConn() {
		return conn;
	}
	public void setConn(DeDBConnect conn) {
		this.conn = conn;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDbId() {
		return dbId;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
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