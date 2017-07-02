package cn.digitalpublishing.springmvc.form.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.util.param.Param;

public class DataTableForm<T> {
	
	private String id;
	
	private String msg;
	
	private String isSuccess;
	
	private String url;
	
	private String httpCode;
	
	private Map<String,Object> condition = new HashMap<String,Object>();

	/**
	 * DataTable请求服务器端次数
	 */
	private int sEcho;

	/**
	 * 每页显示的数量
	 */
	private int iDisplayLength = 10;
	
	/**
	 * 过滤前总记录数
	 */
	private int iTotalRecords;
	
	/**
	 * 过滤后总记录数
	 */
	private int iTotalDisplayRecords;

	/**
	 * 返回的数据
	 */
	private List<T> aaData;
	
	private Integer total;
	private Integer pageSize;

	private Object page;
	private Object rows;
	private Object sort;
	private Object order;
	
	private String _;
	
	//page=1&rows=20&sort=a.inserttime&order=desc
	
	public String get_() {
		return _;
	}

	public void set_(String _) {
		this._ = _;
	}

	private String limit=Param.getParam("button.limit").get("limit");
	
	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	public Object getPage() {
		return page;
	}

	public void setPage(Object page) {
		this.page = page;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public Object getSort() {
		return sort;
	}

	public void setSort(Object sort) {
		this.sort = sort;
	}

	public Object getOrder() {
		return order;
	}

	public void setOrder(Object order) {
		this.order = order;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public List<T> getAaData() {
		return aaData;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
		setTotal(iTotalRecords);
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		setiDisplayLength(pageSize);
	}

	public String getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}
}