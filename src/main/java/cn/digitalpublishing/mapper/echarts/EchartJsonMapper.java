package cn.digitalpublishing.mapper.echarts;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.echarts.EchartJson;

public interface EchartJsonMapper extends BaseMapper<EchartJson,EchartJson> {

	List<EchartJson> findListByEchartId(String id);

}