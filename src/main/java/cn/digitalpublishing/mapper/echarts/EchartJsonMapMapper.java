package cn.digitalpublishing.mapper.echarts;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.echarts.EchartJsonMap;

public interface EchartJsonMapMapper extends BaseMapper<EchartJsonMap,EchartJsonMap> {

	List<EchartJsonMap> findListByRSId(String rsId);

}