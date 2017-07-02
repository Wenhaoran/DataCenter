package cn.digitalpublishing.springmvc.form.echarts;

import cn.digitalpublishing.po.echarts.Echarts;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class EchartsForm  extends DataTableForm<EchartsForm> {
	
	/**
	 * 系统对象
	 */
	private Echarts obj = new Echarts();
	
	/**
	 * 系统名称
	 */
	private String name;
	
	/**
	 * 系统编码
	 */
	private String code;
	
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public Echarts getObj() {
		return obj;
	}
	public void setObj(Echarts obj) {
		this.obj = obj;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
