package cn.digitalpublishing.service.design;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface FtpConnectService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****FTPConnect Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<FTPConnect> getFTPConnectList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FTPConnect getFTPConnectById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateFTPConnect(FTPConnect obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertFTPConnect(FTPConnect obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteFTPConnectById(String id) throws Exception;


}
