package cn.digitalpublishing.po.imp;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 导入规则 
 * @see design_ftp
 */
@SuppressWarnings("serial")
public class ImportTimeTask implements Serializable {
	
	private String id;
	
	private String code ;//编号
	private String name;//名称
	private String type;//类型
	
	private String second;//秒
	private String minute;//分
	private String hour;//时
	private String day;//日
	private String month;//月
	private String week;//周
	
	private String cron;//任务配置
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
	
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
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