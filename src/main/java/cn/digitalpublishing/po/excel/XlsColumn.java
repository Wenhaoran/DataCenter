package cn.digitalpublishing.po.excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see excel 字段配置
 * @see design_xls_column
 */
@SuppressWarnings("serial")
public class XlsColumn implements Serializable {
	
	private String id;
	
	private String code;//字段名
	private String name;//字段描述
	private String type;
	
	private String xlsId;//对应的excel 的ID 
	/*查询用*/private XlsObject xls ;//对应excel
	
	private String value;//值
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;//当前字段是否已经 映射 数据库字段  0.未映射， 1.已映射
	
	
	//以下是暂时废弃字段，所以没删
	private String tabColumn;//数据库字段
	private String tabColumnType;//字段类型
	
	public String getTabColumn() {
		return tabColumn;
	}
	public void setTabColumn(String tabColumn) {
		this.tabColumn = tabColumn;
	}
	public String getTabColumnType() {
		return tabColumnType;
	}
	public void setTabColumnType(String tabColumnType) {
		this.tabColumnType = tabColumnType;
	}
	public String getId() {
		return id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getXlsId() {
		return xlsId;
	}
	public void setXlsId(String xlsId) {
		this.xlsId = xlsId;
	}
	public XlsObject getXls() {
		return xls;
	}
	public void setXls(XlsObject xls) {
		this.xls = xls;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}