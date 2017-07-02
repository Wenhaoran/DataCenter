package cn.digitalpublishing.po.echarts;

import java.io.Serializable;
import java.util.Date;

import cn.digitalpublishing.po.export.DataExport;

/**
 * @since 1.0
 * @author Liu Ben
 * @see 01_ echarts 字段存值表
 * @see design_echarts
 */
@SuppressWarnings("serial")
public class EchartExportRS implements Serializable {
	
	private String id;//资源ID
	
	private String name;
	private String code;
	private String type;
	
	private String echartId;//对应图表
	private Echarts echarts;
	private String echartName;
	private String echartCode;
	
	private String exportId;//对应报表
	private DataExport export;
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;	
	
	
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
	public String getEchartName() {
		return echartName;
	}
	public void setEchartName(String echartName) {
		this.echartName = echartName;
	}
	public String getEchartCode() {
		return echartCode;
	}
	public void setEchartCode(String echartCode) {
		this.echartCode = echartCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getEchartId() {
		return echartId;
	}
	public void setEchartId(String echartId) {
		this.echartId = echartId;
	}
	public String getExportId() {
		return exportId;
	}
	public void setExportId(String exportId) {
		this.exportId = exportId;
	}
	public Echarts getEcharts() {
		return echarts;
	}
	public void setEcharts(Echarts echarts) {
		this.echarts = echarts;
	}
	public DataExport getExport() {
		return export;
	}
	public void setExport(DataExport export) {
		this.export = export;
	}
}