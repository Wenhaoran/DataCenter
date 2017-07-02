package cn.digitalpublishing.springmvc.form.txt;

import cn.digitalpublishing.po.txt.TxtColumn;
import cn.digitalpublishing.springmvc.form.base.DataTableForm;

public class TxtColumnForm  extends DataTableForm<TxtColumnForm> {
	private String xlsId;
	private TxtColumn column;
	public String getXlsId() {
		return xlsId;
	}
	public void setXlsId(String xlsId) {
		this.xlsId = xlsId;
	}
	public TxtColumn getColumn() {
		return column;
	}
	public void setColumn(TxtColumn column) {
		this.column = column;
	}
	
}
