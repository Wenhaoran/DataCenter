package cn.digitalpublishing.service.echarts.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.echarts.EchartJsonMapper;
import cn.digitalpublishing.po.echarts.EchartJson;
import cn.digitalpublishing.service.echarts.EchartJsonService;

@Service("echartJsonService")
public class EchartJsonServiceImpl implements EchartJsonService {
	
	@Resource(name="echartJsonMapper")
    private EchartJsonMapper echartJsonMapper;

	@Override
	public List<EchartJson> findListByCondition(Map<String, Object> condition) {
		return echartJsonMapper.findListByCondition(condition);
	}

	@Override
	public void insert(EchartJson object) {
		echartJsonMapper.insert(object);
	}

	@Override
	public EchartJson findByObjectId(String objectId) {
		return echartJsonMapper.findById(objectId);
	}

	@Override
	public List<EchartJson> findListByEchartId(String id) {
		return echartJsonMapper.findListByEchartId(id);
	}

	@Override
	public void update(EchartJson obj) {
		echartJsonMapper.updateById(obj);
	}

	@Override
	public void deleteById(String id) {
		echartJsonMapper.deleteById(id);
	}
	
	@Override
	public void deleteListByIdsAndTableId(String[] ids, String tableId) {
		for(String id : ids){
			echartJsonMapper.deleteById(id);
		}
	}
}
