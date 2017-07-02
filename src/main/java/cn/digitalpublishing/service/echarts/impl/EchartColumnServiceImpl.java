package cn.digitalpublishing.service.echarts.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.echarts.EchartColumnMapper;
import cn.digitalpublishing.po.echarts.EchartColumn;
import cn.digitalpublishing.service.echarts.EchartColumnService;

@Service("echartColumnService")
public class EchartColumnServiceImpl implements EchartColumnService {
	
	@Resource(name="echartColumnMapper")
    private EchartColumnMapper echartColumnMapper;

	@Override
	public List<EchartColumn> findListByCondition(Map<String, Object> condition) {
		return echartColumnMapper.findListByCondition(condition);
	}

	@Override
	public void insert(EchartColumn object) {
		echartColumnMapper.insert(object);
	}

	@Override
	public EchartColumn findByObjectId(String objectId) {
		return echartColumnMapper.findById(objectId);
	}

	@Override
	public List<EchartColumn> findListByEchartId(String id) {
		return echartColumnMapper.findListByEchartId(id);
	}

	@Override
	public void update(EchartColumn obj) {
		echartColumnMapper.updateById(obj);
	}

	@Override
	public void deleteById(String id) {
		echartColumnMapper.deleteById(id);
	}
	
	@Override
	public void deleteListByIdsAndTableId(String[] ids, String tableId) {
		for(String id : ids){
			echartColumnMapper.deleteById(id);
		}
	}
}
