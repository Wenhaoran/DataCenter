package cn.digitalpublishing.service.echarts.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.echarts.EchartExportRSMapper;
import cn.digitalpublishing.mapper.echarts.EchartsMapper;
import cn.digitalpublishing.po.echarts.EchartExportRS;
import cn.digitalpublishing.po.echarts.Echarts;
import cn.digitalpublishing.service.echarts.EchartExportRSService;

@Service("echartExportRSService")
public class EchartExportRSServiceImpl implements EchartExportRSService {
	
	@Resource(name="echartExportRSMapper")
    private EchartExportRSMapper echartExportRSMapper;
	
	@Resource(name="echartsMapper")
    private EchartsMapper echartsMapper;

	@Override
	public List<EchartExportRS> findListByCondition(Map<String, Object> condition) {
		List<EchartExportRS> rsList = echartExportRSMapper.findListByCondition(condition);
		for(EchartExportRS rs:rsList){
			Echarts echarts = echartsMapper.findById(rs.getEchartId());
			rs.setEcharts(echarts);
			rs.setEchartCode(echarts.getCode());
			rs.setEchartName(echarts.getName());
		}
		return rsList;
	}

	@Override
	public void insert(EchartExportRS object) {
		echartExportRSMapper.insert(object);
	}

	@Override
	public EchartExportRS findByObjectId(String objectId) {
		return echartExportRSMapper.findById(objectId);
	}

	@Override
	public List<EchartExportRS> findListByObjectId(String id) {
		return null;
	}

	@Override
	public void update(EchartExportRS obj) {
		echartExportRSMapper.updateById(obj);
	}

	@Override
	public void deleteById(String id) {
		echartExportRSMapper.deleteById(id);
	}
	

}
