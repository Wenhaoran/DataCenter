package cn.digitalpublishing.springmvc.form.design;

import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class FTPConnectForm  extends DataTableForm<FTPConnectForm> {
	
	/**
	 * 系统对象
	 */
	private FTPConnect obj = new FTPConnect();
	
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

	public FTPConnect getObj() {
		return obj;
	}

	public void setObj(FTPConnect obj) {
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
