package cn.digitalpublishing.service.txt;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.txt.TxtObject;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface TxtObjectService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);
	
	/****TxtObject Start************************************************************************************************************************/
	/**
	 * 获取系统列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<TxtObject> getTxtObjectList(Map<String,Object> condition) throws Exception;

	/**
	 * 获取系统对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TxtObject getTxtObjectById(String id) throws Exception;

	/**
	 * 修改系统对象
	 * @param obj
	 * @param id
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public void updateTxtObject(TxtObject obj) throws Exception;

	/**
	 * 新增系统对象
	 * @param obj
	 * @throws Exception
	 */
	public void insertTxtObject(TxtObject obj) throws Exception;

	/**
	 * 删除系统
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteTxtObjectById(String id) throws Exception;


}
