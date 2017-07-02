package cn.digitalpublishing.springmvc.form.design;

import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class DesignTableForm  extends DataTableForm<DesignTableForm> {
	
	private DesignTable obj = new DesignTable();
	
	private String name;
	
	private String code;
	
	//当前table 所需要关联的 字段名称
	private String[] names;
	

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public DesignTable getObj() {
		return obj;
	}

	public void setObj(DesignTable obj) {
		this.obj = obj;
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
	
}
