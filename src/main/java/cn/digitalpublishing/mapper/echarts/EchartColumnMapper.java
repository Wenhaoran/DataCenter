package cn.digitalpublishing.mapper.echarts;

import java.util.List;

import cn.digitalpublishing.mapper.BaseMapper;
import cn.digitalpublishing.po.echarts.EchartColumn;

public interface EchartColumnMapper extends BaseMapper<EchartColumn,EchartColumn> {

	List<EchartColumn> findListByEchartId(String id);

}