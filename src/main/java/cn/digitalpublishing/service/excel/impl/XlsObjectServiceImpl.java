package cn.digitalpublishing.service.excel.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.excel.XlsObjectMapper;
import cn.digitalpublishing.po.excel.XlsObject;
import cn.digitalpublishing.service.excel.XlsObjectService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("xlsObjectService")
public class XlsObjectServiceImpl implements XlsObjectService {
	
	@Resource(name="xlsObjectMapper")
    private XlsObjectMapper xlsObjectMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(xlsObjectMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<XlsObject> getXlsObjectList(Map<String, Object> condition)throws Exception {
		return xlsObjectMapper.findListByCondition(condition);
	}

	@Override
	public XlsObject getXlsObjectById(String id) throws Exception {
		return xlsObjectMapper.findById(id);
	}

	@Override
	public void updateXlsObject(XlsObject obj) throws Exception {
		xlsObjectMapper.updateById(obj);
	}

	@Override
	public void insertXlsObject(XlsObject obj) throws Exception {
		xlsObjectMapper.insert(obj);
	}

	@Override
	public void deleteXlsObjectById(String id) throws Exception {
		xlsObjectMapper.deleteById(id);
	}


}
