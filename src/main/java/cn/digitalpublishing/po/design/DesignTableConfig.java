/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.digitalpublishing.po.design;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务表设计
 * @author ThinkGem
 * @version 2013-10-15
 */

@SuppressWarnings("serial")
public class DesignTableConfig implements Serializable  {
	
	private String id;//id
	private String tableId;//当前设计所属表ID
	private DesignTable table;//当前设计 所属表
	
	private String type;//类型 1、导出到 ftp 服务器 2、 下载到本地。
	
	//上传代码到ftp服务器
	private String hostName;//ftp服务器地址
	private String port;//ftp服务器端口
	private String username;//ftp登录账户
	private String password;//ftp登录密码
	private String pathname;//ftp 保存路径
	private String originfilename;//待上传文件的名称（绝对地址）
	
	//下载代码到 本地
	//生成结构：{包名}/{模块名}/{分层(dao,entity,service,web)}/{子模块名}/{java类}
	private String packagePath;//生成包路径
	private String projectName;//项目名
	private String moduleName;//模块名
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;

	public DesignTable getTable() {
		return table;
	}

	public void setTable(DesignTable table) {
		this.table = table;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public String getOriginfilename() {
		return originfilename;
	}

	public void setOriginfilename(String originfilename) {
		this.originfilename = originfilename;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
}


