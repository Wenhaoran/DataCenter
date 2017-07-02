package cn.digitalpublishing.service.design.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.DataFieldMapper;
import cn.digitalpublishing.po.design.DataField;
import cn.digitalpublishing.service.design.DataFieldService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("dataFieldService")
public class DataFieldServiceImpl implements DataFieldService {
	
	@Resource(name="dataFieldMapper")
    private DataFieldMapper dataFieldMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(dataFieldMapper.findPageCondition(pageInfo));
	}

	@Override
	public void insert(DataField field) {
		dataFieldMapper.insert(field);
	}

	@Override
	public List<DataField> findListByCondition(HashMap<String, Object> condition) {
		return dataFieldMapper.findListByCondition(condition);
	}
	
	@Override
	public void deleteById(String id) {
		dataFieldMapper.deleteById(id);
	}

	@Override
	public void deleteListByIds(String[] ids) {
		for(String id :ids ){
			dataFieldMapper.deleteById(id);
		}
	}

	@Override
	public DataField findById(String id) {
		return dataFieldMapper.findById(id);
	}

	@Override
	public void update(DataField field) {
		dataFieldMapper.updateById(field);
	}
}
