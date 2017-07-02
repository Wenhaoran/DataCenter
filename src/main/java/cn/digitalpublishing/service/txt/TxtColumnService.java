package cn.digitalpublishing.service.txt;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.txt.TxtColumn;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface TxtColumnService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****TxtColumn Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<TxtColumn> getTxtColumnList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TxtColumn getTxtColumnById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateTxtColumn(TxtColumn obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertTxtColumn(TxtColumn obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteTxtColumnById(String id) throws Exception;

	
	void batchDelByTxtId(String xlsId, String[] ids);

	//根据 txt 对象ID ，获取 所有txt字段
	List<TxtColumn> getTxtColumnListByTxtId(String id);


}
