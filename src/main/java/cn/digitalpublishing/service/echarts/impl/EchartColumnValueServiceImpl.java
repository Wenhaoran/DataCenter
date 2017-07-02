package cn.digitalpublishing.service.echarts.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.echarts.EchartColumnValueMapper;
import cn.digitalpublishing.po.echarts.EchartColumnValue;
import cn.digitalpublishing.service.echarts.EchartColumnValueService;

@Service("echartColumnValueService")
public class EchartColumnValueServiceImpl implements EchartColumnValueService {
	
	@Resource(name="echartColumnValueMapper")
    private EchartColumnValueMapper echartColumnValueMapper;
	
	@Override
	public List<EchartColumnValue> findListByRSId(String rsId) {
		return echartColumnValueMapper.findByRSId(rsId);
	}

	@Override
	public List<EchartColumnValue> findListByCondition(Map<String, Object> condition) {
		return echartColumnValueMapper.findListByCondition(condition);
	}

	@Override
	public void insert(EchartColumnValue object) {
		echartColumnValueMapper.insert(object);
	}

	@Override
	public EchartColumnValue findByObjectId(String objectId) {
		return echartColumnValueMapper.findById(objectId);
	}

	@Override
	public List<EchartColumnValue> findListByObjectId(String id) {
		return null;
	}

	@Override
	public void update(EchartColumnValue obj) {
		echartColumnValueMapper.updateById(obj);
	}

	@Override
	public void deleteById(String id) {
		echartColumnValueMapper.deleteById(id);
	}

}
