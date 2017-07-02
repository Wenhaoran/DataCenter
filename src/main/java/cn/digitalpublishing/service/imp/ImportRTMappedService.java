package cn.digitalpublishing.service.imp;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.imp.ImportRTMapped;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface ImportRTMappedService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****ImportRTMapped Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<ImportRTMapped> getImportRTMappedList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ImportRTMapped getImportRTMappedById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateImportRTMapped(ImportRTMapped obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertImportRTMapped(ImportRTMapped obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteImportRTMappedById(String id) throws Exception;

	void deleteListByIds(String[] ids);

	List<ImportRTMapped> getImportRTMappedByRoleId(String id);

	//在 规则映射完成后，根据当前规则id ，创建 所有rt 的 insert SQL
	void createSql(String roleId,String createId)throws Exception;


}
