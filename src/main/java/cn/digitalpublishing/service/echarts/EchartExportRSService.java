package cn.digitalpublishing.service.echarts;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.echarts.EchartExportRS;


public interface EchartExportRSService {

	List<EchartExportRS> findListByCondition(Map<String, Object> condition);

	void insert(EchartExportRS object);

	EchartExportRS findByObjectId(String objectId);

	List<EchartExportRS> findListByObjectId(String id);

	void update(EchartExportRS obj);

	void deleteById(String id);
}
