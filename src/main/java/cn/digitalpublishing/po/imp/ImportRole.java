package cn.digitalpublishing.po.imp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 导入规则 
 * @see design_ftp
 */
@SuppressWarnings("serial")
public class ImportRole implements Serializable {
	
	private String id;
	
	private String code ;//编号
	private String name;//名称
	private String type;//类型 txt xls
	
	private String objId;//当前规则 的 对象id 
	/*查询用*/private Object obj;//当前规则对应对象
	/*查询用*/private List<?> objColumnList;//当前对象 的 字段集合
	private String objName;//对象名称
	
	private String taskId;//任务ID
	private String cronTask;// * * * * * *  --> 秒，分，时，日，月，周
	private ImportTimeTask task;//
	
	private String importType;//导入状态  0.创建完成 （未配置数据映射表），  1.已配置数据表(未配置导入规则明细)    2.导入规则明细配置完成 （未配置 导入时间）  3.导入时间配置完成。    4.时间配置完成，但是映射配置未完成 , 
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;//状态 0.可用  1.停用
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCronTask() {
		return cronTask;
	}
	public void setCronTask(String cronTask) {
		this.cronTask = cronTask;
	}
	public ImportTimeTask getTask() {
		return task;
	}
	public void setTask(ImportTimeTask task) {
		this.task = task;
	}
	public List<?> getObjColumnList() {
		return objColumnList;
	}
	public void setObjColumnList(List<?> objColumnList) {
		this.objColumnList = objColumnList;
	}
	public String getImportType() {
		return importType;
	}
	public void setImportType(String importType) {
		this.importType = importType;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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