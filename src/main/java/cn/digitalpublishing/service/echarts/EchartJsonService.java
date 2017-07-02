package cn.digitalpublishing.service.echarts;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.echarts.EchartJson;


public interface EchartJsonService {

	List<EchartJson> findListByCondition(Map<String, Object> condition);

	void insert(EchartJson object);

	EchartJson findByObjectId(String objectId);

	List<EchartJson> findListByEchartId(String id);

	void update(EchartJson obj);

	void deleteById(String id);

	void deleteListByIdsAndTableId(String[] ids, String tableId);
}
