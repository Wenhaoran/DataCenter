package cn.digitalpublishing.service.design.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.digitalpublishing.mapper.design.DeDBConnectMapper;
import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.service.design.DeDBConnectService;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Service("deDBConnectService")
public class DeDBConnectServiceImpl implements DeDBConnectService {
	
	@Resource(name="deDBConnectMapper")
    private DeDBConnectMapper deDBConnectMapper;

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		pageInfo.setRows(deDBConnectMapper.findPageCondition(pageInfo));
	}

	@Override
	public List<DeDBConnect> getDBConnectList(Map<String, Object> condition)throws Exception {
		return deDBConnectMapper.findListByCondition(condition);
	}

	@Override
	public DeDBConnect getDBConnectById(String id) throws Exception {
		return deDBConnectMapper.findById(id);
	}

	@Override
	public void updateDBConnect(DeDBConnect obj) throws Exception {
		deDBConnectMapper.updateById(obj);
	}

	@Override
	public void insertDBConnect(DeDBConnect obj) throws Exception {
		deDBConnectMapper.insert(obj);
	}

	@Override
	public void deleteDBConnectById(String id) throws Exception {
		deDBConnectMapper.deleteById(id);
	}

}
