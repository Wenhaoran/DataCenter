package cn.digitalpublishing.springmvc.form.design;

import cn.digitalpublishing.po.design.DataObject;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class DataObjectForm  extends DataTableForm<DataObjectForm> {
	
	/**
	 * 系统对象
	 */
	private DataObject obj = new DataObject();
	
	/**
	 * 系统名称
	 */
	private String name;
	
	/**
	 * 系统编码
	 */
	private String code;
	
	public DataObject getObj() {
		return obj;
	}
	public void setObj(DataObject obj) {
		this.obj = obj;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
}
