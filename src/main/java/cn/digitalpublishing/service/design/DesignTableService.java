package cn.digitalpublishing.service.design;

import java.util.HashMap;
import java.util.List;

import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.util.mybatis.page.PageInfo;


public interface DesignTableService {
	
	/**
     * 分页 查询
     * @param pageInfo
     */
    void findDataGrid(PageInfo pageInfo);

	List<DesignTable> findListByCondition(HashMap<String, Object> condition);

	void insert(DesignTable table);

	DesignTable findByTableId(String tableId);

	void update(DesignTable obj);

	void deleteById(String tableId);
}
