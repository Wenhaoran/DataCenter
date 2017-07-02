package cn.digitalpublishing.service.txt.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.txt.TxtObjectMapper;
import cn.digitalpublishing.po.txt.TxtObject;
import cn.digitalpublishing.service.txt.TxtObjectService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("txtObjectService")
public class TxtObjectServiceImpl implements TxtObjectService {
	
	@Resource(name="txtObjectMapper")
    private TxtObjectMapper txtObjectMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(txtObjectMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<TxtObject> getTxtObjectList(Map<String, Object> condition)throws Exception {
		return txtObjectMapper.findListByCondition(condition);
	}

	@Override
	public TxtObject getTxtObjectById(String id) throws Exception {
		return txtObjectMapper.findById(id);
	}

	@Override
	public void updateTxtObject(TxtObject obj) throws Exception {
		txtObjectMapper.updateById(obj);
	}

	@Override
	public void insertTxtObject(TxtObject obj) throws Exception {
		txtObjectMapper.insert(obj);
	}

	@Override
	public void deleteTxtObjectById(String id) throws Exception {
		txtObjectMapper.deleteById(id);
	}

}
