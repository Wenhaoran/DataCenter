package cn.digitalpublishing.springmvc.form.imp;

import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class ImportLogForm  extends DataTableForm<ImportLogForm> {
	
	private String objId;
	private ImportRole column;
	
	private Object obj;
	private Object objColumn;
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Object getObjColumn() {
		return objColumn;
	}
	public void setObjColumn(Object objColumn) {
		this.objColumn = objColumn;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public ImportRole getColumn() {
		return column;
	}
	public void setColumn(ImportRole column) {
		this.column = column;
	}
}
