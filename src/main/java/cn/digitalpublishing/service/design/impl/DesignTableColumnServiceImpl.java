package cn.digitalpublishing.service.design.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.DesignTableColumnMapper;
import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.service.design.DesignTableColumnService;

@Service("designTableColumnService")
public class DesignTableColumnServiceImpl implements DesignTableColumnService {
	
	@Resource(name="designTableColumnMapper")
    private DesignTableColumnMapper designTableColumnMapper;

	@Override
	public void insert(DesignTableColumn column) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("tableId", column.getTableId());
		condition.put("name", column.getName());
		List<DesignTableColumn> columnList = designTableColumnMapper.findListByCondition(condition);
		if(columnList.size()==0){
			designTableColumnMapper.insert(column);
		}
	}

	@Override
	public List<DesignTableColumn> getListByCondition(Map<String, Object> condition) {
		
		return designTableColumnMapper.findListByCondition(condition);
	}

	@Override
	public void deleteById(String id) {
		designTableColumnMapper.deleteById(id);
	}

	@Override
	public void deleteListByIdsAndTableId(String[] ids, String tableId) {
		for(String id : ids){
			designTableColumnMapper.deleteById(id);
		}
	}

	@Override
	public DesignTableColumn findById(String id) {
		return designTableColumnMapper.findById(id);
	}

	@Override
	public void update(DesignTableColumn column) {
		designTableColumnMapper.updateById(column);
	}
	
}
