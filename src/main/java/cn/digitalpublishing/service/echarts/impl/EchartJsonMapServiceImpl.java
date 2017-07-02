package cn.digitalpublishing.service.echarts.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.echarts.EchartJsonMapMapper;
import cn.digitalpublishing.mapper.echarts.EchartJsonMapper;
import cn.digitalpublishing.mapper.echarts.EchartsMapper;
import cn.digitalpublishing.mapper.export.DataExportMapper;
import cn.digitalpublishing.po.echarts.EchartJson;
import cn.digitalpublishing.po.echarts.EchartJsonMap;
import cn.digitalpublishing.po.export.DataExport;
import cn.digitalpublishing.service.echarts.EchartJsonMapService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("echartJsonMapService")
public class EchartJsonMapServiceImpl implements EchartJsonMapService {
	
	@Resource(name="echartJsonMapMapper")
    private EchartJsonMapMapper echartJsonMapMapper;
	
	@Resource(name="echartsMapper")
    private EchartsMapper echartsMapper;
	
	@Resource(name="echartJsonMapper")
    private EchartJsonMapper echartJsonMapper;
	
	@Resource(name="dataExportMapper")
    private DataExportMapper dataExportMapper;
	
	@Override
	public List<EchartJsonMap> findListByRSId(String rsId) {
		return echartJsonMapMapper.findListByRSId(rsId);
	}

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		List<EchartJsonMap> jsonMapList = echartJsonMapMapper.findPageCondition(pageInfo);
		for(EchartJsonMap jsonMap:jsonMapList){
//			Echarts echarts = echartsMapper.findById(jsonMap.getEchartId());
			EchartJson json = echartJsonMapper.findById(jsonMap.getJsonId());
			DataExport export = dataExportMapper.findById(jsonMap.getExpId());
			jsonMap.setJsonName(json.getName());
			jsonMap.setJsonCode(export.getName());
			jsonMap.setTemp(json.getTemp());
		}
		pageInfo.setRows(jsonMapList);
	}
	
	@Override
	public List<EchartJsonMap> findListByCondition(Map<String, Object> condition) {
		return echartJsonMapMapper.findListByCondition(condition);
	}

	@Override
	public void insert(EchartJsonMap object) {
		echartJsonMapMapper.insert(object);
	}

	@Override
	public EchartJsonMap findByObjectId(String objectId) {
		return echartJsonMapMapper.findById(objectId);
	}

	@Override
	public List<EchartJsonMap> findListByObjectId(String id) {
		return null;
	}

	@Override
	public void update(EchartJsonMap obj) {
		echartJsonMapMapper.updateById(obj);
	}

	@Override
	public void deleteById(String id) {
		echartJsonMapMapper.deleteById(id);
	}

}
