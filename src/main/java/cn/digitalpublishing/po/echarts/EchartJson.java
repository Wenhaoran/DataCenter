package cn.digitalpublishing.po.echarts;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 1.0
 * @author Liu Ben
 * @see 01_ echarts 字段存值表
 * @see design_echarts
 */
@SuppressWarnings("serial")
public class EchartJson implements Serializable {
	
	private String id;//资源ID
	private String name;//对象名称
	private String code;//对象编号
	
	
	/** 
	 * oneArray 单个元素的数组集合
	 * manyArray 多元素数组 的 数组集合
	 * jsonValueString json格式数据(key:value value：字符串)
	 * jsonValueArray json格式数据(key:value value：数组)
	 * jsonArray 简单json数组
	 * other 任何复杂 数据，请拆成多个简单数据源 */
	private String type;//
	
	private String echartId;//对应图表
	private Echarts echarts;
	private String source;//json源数据
	private String temp;//生成模板
	private Integer mapNum;// 需要 映射 数据个数。
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;	// 状态为 0 ，则 说明，当前叶子节点（对象）并没有创建表。
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public Integer getMapNum() {
		return mapNum;
	}
	public void setMapNum(Integer mapNum) {
		this.mapNum = mapNum;
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