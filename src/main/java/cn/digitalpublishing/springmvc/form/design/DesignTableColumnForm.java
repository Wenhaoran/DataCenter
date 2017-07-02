package cn.digitalpublishing.springmvc.form.design;

import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class DesignTableColumnForm  extends DataTableForm<DesignTableColumnForm> {
	
	private DesignTable table = new DesignTable();
	
	private String[] names;//字段名称集合
	
	private String tableId;

	public DesignTable getTable() {
		return table;
	}

	public void setTable(DesignTable table) {
		this.table = table;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
}
