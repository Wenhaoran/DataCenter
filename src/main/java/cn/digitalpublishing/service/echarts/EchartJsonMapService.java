package cn.digitalpublishing.service.echarts;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.echarts.EchartJsonMap;
import cn.digitalpublishing.util.mybatis.page.PageInfo;


public interface EchartJsonMapService {

	List<EchartJsonMap> findListByCondition(Map<String, Object> condition);

	void insert(EchartJsonMap object);

	EchartJsonMap findByObjectId(String objectId);

	List<EchartJsonMap> findListByObjectId(String id);

	void update(EchartJsonMap obj);

	void deleteById(String id);

	void findDataGrid(PageInfo pageInfo);

	List<EchartJsonMap> findListByRSId(String rsId);
}
