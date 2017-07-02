package cn.digitalpublishing.springmvc.form.imp;

import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.po.imp.ImportTimeTask;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class ImportTimeTaskForm  extends DataTableForm<ImportTimeTaskForm> {
	
	private String objId;
	private ImportRole column;
	private ImportTimeTask task;
	
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
	public ImportTimeTask getTask() {
		return task;
	}
	public void setTask(ImportTimeTask task) {
		this.task = task;
	}
}
