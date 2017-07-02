package cn.digitalpublishing.service.design;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface DeDBConnectService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****DBConnect Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<DeDBConnect> getDBConnectList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DeDBConnect getDBConnectById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateDBConnect(DeDBConnect obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertDBConnect(DeDBConnect obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteDBConnectById(String id) throws Exception;

}
