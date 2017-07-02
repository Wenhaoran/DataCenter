package cn.digitalpublishing.mapper.echarts;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.echarts.EchartColumnValue;

public interface EchartColumnValueMapper extends BaseMapper<EchartColumnValue,EchartColumnValue> {

	List<EchartColumnValue> findByRSId(String rsId);

}