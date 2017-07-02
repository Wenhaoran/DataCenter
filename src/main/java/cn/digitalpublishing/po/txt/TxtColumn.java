package cn.digitalpublishing.po.txt;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see excel 字段配置
 * @see design_txt_column
 */
@SuppressWarnings("serial")
public class TxtColumn implements Serializable {
	
	private String id;
	
	private String code;//字段名
	private String name;//字段描述
	private String type;
	
	private String txtId;//
	private TxtObject txt;
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;//当前字段是否已经 映射 数据库字段  0.未映射， 1.已映射
	
	public String getTxtId() {
		return txtId;
	}
	public void setTxtId(String txtId) {
		this.txtId = txtId;
	}
	public TxtObject getTxt() {
		return txt;
	}
	public void setTxt(TxtObject txt) {
		this.txt = txt;
	}
	public String getId() {
		return id;
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