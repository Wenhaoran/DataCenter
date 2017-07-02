package cn.digitalpublishing.service.export;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.export.DataExport;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface DataExportService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****DataExport Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<DataExport> getDataExportList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DataExport getDataExportById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateDataExport(DataExport obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertDataExport(DataExport obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteDataExportById(String id) throws Exception;


}
