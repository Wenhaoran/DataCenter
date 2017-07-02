package cn.digitalpublishing.springmvc.form.design;

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class DBConnectForm  extends DataTableForm<DBConnectForm> {
	
	/**
	 * 系统对象
	 */
	private DeDBConnect obj = new DeDBConnect();
	
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

	public DeDBConnect getObj() {
		return obj;
	}

	public void setObj(DeDBConnect obj) {
		this.obj = obj;
	}

	public String getName() {
		return name;
	}


	public String getCode() {
		return code;
	}


	public Integer getStatus() {
		return status;
	}

}
