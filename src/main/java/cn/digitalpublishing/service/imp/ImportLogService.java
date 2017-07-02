package cn.digitalpublishing.service.imp;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.imp.ImportLog;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface ImportLogService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****ImportLog Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<ImportLog> getImportLogList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ImportLog getImportLogById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateImportLog(ImportLog obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertImportLog(ImportLog obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteImportLogById(String id) throws Exception;


}
