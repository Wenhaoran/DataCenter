package cn.digitalpublishing.springmvc.form.excel;

import cn.digitalpublishing.po.excel.XlsObject;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class XlsObjectForm   extends DataTableForm<XlsObjectForm> {
	
	/**
	 * 系统对象
	 */
	private XlsObject obj = new XlsObject();
	
	/**
	 * 系统名称
	 */
	private String name;
	
	/**
	 * 系统编码
	 */
	private String code;
	
	/**
	 * 系统状态
	 */
	
	private Integer status;


	public String getName() {
		return name;
	}


	public String getCode() {
		return code;
	}


	public Integer getStatus() {
		return status;
	}


	public XlsObject getObj() {
		return obj;
	}


	public void setObj(XlsObject obj) {
		this.obj = obj;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

}
