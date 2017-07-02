package cn.digitalpublishing.springmvc.form.base;


public class BaseForm {
	
	private int page;
	private int rows;
	private String sort;
	private String order;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	//page=1&rows=20&sort=a.inserttime&order=desc
	
	
}
