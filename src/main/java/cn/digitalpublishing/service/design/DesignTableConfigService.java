package cn.digitalpublishing.service.design;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.design.DesignTableConfig;



public interface DesignTableConfigService {

	void insert(DesignTableConfig config);

	List<DesignTableConfig> getListByCondition(Map<String, Object> condition);

	void deleteById(String id);

	DesignTableConfig getConfigByTableId(String tableId);

	void update(DesignTableConfig config);
	
}
