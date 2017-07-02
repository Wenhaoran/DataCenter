package cn.digitalpublishing.service.design.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.DeDBConnectMapper;
import cn.digitalpublishing.mapper.design.DesignTableMapper;
import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.service.design.DesignTableService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("designTableService")
public class DesignTableServiceImpl implements DesignTableService {
	
	@Resource(name="designTableMapper")
    private DesignTableMapper designTableMapper;
	
	@Resource(name="deDBConnectMapper")
    private DeDBConnectMapper deDBConnectMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(designTableMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<DesignTable> findListByCondition(HashMap<String, Object> condition) {
		return designTableMapper.findListByCondition(condition);
	}

	@Override
	public void insert(DesignTable table) {
		designTableMapper.insert(table);
	}

	@Override
	public DesignTable findByTableId(String tbleId) {
		DesignTable table =designTableMapper.findById(tbleId);
		table.setDbconn(deDBConnectMapper.findById(table.getDbId()));
		return table;
	}

	@Override
	public void update(DesignTable obj) {
		designTableMapper.updateById(obj);
	}

	@Override
	public void deleteById(String tableId) {
		designTableMapper.deleteById(tableId);
	}

}
