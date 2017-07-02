package cn.digitalpublishing.po.txt;

import java.io.Serializable;
import java.util.Date;

import cn.digitalpublishing.po.design.FTPConnect;

/**
 * @since 1.0			2.0
 * @author Liu Ben		
 * @see 01_数据库连接配置
 * @see design_txt
 */
@SuppressWarnings("serial")
public class TxtObject implements Serializable {
	
	private String id;
	
	private String name;//对象名称
	private String code ;// 文件名  --  可以设置为，扫描 当前目录下的 当前文件。
	private String type;//分割类型    ;/| 
	
	private String pathType;//目录类型
	private String ftpId;//FTPID
	private FTPConnect ftp;
	private String path;//excel文件路径
	private String pathSuccess;//excel 成功路径
	private String pathError;//excel 错误路径
	
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private Integer status;
	
	public FTPConnect getFtp() {
		return ftp;
	}
	public void setFtp(FTPConnect ftp) {
		this.ftp = ftp;
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
	public String getPathType() {
		return pathType;
	}
	public void setPathType(String pathType) {
		this.pathType = pathType;
	}
	public String getFtpId() {
		return ftpId;
	}
	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPathSuccess() {
		return pathSuccess;
	}
	public void setPathSuccess(String pathSuccess) {
		this.pathSuccess = pathSuccess;
	}
	public String getPathError() {
		return pathError;
	}
	public void setPathError(String pathError) {
		this.pathError = pathError;
	}

}