package cn.digitalpublishing.service.excel;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.excel.XlsColumn;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface XlsColumnService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****XlsColumn Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<XlsColumn> getXlsColumnList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public XlsColumn getXlsColumnById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateXlsColumn(XlsColumn obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertXlsColumn(XlsColumn obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteXlsColumnById(String id) throws Exception;

	
	void batchDelByXlsId(String xlsId, String[] ids);

	List<XlsColumn> getXlsObjectListByXlxId(String id);

}
