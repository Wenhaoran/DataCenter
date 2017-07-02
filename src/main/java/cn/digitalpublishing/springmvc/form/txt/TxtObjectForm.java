package cn.digitalpublishing.springmvc.form.txt;

import cn.digitalpublishing.po.txt.TxtObject;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class TxtObjectForm   extends DataTableForm<TxtObjectForm> {
	
	/**
	 * 系统对象
	 */
	private TxtObject obj = new TxtObject();
	
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


	public TxtObject getObj() {
		return obj;
	}


	public void setObj(TxtObject obj) {
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
