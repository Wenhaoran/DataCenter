package cn.digitalpublishing.service.excel;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.excel.XlsObject;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface XlsObjectService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****XlsObject Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<XlsObject> getXlsObjectList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public XlsObject getXlsObjectById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateXlsObject(XlsObject obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertXlsObject(XlsObject obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteXlsObjectById(String id) throws Exception;


}
