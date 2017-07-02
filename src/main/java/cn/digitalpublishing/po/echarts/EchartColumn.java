package cn.digitalpublishing.po.echarts;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 1.0
 * @author Liu Ben
 * @see 01_ 对象设计 树
 * @see design_echarts
 */
@SuppressWarnings("serial")
public class EchartColumn implements Serializable {
	
	private String id;//资源ID
	private String name;//对象名称
	private String code;//对象编号
	private Integer type;//类型 1-目录 2-对象
	
	private String echartId;// 上级资源ID
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;	
	
	public String getEchartId() {
		return echartId;
	}
	public void setEchartId(String echartId) {
		this.echartId = echartId;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
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

	
	
}