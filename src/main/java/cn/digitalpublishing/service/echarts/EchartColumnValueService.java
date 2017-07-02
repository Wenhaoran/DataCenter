package cn.digitalpublishing.service.echarts;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.echarts.EchartColumnValue;


public interface EchartColumnValueService {

	List<EchartColumnValue> findListByCondition(Map<String, Object> condition);

	void insert(EchartColumnValue object);

	EchartColumnValue findByObjectId(String objectId);

	List<EchartColumnValue> findListByObjectId(String id);

	void update(EchartColumnValue obj);

	void deleteById(String id);

	List<EchartColumnValue> findListByRSId(String rsId);
}
