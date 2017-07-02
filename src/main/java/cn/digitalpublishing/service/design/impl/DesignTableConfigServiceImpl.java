package cn.digitalpublishing.service.design.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.DesignTableConfigMapper;
import cn.digitalpublishing.po.design.DesignTableConfig;
import cn.digitalpublishing.service.design.DesignTableConfigService;

@Service("designTableConfigService")
public class DesignTableConfigServiceImpl implements DesignTableConfigService {
	
	@Resource(name="designTableConfigMapper")
    private DesignTableConfigMapper designTableConfigMapper;

	@Override
	public void insert(DesignTableConfig config) {
		designTableConfigMapper.insert(config);
	}

	@Override
	public List<DesignTableConfig> getListByCondition(Map<String, Object> condition) {
		return designTableConfigMapper.findListByCondition(condition);
	}

	@Override
	public void deleteById(String id) {
		designTableConfigMapper.deleteById(id);
	}

	@Override
	public DesignTableConfig getConfigByTableId(String tableId) {
		return designTableConfigMapper.getConfigByTableId(tableId);
	}

	@Override
	public void update(DesignTableConfig config) {
		designTableConfigMapper.updateById(config);
	}
	
}
