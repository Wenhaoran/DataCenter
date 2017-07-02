package cn.digitalpublishing.service.echarts;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.po.echarts.EchartJsonMap;
import cn.digitalpublishing.po.echarts.Echarts;
import cn.digitalpublishing.util.mybatis.page.PageInfo;


public interface EchartsService {

	List<Echarts> findListByCondition(Map<String, Object> condition);

	void insert(Echarts object);

	Echarts findByObjectId(String objectId);

	List<Echarts> findListByObjectId(String id);

	void update(Echarts obj);

	void deleteById(String id);

	void findDataGrid(PageInfo pageInfo);

	List<String> getJsonString(String jsonType,String template, List<EchartJsonMap> jsonMapList)throws Exception;

	List<Map<String, Object>> getjsonArray(String jsonType, String template, List<EchartJsonMap> jsonMapList)throws Exception;
}
