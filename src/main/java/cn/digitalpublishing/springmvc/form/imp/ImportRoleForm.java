package cn.digitalpublishing.springmvc.form.imp;

import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class ImportRoleForm  extends DataTableForm<ImportRoleForm> {
	
	private String objId;
	private ImportRole column;
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
