package cn.digitalpublishing.service.echarts;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.echarts.EchartColumn;


public interface EchartColumnService {

	List<EchartColumn> findListByCondition(Map<String, Object> condition);

	void insert(EchartColumn object);

	EchartColumn findByObjectId(String objectId);

	List<EchartColumn> findListByEchartId(String id);

	void update(EchartColumn obj);

	void deleteById(String id);

	void deleteListByIdsAndTableId(String[] ids, String tableId);
}
