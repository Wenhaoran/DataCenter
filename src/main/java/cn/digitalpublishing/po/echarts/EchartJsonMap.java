package cn.digitalpublishing.po.echarts;

import java.io.Serializable;
import java.util.Date;

import cn.digitalpublishing.po.export.DataExport;

/**
 * @since 1.0
 * @author Liu Ben
 * @see 01_ echarts 字段存值表
 * @see design_echart_json_map
 */
@SuppressWarnings("serial")
public class EchartJsonMap implements Serializable {
	
	private String id;//资源ID
	private String name;//对象名称
	private String code;//对象编号
	private String type;//类型 1-目录 2-对象
	
	private String echartId;//对应图表
	private Echarts echarts;
	private String expId;//报表ID
	private DataExport exp;//报表
	private String column;//对应报表的字段
	private String jsonId;//对应的json对象Id
	private EchartJson json;//json对象
	private Integer jmapOrder;//对应 json 的第几个对象。
	
	private String rsId;
	private EchartExportRS rs;
	
	private String jsonName;
	private String jsonCode;
	private String temp;
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;	
	
	public EchartExportRS getRs() {
		return rs;
	}
	public void setRs(EchartExportRS rs) {
		this.rs = rs;
	}
	public String getRsId() {
		return rsId;
	}
	public void setRsId(String rsId) {
		this.rsId = rsId;
	}
	public String getJsonName() {
		return jsonName;
	}
	public void setJsonName(String jsonName) {
		this.jsonName = jsonName;
	}
	public String getJsonCode() {
		return jsonCode;
	}
	public void setJsonCode(String jsonCode) {
		this.jsonCode = jsonCode;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getExpId() {
		return expId;
	}
	public void setExpId(String expId) {
		this.expId = expId;
	}
	public DataExport getExp() {
		return exp;
	}
	public void setExp(DataExport exp) {
		this.exp = exp;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getJsonId() {
		return jsonId;
	}
	public void setJsonId(String jsonId) {
		this.jsonId = jsonId;
	}
	public EchartJson getJson() {
		return json;
	}
	public void setJson(EchartJson json) {
		this.json = json;
	}
	public Integer getJmapOrder() {
		return jmapOrder;
	}
	public void setJmapOrder(Integer jmapOrder) {
		this.jmapOrder = jmapOrder;
	}
	public Echarts getEcharts() {
		return echarts;
	}
	public void setEcharts(Echarts echarts) {
		this.echarts = echarts;
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
}