package cn.digitalpublishing.service.imp;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.imp.ImportTimeTask;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface ImportTimeTaskService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****ImportTimeTask Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<ImportTimeTask> getImportTimeTaskList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ImportTimeTask getImportTimeTaskById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateImportTimeTask(ImportTimeTask obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertImportTimeTask(ImportTimeTask obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteImportTimeTaskById(String id) throws Exception;


}
