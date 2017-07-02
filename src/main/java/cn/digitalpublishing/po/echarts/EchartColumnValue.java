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
public class EchartColumnValue implements Serializable {
	
	private String id;//资源ID
	private String name;//对象名称
	private String code;//对象编号
	private Integer type;//类型 1-目录 2-对象
	
	private String echartId;//对应图表
	private Echarts echarts;
	private String expId;//对应报表
	private DataExport export;
	private String colId;//对应字段
	private EchartColumn col;
	private String colVal;//字段 值
	
	private String rsId;
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;	// 状态为 0 ，则 说明，当前叶子节点（对象）并没有创建表。
	
	public String getRsId() {
		return rsId;
	}
	public void setRsId(String rsId) {
		this.rsId = rsId;
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
	public EchartColumn getCol() {
		return col;
	}
	public void setCol(EchartColumn col) {
		this.col = col;
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
	public String getEchartId() {
		return echartId;
	}
	public void setEchartId(String echartId) {
		this.echartId = echartId;
	}
	public String getExpId() {
		return expId;
	}
	public void setExpId(String expId) {
		this.expId = expId;
	}
	public String getColId() {
		return colId;
	}
	public void setColId(String colId) {
		this.colId = colId;
	}
	public String getColVal() {
		return colVal;
	}
	public void setColVal(String colVal) {
		this.colVal = colVal;
	}
}