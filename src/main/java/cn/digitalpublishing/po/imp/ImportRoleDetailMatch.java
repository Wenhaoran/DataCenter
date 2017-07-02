package cn.digitalpublishing.po.imp;

import java.io.Serializable;
import java.util.Date;

import cn.digitalpublishing.po.design.DeDBConnect;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 导入规则 
 * @see design_ftp
 */
@SuppressWarnings("serial")
public class ImportRoleDetailMatch implements Serializable {
	
	private String id;
	
	private String type;//类型
	
	private String dbId;//数据源ID
	private DeDBConnect conn;//数据源连接
	private String table;//匹配表
	private String name;//匹配字段
	private String code ;//查询字段
	
	private String detailId;//当前匹配 对应 规则明细
	private ImportRoleDetail detail;
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
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
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
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
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public ImportRoleDetail getDetail() {
		return detail;
	}
	public void setDetail(ImportRoleDetail detail) {
		this.detail = detail;
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