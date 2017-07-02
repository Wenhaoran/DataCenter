package cn.digitalpublishing.service.design;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.design.DesignTableColumn;



public interface DesignTableColumnService {

	void insert(DesignTableColumn column);

	List<DesignTableColumn> getListByCondition(Map<String, Object> condition);

	void deleteById(String id);

	void deleteListByIdsAndTableId(String[] ids, String tableId);

	DesignTableColumn findById(String id);

	void update(DesignTableColumn column);
	
}
