package cn.digitalpublishing.springmvc.form.excel;

import cn.digitalpublishing.po.excel.XlsColumn;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class XlsColumnForm  extends DataTableForm<XlsColumnForm> {
	private String xlsId;
	private XlsColumn column;
	public String getXlsId() {
		return xlsId;
	}
	public void setXlsId(String xlsId) {
		this.xlsId = xlsId;
	}
	public XlsColumn getColumn() {
		return column;
	}
	public void setColumn(XlsColumn column) {
		this.column = column;
	}
	
}
